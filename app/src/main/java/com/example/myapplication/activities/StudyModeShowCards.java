package com.example.myapplication.activities;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myapplication.KartenClass;
import com.example.myapplication.R;

import java.util.ArrayList;

public class StudyModeShowCards extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    String content ="Inhalt";
    String topic =  "Themenegebiet";
    String quiz;
    Integer progress;
    Bitmap img;
    KartenClass card = new KartenClass();
    ArrayList<String> subjects = new ArrayList<String>();
    ArrayList<String> topics = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode_show_cards);

         this.quiz = getIntent().getExtras().getString("Abfrage");

        // karte.getFrage();
        // karte.getAntwort();

        fill_backTextView();
        fill_FragetextView();
    }

    public void fill_backTextView(){
        TextView textView = (TextView) findViewById(R.id.contentTextView);
        textView.setText(content);
    }

    public void fill_FragetextView(){
        TextView textview = (TextView) findViewById(R.id.topicTextView);
        textview.setText(topic);
    }

    public void correct(View view) {
        // Lernstufe hoch setzen
        System.out.println("gewusst");
    }

    public void incorrect(View view) {
        // Cornelia muss hier Karte in Lernstufe 0 schieben
        System.out.println("nicht gewusst");
    }

    public void partiallyCorrect(View view) {
        // Cornelia muss hier Karte in aktueller Lernstufe lassen
        System.out.println("teilweise gewusst");
    }

    public void previous(View view) {
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }
}