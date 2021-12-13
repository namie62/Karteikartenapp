package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ThemenuebersichtsActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> checkeditems = new ArrayList<String>();
    String fachname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themenuebersicht);
        getListItems();
        fillListView();

        this.fachname = getIntent().getExtras().getString("Fachname");
        TextView textview = (TextView) findViewById(R.id.fachname);
        textview.setText(this.fachname);

    }

    public void getListItems(){
        items.add("Thema1");
        items.add("Thema2");
        items.add("Thema3");
        items.add("Thema4");
        items.add("Thema5");
    }
    public void fillListView(){
        ListView listview = (ListView) findViewById(R.id.themenliste);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, items);
        ListviewHelperClass fachview = new ListviewHelperClass(listview, arrayAdapter, items);
        checkeditems = fachview.getCheckeditems();
        System.out.println(checkeditems);
    }


    public void zurueck(View view) {
        Intent i = new Intent(this, FachuebersichtsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        if (checkeditems.size() == 0) {
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte ein Thema auswählen.");
            startActivity(popupwindow);

        }
        else if(checkeditems.size() == 1 ){
            System.out.println("Should open");
            Intent i = new Intent(this, KartenuebersichtsActivity.class);
            i.putExtra("Themenname", checkeditems.get(0));
            i.putExtra("Fachname", fachname);
            startActivityForResult(i, REQUESTCODE);
        }else{
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte ein Thema auswählen.");
            startActivity(popupwindow);

        }
    }

    public void starteLernmodus(View view){
        if (checkeditems.size() != 0){
            Intent lernmodus = new Intent(this, Kartenanzeige_Lernmodus.class);
            lernmodus.putStringArrayListExtra("Themenliste", checkeditems);
            lernmodus.putExtra("Fachname", this.fachname);
            lernmodus.putExtra("Abfrage", "Themenuebersicht");
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            //popupwindow.putExtra("Abfrage", "Themenuebersicht");
            startActivity(popupwindow);
        }
    }
    public void starteAbfrage(View view){
        if (checkeditems.size() != 0){
            Intent lernmodus = new Intent(this, FlipCard.class);
            lernmodus.putStringArrayListExtra("Themenliste", checkeditems);
            lernmodus.putExtra("Fachname", this.fachname);
            lernmodus.putExtra("Abfrage", "Themenuebersicht");
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            Intent popupwindow = new Intent(this, HinweisPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Fach auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
    }
    public void neuesThema(View view){
        Intent popupeingabe = new Intent(this, Fach_oder_Thema_erstellen_PopUp.class);
        popupeingabe.putExtra("Kategorie", "Thema");
        startActivity(popupeingabe);
    }
}