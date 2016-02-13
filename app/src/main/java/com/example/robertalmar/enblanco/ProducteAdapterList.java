package com.example.robertalmar.enblanco;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by rober_000 on 06/12/2015.
 */
public class ProducteAdapterList extends ArrayAdapter<ProducteAuxUnitats> {
    private List<ProducteAuxUnitats> productes = null;
    Context context;
    int resId;
    View v;
    ProducteAuxUnitats i;
    Holder h;

Button btn;
    static class Holder{
        TextView nom;
        TextView nomdata;
        TextView nomimatge;
        ImageView imatge;
        TextView nompreu;
        TextView preu;
        TextView unitats;
        TextView preuTotal;
        Button restar;
    }

    public ProducteAdapterList(Context context, int resource, List<ProducteAuxUnitats> productes) {
        super(context, resource,productes);
        this.context = context;
        this.resId = resource;
        this.productes = productes;
    }
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        // assign the view we are converting to a local variable
        final int pos = position;
        v = convertView;
        h = null;

        // first check to see if the view is null. if so, we have to inflate it.
        // to inflate it basically means to render, or show, the view.
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resId, parent, false);

            h = new Holder();
            //h.nom = (TextView) v.findViewById(R.id.toptext);
            h.nomdata = (TextView) v.findViewById(R.id.toptextcomanda);
            //h.nomimatge = (TextView) v.findViewById(R.id.middletext);
            h.imatge = (ImageView) v.findViewById(R.id.middletextdatacomanda);
            //h.nompreu = (TextView) v.findViewById(R.id.bottomtext);
            h.preu = (TextView) v.findViewById(R.id.desctextcomanda);
            h.unitats = (TextView) v.findViewById(R.id.textView10);
            h.preuTotal = (TextView) v.findViewById(R.id.textView11);
            h.restar = (Button) v.findViewById(R.id.button5);

            v.setTag(h);

            // LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //v = inflater.inflate(R.layout.arrayadapter, null);
        } else {
            h = (Holder) v.getTag();
        }

        i = productes.get(position);

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 *
		 * Therefore, i refers to the current Item object.
		 */







        if (i != null) {


//            h.nom.setText("Nom: ");
                h.nomdata.setText(i.getNom());
                //          h.nomimatge.setText("Imatge: ");

                Drawable d = new BitmapDrawable(Resources.getSystem(), i.getImatge());
                h.imatge.setBackground(d);
                //        h.nompreu.setText("Preu: ");
                h.preu.setText(String.format("%.2f", i.getPreu()) + "€");
                h.unitats.setText(i.getUnitats() + "x");
                String p = String.format("%.2f", i.getUnitats() * i.getPreu());
                h.preuTotal.setText(p + "€");

                h.restar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ProducteAuxUnitats pau = (ProducteAuxUnitats) NovaComanda.l1.getItemAtPosition(pos);
                        pau.setUnitats(pau.getUnitats() - 1);
                        double p = pau.getUnitats() * pau.getPreu();
                        pau.setPreuTotal(p);
                        int index = NovaComanda.Comandes.indexOf(pau);
                        for(int i = 0; i < NovaComanda.array.size(); ++i){
                            if(pau.getNom().equals(NovaComanda.array.get(i).getNom())) NovaComanda.array.get(i).setStoc(NovaComanda.array.get(i).getStoc() + 1);
                        }
                        NovaComanda.lista.setAdapter(NovaComanda.PA);
                        if(pau.getUnitats().equals(0)){
                            NovaComanda.Comandes.remove(index);
                            NovaComanda.pr -= pau.getPreu();
                            double preuF = NovaComanda.pr;
                            Math.abs(preuF);
                            String p2 = String.format("%.2f", preuF);
                            NovaComanda.t13.setText(p2+"€");
                        }
                        else {
                            NovaComanda.Comandes.add(index, pau);
                            NovaComanda.Comandes.remove(index);
                            NovaComanda.pr -= pau.getPreu();
                            double preuF = NovaComanda.pr;
                            Math.abs(preuF);
                            String p2 = String.format("%.2f", preuF);
                            NovaComanda.t13.setText(p2 +"€");
                        }
                        NovaComanda.l1.setAdapter(NovaComanda.PACom);

                    }
                });

        }
        return v;
    }


}
