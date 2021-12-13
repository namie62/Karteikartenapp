package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class KartenuebersichtsActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> checkeditems = new ArrayList<String>();
    String themenname;
    String fachname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartenuebersicht);
        getListItems();
        fillListView();
        this.themenname = getIntent().getExtras().getString("Themenname");
        this.fachname = getIntent().getExtras().getString("Fachname");
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
        ListviewHelperClass fachview = new ListviewHelperClass(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
    }

    public void zurueck(View view) {
        Intent i = new Intent(this, FachuebersichtsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void newKarte(View view){
        Intent i = new Intent(this, KartenerstellungsActivity.class);
        i.putExtra("Themenname", this.themenname);
        startActivityForResult(i, REQUESTCODE);
    }

    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        if (checkeditems.size() == 0) {
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Karte auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
        else if(checkeditems.size() == 1 ){

            //KartensammlerfürAnzeigeClass lernmodus = new KartensammlerfürAnzeigeClass();
            //lernmodus.starteLernmodus("Fachuebersicht", checkeditems, themen, karten);
            Intent lernmodus = new Intent(this, Kartenanzeige_Lernmodus.class);
            lernmodus.putStringArrayListExtra("Kartenliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Kartenuebersicht");
            startActivityForResult(lernmodus, REQUESTCODE);
            }
        else{
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte nur eine Karte auswählen.");
            startActivity(popupwindow);
        }
    }

    public void starteLernmodus(View view){
        if (checkeditems.size() != 0){
            Intent lernmodus = new Intent(this, Kartenanzeige_Lernmodus.class);
            lernmodus.putStringArrayListExtra("Kartenliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Kartenuebersicht");
            lernmodus.putExtra("Themenname", themenname);
            lernmodus.putExtra("Fachname", fachname);
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Karte auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
    }
    public void starteAbfrage(View view){
        if (checkeditems.size() != 0){
            Intent lernmodus = new Intent(this, FlipCard.class);
            lernmodus.putStringArrayListExtra("Kartenliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Kartenuebersicht");
            lernmodus.putExtra("Themenname", themenname);
            lernmodus.putExtra("Fachname", fachname);
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Karte auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
    }
}