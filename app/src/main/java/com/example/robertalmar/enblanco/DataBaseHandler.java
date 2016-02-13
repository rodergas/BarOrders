package com.example.robertalmar.enblanco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class DataBaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSIO = 3;

    private static final String DB_NOM = "productesDB2";

    private static final String TAULA_PRODUCTES = "productes";
    private static final String TAULA_COMANDES = "comandes";
    private static final String TAULA_COMANDES_PROD = "comandesprod";

    private static final String ID_NOM = "nom";
    private static final String ID_IMATGE = "imatge";
    private static final String ID_PREU = "preu";
    private static final String ID_STOCK = "stock";

    private static final String ID_DATA = "data";
    private static final String ID_NUM = "num";
    private static final String ID_PREUCOMANDA = "preucomanda";
    private static final String ID_TAULA = "taula";

    private static final String ID_NOMP = "nom";
    private static final String ID_IMATGEP = "imatge";
    private static final String ID_PREUP = "preu";
    private static final String ID_UNITATS = "unitats";
    private static final String ID_PREUTOTAL = "preutotal";
    public DataBaseHandler(Context context) {
        super(context, DB_NOM, null, DB_VERSIO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PRODUCTES_TAULA = "CREATE TABLE IF NOT EXISTS " + TAULA_PRODUCTES + "("
                + ID_NOM + " TEXT PRIMARY KEY, "
                + ID_IMATGE + " BLOB, "
                + ID_PREU + " REAL, "
                + ID_STOCK + " INTEGER "
                + ")";
        String CREATE_COMANDES_TAULA = "CREATE TABLE IF NOT EXISTS " + TAULA_COMANDES + "("
                + ID_NUM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ID_DATA + " DATETIME, "
                + ID_TAULA + " INTEGER, "
                + ID_PREUCOMANDA + " REAL "
                + ")";

        db.execSQL(CREATE_PRODUCTES_TAULA);
        db.execSQL(CREATE_COMANDES_TAULA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TAULA_PRODUCTES);
        onCreate(db);
    }

    public boolean afegirProducte(Producte p) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT nom FROM productes WHERE nom=?", new String[] {p.getNom() + ""});
        if(c.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(ID_NOM, p.getNom());
            values.put(ID_IMATGE, p.getImatge());
            values.put(ID_PREU, p.getPreu());
            values.put(ID_STOCK, p.getStoc());
            // Inserting Row
            db.insert(TAULA_PRODUCTES, null, values);
            db.close(); // Closing database connection
            return true;
        } else {
            return false;
        }
    }

    public boolean afegirComanda(String data, int numTaula, double preu) {
        SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ID_DATA, data);
            values.put(ID_TAULA, numTaula);
            values.put(ID_PREUCOMANDA, preu);
            // Inserting Row
            db.insert(TAULA_COMANDES, null, values);

            db.close(); // Closing database connection
            return true;
    }

    public List<Producte> getProductes() {
        List<Producte> llistaProd = new ArrayList<Producte>();
        String selectQuery = "SELECT  * FROM " + TAULA_PRODUCTES;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Producte p = new Producte();
                p.setNom(cursor.getString(0));
                p.setImatge(cursor.getBlob(1));
                p.setPreu(Double.parseDouble(cursor.getString(2)));
                llistaProd.add(p);
            } while (cursor.moveToNext());
        }

        return llistaProd;
    }
    public void borrarProd(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] borrar = new String[]{id};
        db.delete(TAULA_PRODUCTES,  "nom = ?" , borrar);
    }

    public void modificarStock(String id, int stock){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_STOCK, stock);
        db.update(TAULA_PRODUCTES, values, ID_NOM + " = ?", new String[]{id});

    }

    public List<Comanda> ferCaixa(String from, String to){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TAULA_COMANDES, null, ID_DATA + " BETWEEN ? AND ?", new String[]{
               from, to}, null, null, null, null);
        List<Comanda> comandes = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                Comanda c = new Comanda();
                c.setData(cursor.getString(0));
                c.setTaula(cursor.getInt(1));
                c.setPreuTot(Double.parseDouble(cursor.getString(2)));
                comandes.add(c);
            } while (cursor.moveToNext());
        }
        return comandes;
    }

}
