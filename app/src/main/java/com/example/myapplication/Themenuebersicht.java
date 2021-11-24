package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Themenuebersicht extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    public  String[] items = {"Thema1", "Thema2", "Thema3", "Thema4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themenuebersicht);
        fillListView();
    }

    public void fillListView(){
        ListView faecheranzeige = (ListView) findViewById(R.id.themenliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice, items);
        faecheranzeige.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        faecheranzeige.setAdapter(arrayAdapter);
    }

//    public void onClick(View view) {  //öffnet Kartenerstellung
//        Intent i = new Intent(this, Kartenerstellung.class);
//        startActivityForResult(i, REQUESTCODE);
//    }

    public void zurueck(View view) {
        Intent i = new Intent(this, Fachuebersicht.class);
        startActivityForResult(i, REQUESTCODE);
    }


    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        Intent i = new Intent(this, Kartenerstellung.class);
        startActivityForResult(i, REQUESTCODE);
    }
}