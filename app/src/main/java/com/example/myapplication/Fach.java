package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Fach extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;

    public  String[] items = {"Fach1", "Fach2", "Fach3", "Fach4"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach);
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



    public void onClick(View view) {   // Die Methode openGalery muss in Jennys Knopf onClick rein
        Intent i = new Intent(this, Thema.class);
        startActivityForResult(i, REQUESTCODE);
    }
}