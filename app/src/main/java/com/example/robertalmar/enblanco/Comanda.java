package com.example.robertalmar.enblanco;

/**
 * Created by rober_000 on 28/12/2015.
 */
public class Comanda {
    String data;
    int taula;
    double preuTot;

    public Comanda() {
    }

    public Comanda(String data, int taula, double preuTot) {
        this.data = data;
        this.taula = taula;
        this.preuTot = preuTot;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getTaula() {
        return taula;
    }

    public void setTaula(int taula) {
        this.taula = taula;
    }

    public double getPreuTot() {
        return preuTot;
    }

    public void setPreuTot(double preuTot) {
        this.preuTot = preuTot;
    }
}
