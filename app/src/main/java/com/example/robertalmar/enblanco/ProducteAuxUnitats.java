package com.example.robertalmar.enblanco;

import android.graphics.Bitmap;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class ProducteAuxUnitats {
    String nom;
    double preu;
    Bitmap imatge;
    Integer unitats;
    double preuTotal;
    public ProducteAuxUnitats(String n, Bitmap imatge, double p, int u, double pt){
        this.nom = n;
        this.imatge = imatge;
        this.preu = p;
        this.unitats = u;
        this.preuTotal = pt;
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

    public double getPreuTotal() {
        return preuTotal;
    }

    public void setPreuTotal(double preu) {
        this.preuTotal = preu;
    }

    public Bitmap getImatge() {
        return imatge;
    }

    public void setImatge(Bitmap imatge) {
        this.imatge = imatge;
    }

    public Integer getUnitats(){return unitats;}

    public void setUnitats(int u){this.unitats = u;}
}
