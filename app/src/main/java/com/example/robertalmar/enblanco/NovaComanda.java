package com.example.robertalmar.enblanco;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rober_000 on 04/12/2015.
 */
public class NovaComanda extends ActionBarActivity {
    public static DataBaseHandler DBP;
    private String dataOr, hourOr, taulaOr;
    private int any, mes, dia, hora, minut, indexCom = 0;
    public static ProducteAdapter2 PA;
    public static ProducteAdapterList PACom;
    public static List<ProducteAuxUnitats> Comandes;
    public static ListView l1;
    AlertDialog dialog;
    private EditText textData, textHour, taula;
    static final String[] numTaulas = new String[] {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20"};
    TextView tx10;
    public static TextView t13;
    public static double pr = 0.00;
    public int[] colors = new int[]{0xFFedf5ff, 0xFFFFFFFF};
    public int colorPos;
    public static List<ProducteAux> array;
    public static GridView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.novacomanda);
        inicialitzarData();
        clicData();
        clicHora();
        clicTaula();
        taula = (EditText) findViewById(R.id.editText4);
        t13 = (TextView) findViewById(R.id.textView13);
        tx10 = (TextView) findViewById(R.id.textView10);

        inicializarProd();
        pr = 0.00;
        String p3 = String.format("%.2f", pr);
        t13.setText(String.valueOf(p3) + "€");

    }

    public void inicializarProd() {
        taulaOr = taula.getText().toString();
        dataOr = textData.getText().toString();
        hourOr = textHour.getText().toString();
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
                    array.add( new ProducteAux(c.getString(0) , bm.getImatge(c.getBlob(1)) ,Double.parseDouble(c.getString(2)), c.getInt(3)));
                    i++;
                } while (c.moveToNext());
            }
            PA = new ProducteAdapter2(this, R.layout.prodscomanda, array);
            Comandes = new ArrayList<ProducteAuxUnitats>();
            PACom = new ProducteAdapterList(this, R.layout.comanda, Comandes);
            //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
            lista = (GridView) findViewById(R.id.listView);
             l1 = (ListView) findViewById(R.id.listView2);

            lista.setAdapter(PA);
            l1.setAdapter(PACom);

            lista.setChoiceMode(lista.CHOICE_MODE_MULTIPLE);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // ProducteAdapter PA = (ProducteAdapter)lista.getItemAtPosition(position);
                    //View v = PA.getView(position,view,parent);
                    //CheckBox CB = (CheckBox)v.findViewById(R.id.checkBox);

                    TextView tx1 = (TextView) view.findViewById(R.id.desctext);
                    TextView tx2 = (TextView) view.findViewById(R.id.toptextcomanda);

                    String unidades = "";
                    String unidadesFinal = "";
                    //String selected = "";


                    ProducteAux pa = (ProducteAux) lista.getItemAtPosition(position);
                    if(array.get(position).getStoc() > 0) {
                        array.get(position).setStoc(array.get(position).getStoc() - 1);
                        lista.setAdapter(PA);
                        boolean tiene = false;
                        int index = 0;
                        double preu = 0;
                        for (int i = 0; i < l1.getCount(); ++i) {
                            ProducteAuxUnitats aux = (ProducteAuxUnitats) l1.getItemAtPosition(i);
                            if (aux.getNom().equals(pa.getNom())) {
                                tiene = true;
                                index = i;
                                preu = pa.getPreu();
                            }
                        }
                        if (!tiene) {
                            ProducteAuxUnitats paU = new ProducteAuxUnitats(pa.getNom(), pa.getImatge(), pa.getPreu(), 1, pa.getPreu());
                            Comandes.add(paU);
                            pr += pa.getPreu();
                            String p2 = String.format("%.2f", pr);
                            t13.setText(p2 + "€");
                            l1.setAdapter(PACom);
                        } else {
                            ProducteAuxUnitats paUUni = Comandes.get(index);
                            paUUni.setUnitats(paUUni.getUnitats() + 1);
                            String p = String.format("%.2f", (paUUni.getUnitats() * preu));
                            p = p.replace(',', '.');
                            paUUni.setPreuTotal(Double.parseDouble(p));
                            paUUni.setPreu(pa.getPreu());
                            pr += paUUni.getPreu();
                            String p2 = String.format("%.2f", pr);
                            t13.setText(p2 + "€");
                            Comandes.add(index, paUUni);
                            Comandes.remove(index);
                            l1.setAdapter(PACom);
                        }
                    } else{
                        Toast.makeText(getApplicationContext(), "S'ha de reposar el stoc del producte " + pa.getNom(), Toast.LENGTH_SHORT).show();
                    }


                    // Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG).show();


                }
            });
        }
        db.close();
    }
    public void inicialitzarData(){
        textData = (EditText) findViewById(R.id.dataEdit);
        textHour = (EditText) findViewById(R.id.editText2);
        final Calendar calendar = Calendar.getInstance();
        any = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        hora = calendar.get(Calendar.HOUR_OF_DAY);
        minut = calendar.get(Calendar.MINUTE);

    }
    public void clicTaula() {
        taula = (EditText) findViewById(R.id.editText4);
        taula.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                segundaParte();
            }
        });



    }

    public void segundaParte(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setItems(numTaulas, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                String s = addLeadingZero(item + 1);
                taula.setText(s);
            }
        });
        dialog = builder.create();
        dialog.show();
    }
    public void clicData(){
        textData = (EditText) findViewById(R.id.dataEdit);
        textData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createdDialog(0).show();
            }
        });
    }
    public void clicHora(){
        textHour = (EditText) findViewById(R.id.editText2);
        textHour.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createdDialog(1).show();
            }
        });
    }

    protected Dialog createdDialog(int id){
        if(id == 0) return new DatePickerDialog(this, datePickerListener,any,mes,dia);
        else if(id == 1) return new TimePickerDialog(this, timePickerListener,hora,minut,false);
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            any = selectedYear;
            mes = selectedMonth;
            dia = selectedDay;

            textData.setText(new StringBuilder().append(addLeadingZero(dia)).append("-").append(addLeadingZero(mes+1)).append("-").append(any));
        }
    };

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinut) {
            hora = selectedHour;
            minut = selectedMinut;

            textHour.setText(new StringBuilder().append(addLeadingZero(hora)).append(":").append(addLeadingZero(minut)));
        }
    };


    private String addLeadingZero(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }

    public void afegirNovaComanda(View v){
        if(v.getId() == R.id.button3){
               DataBaseHandler db = new DataBaseHandler(this);
                for(int i = 0; i < lista.getCount(); ++i)db.modificarStock(array.get(i).getNom(), array.get(i).getStoc());

            Calendar cal = Calendar.getInstance();

            String dataa = textData.getText().toString();
            String hourr = textHour.getText().toString();
            int d=0,m=0,y=0,min=0,s=0;
            boolean dataB = true, hourB = true, taulaB = true;
            if(dataa.length() != 0) {
                d = Integer.parseInt(dataa.substring(0, 2)); //DD
                m = (Integer.parseInt(dataa.substring(3, 5))) - 1; //MM
                y = Integer.parseInt(dataa.substring(6)); //YYYY
            } else{
                dataB = false;
            }

            if(hourr.length() != 0) {
                min = Integer.parseInt(hourr.substring(0, 2)); //HH
                s = Integer.parseInt(hourr.substring(3)); //MM
            } else{
                hourB = false;
            }

            String numtaula = taula.getText().toString();
            int ntaula = 0;
            if(numtaula.length() != 0){
                ntaula = Integer.valueOf(numtaula);
            }else{
                taulaB = false;
            }

            if(dataB && taulaB && hourB && pr != 0) {
                cal.set(y, m, d, min, s);
                Date data = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateFormat.format(data);
                DBP.afegirComanda(dateFormat.format(data), ntaula, pr);
                textData.setText(dataOr);
                textHour.setText(hourOr);
                taula.setText(taulaOr);
                pr = 0;
                String p4 = String.format("%.2f", pr);
                t13.setText(String.valueOf(p4) + "€");
                Comandes.clear();
                l1.setAdapter(PACom);
                Toast.makeText(getApplicationContext(), "Comanda creada correctament" , Toast.LENGTH_SHORT).show();
            } else if(!dataB){
                Toast.makeText(getApplicationContext(), "No s'ha seleccionat una data", Toast.LENGTH_SHORT).show();
            }else if(!hourB){
                Toast.makeText(getApplicationContext(), "No s'ha seleccionat una hora", Toast.LENGTH_SHORT).show();
            }else if(!taulaB){
                Toast.makeText(getApplicationContext(), "No s'ha seleccionat una taula", Toast.LENGTH_SHORT).show();
            } else if(pr == 0){
                Toast.makeText(getApplicationContext(), "Afegeix algun producte a la comanda", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        DBP.close();
    }

}