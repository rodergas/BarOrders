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
public class ProducteAdapter extends ArrayAdapter<ProducteAux> {
    private List<ProducteAux> productes = null;
    Context context;
    int resId;
    public static int value,uni;
    public static View v;
    public static Holder h;

    static class Holder{
        TextView nom;
        TextView nomdata;
        TextView nomimatge;
        ImageView imatge;
        TextView nompreu;
        TextView preu;
        TextView stoc;
        ImageView signo;
        ImageView delete;
    }

    public ProducteAdapter(Context context, int resource, List<ProducteAux> productes) {
        super(context, resource,productes);
        this.context = context;
        this.resId = resource;
        this.productes = productes;
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
            h.nomdata = (TextView) v.findViewById(R.id.toptextcomanda);
            //h.nomimatge = (TextView) v.findViewById(R.id.middletext);
            h.imatge = (ImageView) v.findViewById(R.id.middletextdatacomanda);
            //h.nompreu = (TextView) v.findViewById(R.id.bottomtext);
            h.preu = (TextView) v.findViewById(R.id.desctext);
            h.stoc = (TextView) v.findViewById(R.id.textView19);
            h.signo = (ImageView) v.findViewById(R.id.imageView);
            h.delete = (ImageView) v.findViewById(R.id.imageView2);
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
       final  ProducteAux i = productes.get(position);

        if (i != null) {
//            h.nom.setText("Nom: ");
            h.nomdata.setText(i.getNom());
  //          h.nomimatge.setText("Imatge: ");

            Drawable d = new BitmapDrawable(Resources.getSystem(),i.getImatge());
            h.imatge.setBackground(d);
    //        h.nompreu.setText("Preu: ");
            h.preu.setText(String.format("%.2f", i.getPreu())+"€");
            h.stoc.setText(i.getStoc() + " u.");
            uni = i.getStoc();
            h.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Estas segur que vols esborrar el producte " + i.getNom() + "?");
                    builder.setCancelable(false);
                    builder.setTitle("Esborrar producte");
                    builder.setPositiveButton("Esborrar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            LlistaProductes.array.remove(pos);
                            notifyDataSetChanged();
                            //DataBaseHandler db = new DataBaseHandler(context);
                            LlistaProductes.DBP.borrarProd(i.getNom());
                            Toast.makeText(context, "S'ha esborrat el producte "+  i.getNom(), Toast.LENGTH_SHORT).show();
                            //db.close();

                        }
                    });
                    builder.setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                }
            });

            h.signo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Quantes unitats vols afegir al stoc?");
                    builder.setCancelable(false);
                    builder.setTitle("Reposar stoc");
                    final EditText txt = new EditText(context);
                    txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    builder.setView(txt);
                    builder.setPositiveButton("Reposar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            try {
                                value = Integer.valueOf(txt.getText().toString());
                                int newUni = uni + value;
                                uni = (newUni <= Integer.MAX_VALUE && newUni >= 0 ? newUni : Integer.MAX_VALUE);

                                    i.setStoc(uni);
                                    h.stoc.setText(String.valueOf(i.getStoc()) + " u.");
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "S'ha modificat el stoc correctament", Toast.LENGTH_SHORT).show();
                                    // DataBaseHandler db = new DataBaseHandler(context);
                                    LlistaProductes.DBP.modificarStock(i.getNom(), uni);
                                //db.close();
                            } catch (NumberFormatException e) {
                                Toast.makeText(context, "S'ha introduit un numero massa gran o no s'ha introduit un numero", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    builder.setNegativeButton("Cancelar",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            });


        }

        return v;
    }


}
