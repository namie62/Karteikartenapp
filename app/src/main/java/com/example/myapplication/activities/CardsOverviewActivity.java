package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class CardsOverviewActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    ArrayList<String> checkeditems = new ArrayList<String>();
    String topics;
    String subjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cards);
        getListItems();
        fillListView();
        this.topics = getIntent().getExtras().getString("Themenname");
        this.subjects = getIntent().getExtras().getString("Fachname");
        TextView textView = (TextView) findViewById(R.id.topicsTextView);
        textView.setText(topics);
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
        ListviewHelperClass fachview = new ListviewHelperClass(listview, items);
        checkeditems = fachview.getCheckeditems();
    }

    public void zurueck(View view) {
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void newKarte(View view){
        Intent i = new Intent(this, CreateNewCardActivity.class);
        i.putExtra("Themenname", this.topics);
        startActivityForResult(i, REQUESTCODE);
    }

    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        if (checkeditems.size() == 0) {
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Karte auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
        else if(checkeditems.size() >0 ){

            //KartensammlerfürAnzeigeClass lernmodus = new KartensammlerfürAnzeigeClass();
            //lernmodus.starteLernmodus("Fachuebersicht", checkeditems, themen, karten);
            Intent lernmodus = new Intent(this, StudyModeShowCards.class);
            lernmodus.putStringArrayListExtra("Kartenliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Kartenuebersicht");
            startActivityForResult(lernmodus, REQUESTCODE);
            } /*
        else{
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte nur eine Karte auswählen.");
            startActivity(popupwindow);
        }*/
    }

    public void starteLernmodus(View view){
        if (checkeditems.size() != 0){
            Intent lernmodus = new Intent(this, StudyModeShowCards.class);
            lernmodus.putStringArrayListExtra("Kartenliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Kartenuebersicht");
            lernmodus.putExtra("Themenname", topics);
            lernmodus.putExtra("Fachname", subjects);
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Karte auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
    }
    public void starteAbfrage(View view){
        if (checkeditems.size() != 0){
            Intent lernmodus = new Intent(this, QuizModeShowCards.class);
            lernmodus.putStringArrayListExtra("Kartenliste", checkeditems);
            lernmodus.putExtra("Abfrage", "Kartenuebersicht");
            lernmodus.putExtra("Themenname", topics);
            lernmodus.putExtra("Fachname", subjects);
            startActivityForResult(lernmodus, REQUESTCODE);
        }
        else{
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Karte auswählen.");
            // popupwindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupwindow);
        }
    }
}