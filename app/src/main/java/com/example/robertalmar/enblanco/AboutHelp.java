package com.example.robertalmar.enblanco;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rober_000 on 01/01/2016.
 */
public class AboutHelp extends ActionBarActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpabout);
        ListView lv = (ListView) findViewById(R.id.listView4);
        context = this;
        final List<String> your_array_list = new ArrayList<String>();
        your_array_list.add("Informació");
        your_array_list.add("Preguntes freqüents");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                your_array_list );
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s  = your_array_list.get(position);
                if(s.equals("Informació")){
                    Intent i = new Intent(context,About.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(context,Ajuda.class);
                    startActivity(i);
                }
            }
        });

    }
}
