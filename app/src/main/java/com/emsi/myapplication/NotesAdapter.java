package com.emsi.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class NotesAdapter extends ArrayAdapter<MaNote> {

    public NotesAdapter(@NonNull Context context, @NonNull List<MaNote> objects) {
        super(context, R.layout.noteline, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li=(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=li.inflate(R.layout.noteline,parent,false);
        TextView noteLabel=itemView.findViewById(R.id.noteLabel);
        TextView noteScore=itemView.findViewById(R.id.noteScore);
        ImageView noteIcon=itemView.findViewById(R.id.noteIcon);

        noteLabel.setText(this.getItem(position).getLabel());
        noteScore.setText(getItem(position).getScore()+"");
        if (getItem(position).getScore()<10)
            noteIcon.setImageResource(R.drawable.dislike);
        else
            noteIcon.setImageResource(R.drawable.like);
        return itemView;

    }
}
