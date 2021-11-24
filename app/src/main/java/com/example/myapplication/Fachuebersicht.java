package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Fachuebersicht extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    boolean[] checkchecker;
    ArrayList<String> checkeditems = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fachuebersicht);
        getListItems();
        fillListView();
    }

    public void getListItems(){
        items.add("Fach1");
        items.add("Fach2");
        items.add("Fach3");
        items.add("Fach4");
        items.add("Fach5");
    }

    public void fillListView() {
        ListView listview = (ListView) findViewById(R.id.fachliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        // ab da geht klasse los dann

        ListviewHelper fachview = new ListviewHelper(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();



//    public void onClick(View view) {
//        Intent i = new Intent(this, Themenuebersicht.class);
//        startActivityForResult(i, REQUESTCODE);
    }


    public void zurueck(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void vorwaerts(View view){
        if (checkeditems.size() == 0) {
            System.out.println("errormessage: Bitte  ein Fach auswählen");
        }
        else if(checkeditems.size() == 1 ){
            Intent i = new Intent(this, Themenuebersicht.class);
            i.putExtra("Fachname", checkeditems);
            startActivityForResult(i, REQUESTCODE);
        }else{
            System.out.println("errormessage: Bitte nur ein Fach auswählen");

        }
    }
}