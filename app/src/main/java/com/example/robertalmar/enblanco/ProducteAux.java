package com.example.robertalmar.enblanco;

import android.graphics.Bitmap;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class ProducteAux {
    String nom;
    double preu;
    Bitmap imatge;
    int stoc;

    public ProducteAux(String n, Bitmap imatge, double p, int stoc){
        this.nom = n;
        this.imatge = imatge;
        this.preu = p;
        this.stoc = stoc;
    }

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }
}
