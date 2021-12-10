package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Kartenuebersicht extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> checkeditems = new ArrayList<String>();
    String themenname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartenuebersicht);

        getListItems();
        fillListView();

        this.themenname = getIntent().getExtras().getString("Themenname");
        TextView textview = (TextView) findViewById(R.id.themenname);
        textview.setText(themenname);
    }

    public void getListItems(){
        items.add("Karte1");
        items.add("Karte2");
        items.add("Karte3");
        items.add("Karte4");
    }

    public void fillListView(){
        ListView listview = (ListView) findViewById(R.id.kartenliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        ListviewHelper fachview = new ListviewHelper(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
    }

    public void zurueck(View view) {
        Intent i = new Intent(this, Fachuebersicht.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void newKarte(View view){
        Intent i = new Intent(this, Kartenerstellung.class);
        i.putExtra("Themenname", this.themenname);
        startActivityForResult(i, REQUESTCODE);
    }


    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        if (checkeditems.size() == 0) {
            System.out.println("errormessage: Bitte  ein Fach auswählen");
        }
        else if(checkeditems.size() == 1 ){
            System.out.println("Should open KartendarstellungLernmodus");
        }else{
            System.out.println("errormessage: Bitte nur ein Fach auswählen");
        }
    }
}