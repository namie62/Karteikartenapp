package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fachuebersicht extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;
    public  String[] items = {"Fach1", "Fach2", "Fach3", "Fach4"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fachuebersicht);
        fillListView();
    }

    public void fillListView(){
        ListView listview = (ListView) findViewById(R.id.fachliste);
        listview.setEnabled(true);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,items);
        System.out.println(arrayAdapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listview.setAdapter(arrayAdapter);
    }

    public void onClick(View view) {  //öffnet Themenübersicht
        Intent i = new Intent(this, Themenuebersicht.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void zurueck(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }
}