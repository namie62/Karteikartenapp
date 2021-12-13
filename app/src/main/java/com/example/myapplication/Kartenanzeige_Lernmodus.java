package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.ArrayList;

public class Kartenanzeige_Lernmodus extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    String antwort ="AntwortDummy";
    String frage=  "FrageDummy";
    String abfrage;
    Integer lernstufe;
    Bitmap grafik;
    KartenClass karte = new KartenClass();
    ArrayList<String> faecher = new ArrayList<String>();
    ArrayList<String> themen = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartenanzeige_lernmodus);

         this.abfrage = getIntent().getExtras().getString("Abfrage");

        // karte.getFrage();
        // karte.getAntwort();

        fill_AntworttextView();
        fill_FragetextView();

    }

    public void fill_AntworttextView(){
        TextView textview = (TextView) findViewById(R.id.InhalttextView);
        textview.setText(antwort);
    }

    public void fill_FragetextView(){
        TextView textview = (TextView) findViewById(R.id.ThementextView);
        textview.setText(frage);
    }

    public void gewusst(View view) {
        // Lernstufe hoch setzen
        System.out.println("gewusst");
    }

    public void nichtgewusst(View view) {
        // Cornelia muss hier Karte in Lernstufe 0 schieben
        System.out.println("nicht gewusst");
    }

    public void teilweisegewusst(View view) {
        // Cornelia muss hier Karte in aktueller Lernstufe lassen
        System.out.println("teilweise gewusst");
    }


    public void zurueck(View view) {
        Intent i = new Intent(this, FachuebersichtsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }
}