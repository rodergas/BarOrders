package com.example.robertalmar.enblanco;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by rober_000 on 07/12/2015.
 */
public class AfegirProducte extends ActionBarActivity {
    ImageButton IB;
    EditText nom;
    EditText preu;
    TextView text;
    TextView resultado;
    Drawable backOriginal;
    String preuOr;
    String nomOr;
    String stocOr;
    TextView stocText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afegirproductepantalla);
        IB = (ImageButton) findViewById(R.id.imageButton);
        text = (TextView) findViewById(R.id.textView7);
        resultado = (TextView) findViewById(R.id.textView6);
        nom = (EditText) findViewById(R.id.editText);
        preu = (EditText) findViewById(R.id.editText3);
        stocText = (EditText) findViewById(R.id.editText5);

        backOriginal = IB.getBackground();
        nomOr = nom.getText().toString();
        preuOr = preu.getText().toString();
        stocOr = stocText.getText().toString();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        IB.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){
                   Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   i.setType("image/*");
                   startActivityForResult(i, 2);
               }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                NavUtils.navigateUpFromSameTask(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void onActivityResult(int requestCode, int resultCode, Intent i){
        super.onActivityResult(requestCode, resultCode, i);
        if(resultCode == RESULT_OK && requestCode == 2){
            Uri imatge = i.getData();
            String[] path = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(imatge, path, null, null, null);
            c.moveToFirst();
            int columna = c.getColumnIndex(path[0]);
            String imatgePath = c.getString(columna);
            c.close();
            Bitmap b = (BitmapFactory.decodeFile(imatgePath));
            b = Bitmap.createScaledBitmap(b, 100, 100, true);
            Drawable d = new BitmapDrawable(Resources.getSystem(),b);
            IB.setBackground(d);
            text.setText("");

        }
    }

    public void afegeixProducte(View v){
        if(v.getId() == R.id.button6){
            boolean preuBB = false;
            boolean stocB = false;
            boolean imatgeB = false;
            DataBaseHandler db = new DataBaseHandler(this);
            boolean b = false;
            boolean b2 = false;
            Drawable drawable5 = this.getResources().getDrawable(R.drawable.bravas);
            Bitmap image= ((BitmapDrawable)drawable5).getBitmap();
            image = Bitmap.createScaledBitmap(image, 40, 40, true);
            if(IB.getBackground() != backOriginal) {
                image = ((BitmapDrawable) IB.getBackground()).getBitmap();
            } else {
                imatgeB = true;
                b2 = true;
            }
            BitMap BM = new BitMap();
            String nomdata = "";
            if(nom.getText().toString() != nomdata){
                nomdata = nom.getText().toString();
            } else{
                b2 = true;
            }
            Double preudata = 0.0;
            boolean preuB = false;
            boolean decimalB = false;
            try{
                String p = preu.getText().toString();
                int index = p.indexOf('.');
                if(index == -1 && p.length() > 4) {
                    preuB = true; b2 = true;
                } else if(index != -1  && (index -1) >= 4){
                    preuB = true; b2 = true;
                }else if(index != -1  && ((p.length()-1) - index) > 2){
                    decimalB = true; b2 = true;
                }else preudata = Double.parseDouble(preu.getText().toString());
            }catch (NumberFormatException e){
                preuBB = true;
                b2 = true;
            }
            int stoc = 0;
            boolean b3 = false;
            if(stocText.getText().length() != 0){
                try {
                    stoc = Integer.valueOf(stocText.getText().toString());
                } catch(NumberFormatException e){
                    b2 = true;
                    b3 = true;
                }
            }else{
                stocB = true;
                b2 = true;
            }
            if(b2 == false) {b  = db.afegirProducte(new Producte(nomdata, BM.getBytes(image), preudata, stoc)); db.close();}


            if(b == true){
                //resultado.setText("El producte s'ha afegit correctament");
                Toast.makeText(getApplicationContext(), "El producte s'ha afegit correctament", Toast.LENGTH_SHORT).show();
                nom.setText(nomOr);
                preu.setText(preuOr);
                IB.setBackground(backOriginal);
                stocText.setText(stocOr);
                //IB.setBackground(getResources().getDrawable(android.R.drawable.btn_default_small));
                text.setText("Seleccionar imatge");
            }  else if(preuBB == true){
                //resultado.setText("No s'han introduït correctament les dades");
                Toast.makeText(getApplicationContext(), "No s'ha introduit un preu", Toast.LENGTH_SHORT).show();

            } else if(stocB == true){
                //resultado.setText("No s'han introduït correctament les dades");
                Toast.makeText(getApplicationContext(), "No s'ha introduit un stoc", Toast.LENGTH_SHORT).show();
            } else if(imatgeB == true){
                //resultado.setText("No s'han introduït correctament les dades");
                Toast.makeText(getApplicationContext(), "No s'ha introduit una imatge", Toast.LENGTH_SHORT).show();

            }
            else if(preuB == true){
                //resultado.setText("No s'han introduït correctament les dades");
                Toast.makeText(getApplicationContext(), "S'ha introduit un preu amb masses digits a la part entera", Toast.LENGTH_SHORT).show();

            } else if(decimalB == true){
                //resultado.setText("No s'han introduït correctament les dades");
                Toast.makeText(getApplicationContext(), "S'ha introduit un preu amb més de dos decimals", Toast.LENGTH_SHORT).show();

            }else if(b3 == true){
                //resultado.setText("No s'han introduït correctament les dades");
                Toast.makeText(getApplicationContext(), "S'ha introduit un numero de stoc massa gran", Toast.LENGTH_SHORT).show();

            }else if(b2 == true){
                Toast.makeText(getApplicationContext(), "Falten dades per introduir", Toast.LENGTH_SHORT).show();
            } else if(b == false) {
                Toast.makeText(getApplicationContext(), "Ja hi ha un producte amb aquest nom", Toast.LENGTH_SHORT).show();
                //resultado.setText("Ja hi ha un producte amb aquest nom");
            }

        }
    }



}
