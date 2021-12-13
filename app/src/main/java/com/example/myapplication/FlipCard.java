package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FlipCard extends AppCompatActivity {
    private static final int REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_card);
    }


    public void zurueck(View view){
        Intent i = new Intent(this, FachuebersichtsActivity.class);
        startActivityForResult(i, REQUESTCODE);
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
}