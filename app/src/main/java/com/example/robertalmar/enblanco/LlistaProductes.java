package com.example.robertalmar.enblanco;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class LlistaProductes extends ActionBarActivity {
    public static DataBaseHandler DBP;
    private ProducteAdapter PA;
    private ArrayList<String> select;
    TextView tx;
    public static Context con;
    public static List<ProducteAux> array;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.llistaprod);
        tx = (TextView) findViewById(R.id.textView8);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        con = getApplicationContext();
        cargar();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                NavUtils.navigateUpFromSameTask(this);

                boolean atras = true;
                Bundle bundleObj = new Bundle();
                bundleObj.putString("atras", Boolean.toString(atras));
                Intent i = new Intent(getApplicationContext(),Comandes.class);
                i.putExtras(bundleObj);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void cargar() {
        BitMap bm = new BitMap();
        DBP = new DataBaseHandler(this);
        SQLiteDatabase db = DBP.getReadableDatabase();
        if (db != null) {
            Cursor c = db.rawQuery("select * from productes order by nom asc", null);
            int i = 0;
            int cantidad = c.getCount();
            array = new ArrayList<ProducteAux>();
            if (c.moveToFirst()) {
                do {
                    array.add(new ProducteAux(c.getString(0) , bm.getImatge(c.getBlob(1)) ,Double.parseDouble(c.getString(2)), c.getInt(3)));
                    i++;
                } while (c.moveToNext());
            }
            PA = new ProducteAdapter(this, R.layout.arrayadapter, array);
            //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
            final ListView lista = (ListView) findViewById(R.id.lista);
            lista.setAdapter(PA);
            lista.setChoiceMode(lista.CHOICE_MODE_MULTIPLE);
            select = new ArrayList<String>();

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   // ProducteAdapter PA = (ProducteAdapter)lista.getItemAtPosition(position);
                    //View v = PA.getView(position,view,parent);
                    //CheckBox CB = (CheckBox)v.findViewById(R.id.checkBox);


                            TextView tx1 = (TextView) view.findViewById(R.id.desctext);
                            TextView tx2 = (TextView) view.findViewById(R.id.toptextcomanda);

                            String selected = "";
                            ProducteAux pa = (ProducteAux)lista.getItemAtPosition(position);
                            if(!select.contains(pa.getNom())){
                                tx.setText("");
                              //  CB.setChecked(true);
                                select.add(pa.getNom());
                            }
                            else {
                              //  CB.setChecked(false);
                                select.remove(pa.getNom());
                            }
                            for(int j = 0; j < select.size(); ++j) selected += select.get(j) + "\n";



                   // Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG).show();


                }
            });

        }
    }

    public void paginaPrincipal(View v){
       //if(v.getId() == R.id.button3){
            select = new ArrayList<>();
            Intent i = new Intent(this,Comandes.class);
            startActivity(i);
           // finish();
        //}
    }

    public void afegirProductePantalla(View v){
        if(v.getId() == R.id.button4){
            select = new ArrayList<>();
            Intent i = new Intent(this,AfegirProducte.class);
            startActivity(i);
           // finish();
        }
    }
/*
    public void borrarProductePantalla(View v){
        if(v.getId() == R.id.button7){
            if(select.size() == 0){
                Toast.makeText(getApplicationContext(), "No s'ha seleccionat cap producte", Toast.LENGTH_LONG).show();
                //tx.setText("No s'ha seleccionat cap producte");
            }
            else {
                //tx.setText("");
                String borrats = "";
                for(int j = 0; j < select.size(); ++j) borrats += select.get(j) + "\n";
                Toast.makeText(getApplicationContext(), "S'han suprimit els següents productes:" + "\n" + borrats, Toast.LENGTH_LONG).show();
                DataBaseHandler db = new DataBaseHandler(this);

                for (int i = 0; i < select.size(); ++i) db.borrarProd(select.get(i));
                for (int i = 0; i < select.size(); ++i) select.remove(i);
                cargar();
            }
        }
    }*/
@Override
public void onDestroy() {
    super.onDestroy();
    DBP.close();
}

}

