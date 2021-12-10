package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FachuebersichtsActivity extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

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
        String ueberschrift = "Fächer";
        TextView textview = (TextView) findViewById(R.id.ueberschrift);
        textview.setText(ueberschrift);
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
        ListviewHelperClass fachview = new ListviewHelperClass(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
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
            Intent i = new Intent(this, ThemenuebersichtsActivity.class);
            i.putExtra("Fachname", checkeditems.get(0));
            startActivityForResult(i, REQUESTCODE);
        }else{
            System.out.println("errormessage: Bitte nur ein Fach auswählen");
        }
    }

    public void starteLernmodus(View view){
        if (checkeditems.size() != 0){
            //KartensammlerfürAnzeigeClass lernmodus = new KartensammlerfürAnzeigeClass();
            //lernmodus.starteLernmodus("Fachuebersicht", checkeditems, themen, karten);
            Intent lernmodus = new Intent(this, Kartenanzeige_Lernmodus.class);
            lernmodus.putStringArrayListExtra("Faecherliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Fachuebersicht");
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            System.out.println("Bitte mindestens 1 Fach auswählen");
        }
    }
}