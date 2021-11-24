package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fachuebersicht extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;
    public  String[] items = {"Fach1", "Fach2", "Fach3", "Fach4"};
    boolean[] checkchecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fachuebersicht);
        fillListView();
    }

    public void fillListView(){

        ListView listview = (ListView) findViewById(R.id.fachliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,items);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setClickable(true);



        listview.setLongClickable(true);
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                // TODO Auto-generated method stub

                System.out.println("long clicked"+"pos: " + pos);
                return true;
            }
        });

        checkchecker = new boolean[items.length];

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                checkchecker[position] = !checkchecker[position];
                if (checkchecker[position])
                    System.out.println("Das Item"+ items[position] + "wurde angeklickt");
                else
                    System.out.println("Das Item wurde deselected");
            }
        });
        listview.setAdapter(arrayAdapter);
    }

//    public void onClick(View view) {  //öffnet Themenübersicht
//        Intent i = new Intent(this, Themenuebersicht.class);
//        startActivityForResult(i, REQUESTCODE);
//    }

    public void zurueck(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void vorwaerts(View view){
        Intent i = new Intent(this, Themenuebersicht.class);
        startActivityForResult(i, REQUESTCODE);
    }
}