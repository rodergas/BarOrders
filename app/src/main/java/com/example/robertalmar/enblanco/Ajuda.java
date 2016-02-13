package com.example.robertalmar.enblanco;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by rober_000 on 31/12/2015.
 */
public class Ajuda extends ActionBarActivity{
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> pare;
    HashMap<String, List<String>> child;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajuda);
        expListView = (ExpandableListView) findViewById(R.id.expandableListView);
        inicialitzar();
        listAdapter = new ExpandableListAdapter(this, pare, child);
        expListView.setAdapter(listAdapter);
    }

    public void inicialitzar(){
        pare = new ArrayList<>();
        child = new HashMap<>();

        pare.add("Com afegir un producte?");
        pare.add("Com esborrar un producte?");
        pare.add("Com reposar el stoc d'un producte?");
        pare.add("Com crear una comanda?");
        pare.add("Com esborrar una unitat d'un producte en la construcció d'una comanda?");
        pare.add("Com fer caixa d'un dia?");
        pare.add("Com veure les comandes fetes entre dues dates?");

        List<String> afegir = new ArrayList<>();
        String aux = "Llista de productes > Afegir producte";
        afegir.add("Per afegir un producte l'usuari haurà de fer els següents passos: " +
                "Llista de productes > Afegir producte i haurà d'omplir tots els camps que es mostren, les úniques restriccions que hi ha " +
                "són que el stoc no pot ser major que el número 2147483647 i el preu no pot contenir més de 4 xifres a la part entera i dues en la" +
                        " part decimal, per últim es clica sobre el botó Afegir producte");

        List<String> esborrar = new ArrayList<>();
        esborrar.add("Per esborrar un producte l'usuari haurà de fer els següents passos: " +
        "Llista de productes > clicar a la X corresponent del producte que es vol esborrar");

        List<String> stoc = new ArrayList<>();
        stoc.add("Per reposar el stoc d'un producte l'usuari haurà de fer els següents passos: " +
        "Llista de productes > clicar al + corresponent del producte que es vol reposar el stoc, l'única restricció que hi ha es que el número de stoc mai" +
                " serà més gran que el número 2147483647");

        List<String> comanda = new ArrayList<>();
        comanda.add("Per construir una comanda l'usuari haurà de fer els següents passos: " +
                "Crear comanda > seleccionar una data,hora i una taula > clicar sobre els productes que es vol afegir a la comanda > Crear comanda");

        List<String> comandaBor = new ArrayList<>();
        comandaBor.add("Per esborrar una unitat d'un producte que està en la comanda l'usuari haurà de clicar en el botó - del producte corresponent");

        List<String> caixa = new ArrayList<>();
        caixa.add("Per fer caixa sobre un dia en concret l'usuari haurà de fer els següent passos: " +
                "Llistat de comandes > Fer caixa i seleccionarà la data en la qual es vol fer caixa");

        List<String> llistar = new ArrayList<>();
        llistar.add("Per veure les comandes fetes entre dues dates l'usuari haurà de fer els següent passos: " +
                "Llistat de comandes > Llistar comandes entre dues dates > seleccionarà la data d'inici i la data de fi");

        child.put(pare.get(0), afegir);
        child.put(pare.get(1), esborrar);
        child.put(pare.get(2), stoc);
        child.put(pare.get(3), comanda);
        child.put(pare.get(4), comandaBor);
        child.put(pare.get(5), caixa);
        child.put(pare.get(6), llistar);


    }
}
