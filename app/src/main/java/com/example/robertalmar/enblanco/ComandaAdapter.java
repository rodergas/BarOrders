package com.example.robertalmar.enblanco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by rober_000 on 06/12/2015.
 */
public class ComandaAdapter extends ArrayAdapter<Comanda> {
    private List<Comanda> com = null;
    Context context;
    int resId;
    public static View v;
    public static Holder h;

    static class Holder{
        TextView data;
        TextView taula;
        TextView preu;
    }

    public ComandaAdapter(Context context, int resource, List<Comanda> productes) {
        super(context, resource,productes);
        this.context = context;
        this.resId = resource;
        this.com = productes;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        // assign the view we are converting to a local variable
        v = convertView;
        h = null;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resId, parent, false);

            h = new Holder();
            //h.nom = (TextView) v.findViewById(R.id.toptext);
            h.data = (TextView) v.findViewById(R.id.textView24);
            //h.nomimatge = (TextView) v.findViewById(R.id.middletext);
            h.taula = (TextView) v.findViewById(R.id.textView25);
            //h.nompreu = (TextView) v.findViewById(R.id.bottomtext);
            h.preu = (TextView) v.findViewById(R.id.textView26);
            v.setTag(h);
            // LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = inflater.inflate(R.layout.arrayadapter, null);
        } else {
            h = (Holder) v.getTag();
        }
        final Holder haux = h;

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */
       final  Comanda i = com.get(position);

        if (i != null) {
//            h.nom.setText("Nom: ");
            h.data.setText(i.getData());
            h.taula.setText(String.valueOf(i.getTaula()));
    //        h.nompreu.setText("Preu: ");
            h.preu.setText(String.format("%.2f", i.getPreuTot())+"€");

        }

        return v;
    }


}
