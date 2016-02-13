package com.example.robertalmar.enblanco;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class Producte {
    private String nom;
    private double preu;
    private byte[] imatge;
    private int stoc;

    public int getStoc() {
        return stoc;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public Producte(){}
    public Producte(String nom, byte[] imatge, double preu, int stoc) {
        this.nom = nom;
        this.preu = preu;
        this.imatge = imatge;
        this.stoc = stoc;
    }

    public String getNom() {
        return nom;
    }

    public double getPreu() {
        return preu;

    }

    public byte[] getImatge() {
        return imatge;

    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public void setImatge(byte[] imatge) {
        this.imatge = imatge;
    }


}
