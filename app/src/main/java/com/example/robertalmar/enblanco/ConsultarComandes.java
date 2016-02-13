package com.example.robertalmar.enblanco;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by rober_000 on 28/12/2015.
 */
public class ConsultarComandes extends ActionBarActivity {
    TextView data1,data2,recaptat,desde,pre,dh,ta;
    Date date2,date3;
    EditText data11,data22,data33;
    String recOr;
    private int any, mes, dia;
    private int any2, mes2, dia2;
    private int any3, mes3, dia3;
    private ComandaAdapter com;
    private List<Comanda> comandes;
   private ListView lista;
    Context context;
    DataBaseHandler db;
    boolean caixa = false;
    String data1Or, data2Or,data3Or;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        context = this;
        db = new DataBaseHandler(context);
        setContentView(R.layout.consultarcomandes);
        inicializar();
        clicData1();
        clicData2();
        clicData3();
    }

    public void inicializar(){
        pre = (TextView) findViewById(R.id.price);
        dh = (TextView) findViewById(R.id.datahora);
        ta = (TextView) findViewById(R.id.taula);
        comandes = new ArrayList<>();
        final Calendar calendar = Calendar.getInstance();
        any = calendar.get(Calendar.YEAR);
        mes = calendar.get(Calendar.MONTH);
        dia = calendar.get(Calendar.DAY_OF_MONTH);
        any2 = calendar.get(Calendar.YEAR);
        mes2 = calendar.get(Calendar.MONTH);
        dia2 = calendar.get(Calendar.DAY_OF_MONTH);
        any3 = calendar.get(Calendar.YEAR);
        mes3 = calendar.get(Calendar.MONTH);
        dia3 = calendar.get(Calendar.DAY_OF_MONTH);
        data1 = (TextView) findViewById(R.id.textView21);
        data2 = (TextView) findViewById(R.id.textView22);
        desde = (TextView) findViewById(R.id.textView27);
        data33 = (EditText) findViewById(R.id.editText8);
        data11 = (EditText) findViewById(R.id.editText6);
        data22 = (EditText) findViewById(R.id.editText7);
        data1Or = data11.getText().toString();
        data2Or = data22.getText().toString();
        data3Or = data33.getText().toString();
        recaptat = (TextView) findViewById(R.id.textView23);
        recOr = recaptat.getText().toString();
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        data1.setVisibility(View.INVISIBLE);
        data11.setVisibility(View.INVISIBLE);
        recaptat.setVisibility(View.INVISIBLE);
        data2.setVisibility(View.INVISIBLE);
        data22.setVisibility(View.INVISIBLE);
        data33.setVisibility(View.INVISIBLE);
        desde.setVisibility(View.INVISIBLE);
        pre.setVisibility(View.INVISIBLE);
        dh.setVisibility(View.INVISIBLE);
        ta.setVisibility(View.INVISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId ==  R.id.radioButton2){
                    //Fer caixa
                    data1.setVisibility(View.VISIBLE);
                    data11.setVisibility(View.VISIBLE);
                    recaptat.setVisibility(View.VISIBLE);
                    data2.setVisibility(View.INVISIBLE);
                    data22.setVisibility(View.INVISIBLE);
                    data33.setVisibility(View.INVISIBLE);
                    desde.setVisibility(View.INVISIBLE);
                    pre.setVisibility(View.VISIBLE);
                    dh.setVisibility(View.VISIBLE);
                    ta.setVisibility(View.VISIBLE);
                    com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                    //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
                    lista = (ListView) findViewById(R.id.listView3);
                    comandes.clear();
                    lista.setAdapter(com);
                    recaptat.setText(recOr);
                    data11.setText(data1Or);
                    data22.setText(data2Or);
                    data33.setText(data3Or);
                    caixa = true;

                } else if(checkedId ==  R.id.radioButton){
                    //Llistar comandes
                    data1.setVisibility(View.INVISIBLE);
                    data11.setVisibility(View.INVISIBLE);
                    data2.setVisibility(View.VISIBLE);
                    data22.setVisibility(View.VISIBLE);
                    recaptat.setVisibility(View.INVISIBLE);
                    data33.setVisibility(View.VISIBLE);
                    desde.setVisibility(View.VISIBLE);
                    pre.setVisibility(View.VISIBLE);
                    dh.setVisibility(View.VISIBLE);
                    ta.setVisibility(View.VISIBLE);
                    com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                    //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
                    lista = (ListView) findViewById(R.id.listView3);
                    comandes.clear();
                    lista.setAdapter(com);
                    recaptat.setText(recOr);
                    data11.setText(data1Or);
                    data22.setText(data2Or);
                    caixa = false;
                }
                // checkedId is the RadioButton selected
            }
        });

    }

    public void clicData1(){
        data11.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                createdDialog(0).show();

            }
        });
    }

    public void clicData2(){
        data22.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createdDialog(1).show();
            }
        });
    }

    public void clicData3(){
        data33.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                createdDialog(2).show();
            }
        });
    }


    protected Dialog createdDialog(int id){
        if(id == 0) return new DatePickerDialog(this, datePickerListener,any,mes,dia);
        else if(id == 1) return new DatePickerDialog(this, datePickerListener2,any2,mes2,dia2);
        else if(id == 2) return new DatePickerDialog(this, datePickerListener3,any3,mes3,dia3);
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            any = selectedYear;
            mes = selectedMonth;
            dia = selectedDay;

            data11.setText(new StringBuilder().append(addLeadingZero(dia)).append("-").append(addLeadingZero(mes + 1)).append("-").append(any));
            Calendar cal = Calendar.getInstance();
            cal.set(any, mes, dia, 0, 0,0);
            Date data = cal.getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            cal.set(any, mes , dia, 23, 59, 59);
            Date data2 = cal.getTime();
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            comandes = db.ferCaixa(dateFormat.format(data),dateFormat2.format(data2));
            double total = 0.00;
            if(comandes.size() != 0) {
                com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
                for(int i = 0; i < comandes.size(); ++i) total += comandes.get(i).getPreuTot();
                String p2 = String.format("%.2f", total);

                recaptat.setText(recOr + " " + p2 + "€");
                lista = (ListView) findViewById(R.id.listView3);
                lista.setAdapter(com);
            } else {
                comandes.clear();
                com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                lista.setAdapter(com);
                String p2 = String.format("%.2f", total);
                recaptat.setText(recOr + " " + p2 + "€");
                Toast.makeText(context, "No hi ha comandes fetes pel dia seleccionat", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            any2 = selectedYear;
            mes2 = selectedMonth;
            dia2 = selectedDay;

            data22.setText(new StringBuilder().append(addLeadingZero(dia2)).append("-").append(addLeadingZero(mes2 + 1)).append("-").append(any2));

            Calendar cal = Calendar.getInstance();
            cal.set(any2, mes2 , dia2, 23, 59, 59);
            date2 = cal.getTime();
            SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if(data22.getText().toString().length() != 0 && data33.getText().toString().length() != 0) {
                comandes = db.ferCaixa(dateFormat2.format(date3), dateFormat2.format(date2));
                double total = 0.00;
                if (comandes.size() != 0) {
                    com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                    //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
                    for (int i = 0; i < comandes.size(); ++i) total += comandes.get(i).getPreuTot();
                    String p2 = String.format("%.2f", total);

                    recaptat.setText(recOr + " " + p2 + "€");
                    lista = (ListView) findViewById(R.id.listView3);
                    lista.setAdapter(com);
                } else {
                    if(date3.getTime() > date2.getTime())  Toast.makeText(context, "La data final no pot ser anterior que la data d'inici", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(context, "No hi ha comandes fetes  pel rang de dates introduït", Toast.LENGTH_SHORT).show();
                    comandes.clear();
                    com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                    lista.setAdapter(com);
                    String p2 = String.format("%.2f", total);
                    recaptat.setText(recOr + " " + p2 + "€");
                }
            }
        }
    };

    private DatePickerDialog.OnDateSetListener datePickerListener3 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            any3 = selectedYear;
            mes3 = selectedMonth;
            dia3 = selectedDay;

            data33.setText(new StringBuilder().append(addLeadingZero(dia3)).append("-").append(addLeadingZero(mes3 + 1)).append("-").append(any3));

            Calendar cal = Calendar.getInstance();
            cal.set(any3, mes3, dia3, 0, 0, 0);
            date3 = cal.getTime();
            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (data22.getText().toString().length() != 0 && data33.getText().toString().length() != 0) {
                comandes = db.ferCaixa(dateFormat3.format(date3), dateFormat3.format(date2));
                double total = 0.00;
                if (comandes.size() != 0) {
                    com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                    //ArrayAdapter<ProducteAux> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, array);
                    for (int i = 0; i < comandes.size(); ++i) total += comandes.get(i).getPreuTot();
                    String p2 = String.format("%.2f", total);

                    recaptat.setText(recOr + " " + p2 + "€");
                    lista = (ListView) findViewById(R.id.listView3);
                    lista.setAdapter(com);
                } else {
                    if(date3.getTime() > date2.getTime())  Toast.makeText(context, "La data final no pot ser anterior a la data d'inici", Toast.LENGTH_SHORT).show();
                    else Toast.makeText(context, "No hi ha comandes fetes  pel rang de dates introduït", Toast.LENGTH_SHORT).show();
                    comandes.clear();
                    com = new ComandaAdapter(context, R.layout.distribuciollistat, comandes);
                    lista.setAdapter(com);
                    String p2 = String.format("%.2f", total);
                    recaptat.setText(recOr + " " + p2 + "€");
                }
            }
        }
    };


    private String addLeadingZero(int num) {
        return num < 10 ? "0" + num : String.valueOf(num);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
