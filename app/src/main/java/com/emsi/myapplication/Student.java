package com.emsi.myapplication;

import android.graphics.Bitmap;

import java.util.ArrayList;

class Student {
    private int id;
    private String nom,prenom,classe,phone;
    private Bitmap photo;
    private ArrayList<MaNote> notes;

    public ArrayList<MaNote> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<MaNote> notes) {
        this.notes = notes;
    }

    public void addNote(MaNote n){
        if (notes==null)
            notes=new ArrayList<MaNote>();
        notes.add(n);
    }



    public Student(int id, String nom, String prenom, String classe, String phone, Bitmap photo) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.phone = phone;
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }
}
