package com.emsi.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.transform.ErrorListener;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUGTAG = "MainActivity";
    private Student std;
    private ResourceBundle json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(DEBUGTAG,"on est dans onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(DEBUGTAG,"on est dans onStart");
    }






    @Override
    protected void onPause() {
        super.onPause();
        Log.d(DEBUGTAG,"on est dans onPause");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(DEBUGTAG,"on est dans onRestart");
    }

    public void btnClick(View view) {
        Toast.makeText(this, R.string.btnClicked, Toast.LENGTH_LONG).show();


    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.d(DEBUGTAG,"on est dans onResume");
        StringRequest req = new StringRequest(Request.Method.GET,
                URL_BASE+URL_WS_PROFILE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.w(DEBUGTAG,response);
                        JSONObject json = null;
                        try {
                            json = new JSONObject(response);
                            if(json.has("error"))
                                Toast.makeText(MainActivity.this,json.getString("error"), Toast.LENGTH_LONG).show();
                            else {
                                std = new Student(json.getInt("id"),json.getString("nom"),
                                        json.getString("prenom"),json.getString("classe"),
                                        json.getString("phone"),null);
                                if (json.has("notes")){
                                    JSONArray ja = json.getJSONArray("notes");
                                    for (int i=0; i<ja.length();i++){
                                        JSONObject j = ja.getJSONObject(i);
                                        MaNote n = new MaNote(j.getString("label"),j.getDouble("score"));
                                        std.addNote(n);
                                    }
                                }

                                VolleySingleton.getInstance(getApplicationContext()).getImageLoader().get(
                                        URL_BASE+URL_IMAGES+json.getString("photo"), new ImageLoader.ImageListener(){
                                            @Override
                                            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                                                std.setPhoto(response.getBitmap());

                                                EditText txtNom = findViewById(R.id.txtNom);
                                                txtNom.setText(std.getNom());
                                                EditText txtPrenom = findViewById(R.id.txtPrenom);
                                                txtPrenom.setText(std.getPrenom());
                                                EditText txtClasse = findViewById(R.id.txtClasse);
                                                txtClasse.setText(std.getClasse());
                                                ImageView img = findViewById(R.id.imgProfile);
                                                img.setImageBitmap(std.getPhoto());
                                                ListView l =findViewById(R.id.lisNotes);
                                                if (l!=null)
                                                l.setAdapter(new NotesAdapter(MainActivity.this,std.getNotes()));

                                            }

                                            public void onErrorResponse(VolleyError error) {
                                                        Log.w(DEBUGTAG,error.getMessage());
                                                    }
                                                });

                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.w(DEBUGTAG,error.getMessage());
            }
        });
        VolleySingleton.getInstance(this.getApplicationContext()).addToRequestQueue(req);
            Log.w(DEBUGTAG, "Requete envoyee");
    /*
      MonWS ws = new MonWS();
      ws.execute(URL_BASE,URL_WS_PROFILE,URL_IMAGES);
    */
    }

    private static  String URL_BASE="https://belatar.name";
    private static  String URL_WS_PROFILE="/rest/profile.php?login=test&passwd=test&id=9998&notes=true";
    private static  String URL_IMAGES="/images/";
/*
    public class MonWS extends AsyncTask<String,Void,Student>{

        @Override
        protected Student doInBackground(String... urls) {
            Log.d(DEBUGTAG,urls[0]);

            URL url = null;
            Student std = null;
            try {
                url = new URL(urls[0]+urls[1]);
                HttpURLConnection conn =(HttpURLConnection) url.openConnection();
                InputStream is = (InputStream) new BufferedInputStream(conn.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                StringBuilder sb= new StringBuilder();
                String line = null;
                while ((line = r.readLine()) != null) {
                    sb.append(line).append('\n');
                }
                is.close();
                String result = sb.toString();
                Log.d(DEBUGTAG,result);
                JSONObject json = new JSONObject(result);
                if(json.has("error"))
                    Toast.makeText(MainActivity.this,json.getString("error"),
                            Toast.LENGTH_LONG).show();
                else{
                    std = new Student(json.getInt("id"),json.getString("nom"),
                            json.getString("prenom"),json.getString("classe"),
                            json.getString("phone"),null);
                    ////TODO: telecharger limage
                    url = new URL(urls[0]+urls[2]+json.getString("photo"));
                    conn =(HttpURLConnection) url.openConnection();
                    is = (InputStream) new BufferedInputStream(conn.getInputStream());
                    std.setPhoto(BitmapFactory.decodeStream(is));
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return std;
        }

        @Override
        protected void onPostExecute(Student std) {
            super.onPostExecute(std);
        if(std==null)////TODO:
            return;
            EditText txtNom = findViewById(R.id.txtNom);
            txtNom.setText(std.getNom());
            EditText txtPrenom = findViewById(R.id.txtPrenom);
            txtPrenom.setText(std.getPrenom());
            EditText txtClasse = findViewById(R.id.txtClasse);
            txtClasse.setText(std.getClasse());
            ImageView img = findViewById(R.id.imgProfile);
            img.setImageBitmap(std.getPhoto());
        }
    }
*/
}
