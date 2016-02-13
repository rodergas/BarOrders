package com.example.robertalmar.enblanco;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.InputStream;
import java.util.List;


public class Comandes extends ActionBarActivity {
    BitMap bm;
    Bitmap Jamon,Agua,CocaZero,Nestea,Bravas,BocataAtun;
    SharedPreferences prefs;
    boolean atras;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
        setContentView(R.layout.activity_comandes);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        bm = new BitMap();

        cargar();


    }

    public void cargar(){


        if(!prefs.getBoolean("firstTime", false)) {
            this.deleteDatabase("productesDB2");
            DataBaseHandler db = new DataBaseHandler(this);
            Drawable drawable = this.getResources().getDrawable(R.drawable.jamon);

            Jamon = ((BitmapDrawable)drawable).getBitmap();
            Jamon = Bitmap.createScaledBitmap(Jamon,40,40,true);
            Drawable drawable2 = this.getResources().getDrawable(R.drawable.agua);
            Agua = ((BitmapDrawable)drawable2).getBitmap();
            Agua = Bitmap.createScaledBitmap(Agua, 40, 40, true);
            Drawable drawable3 = this.getResources().getDrawable(R.drawable.coca);
            CocaZero = ((BitmapDrawable)drawable3).getBitmap();
            CocaZero = Bitmap.createScaledBitmap(CocaZero, 40, 40, true);
            Drawable drawable4 = this.getResources().getDrawable(R.drawable.nestea);
            Nestea = ((BitmapDrawable)drawable4).getBitmap();
            Nestea = Bitmap.createScaledBitmap(Nestea, 40, 40, true);
            Drawable drawable5 = this.getResources().getDrawable(R.drawable.bravas);
            Bravas = ((BitmapDrawable)drawable5).getBitmap();
            Bravas  = Bitmap.createScaledBitmap(Bravas , 40, 40, true);
            Drawable drawable6 = this.getResources().getDrawable(R.drawable.atun);
            BocataAtun = ((BitmapDrawable)drawable6).getBitmap();
            BocataAtun = Bitmap.createScaledBitmap(BocataAtun, 40, 40, true);



            db.afegirProducte(new Producte("Agua", bm.getBytes(Agua), 1,10));
            db.afegirProducte(new Producte("Cocacola Zero", bm.getBytes(CocaZero), 2,10));
            db.afegirProducte(new Producte("Nestea", bm.getBytes(Nestea), 1.5,10));
            db.afegirProducte(new Producte("Tapa de jamón", bm.getBytes(Jamon), 4,10));
            db.afegirProducte(new Producte("Bravas", bm.getBytes(Bravas), 3.33,10));
            db.afegirProducte(new Producte("Bocata de atún", bm.getBytes(BocataAtun), 0.5,10));
            db.close();
           SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comandes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void irLlistaProductes(View v){
        if(v.getId() == R.id.button){
            Intent i = new Intent(this,LlistaProductes.class);
            startActivity(i);
           // finish();
        }
    }

    public void irNovaComanda(View v){
        if(v.getId() == R.id.button2){
            Intent i = new Intent(this,NovaComanda.class);
            startActivity(i);
           // finish();
        }
    }

    public void irLlistarComanda(View v){
        if(v.getId() == R.id.button7){
            Intent i = new Intent(this,ConsultarComandes.class);
            startActivity(i);
            // finish();
        }
    }

    public void irAjuda(View v){
        if(v.getId() == R.id.button10){
            Intent i = new Intent(this,AboutHelp.class);
            startActivity(i);
            // finish();
        }
    }

}
