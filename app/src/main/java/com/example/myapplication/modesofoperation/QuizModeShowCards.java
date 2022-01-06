package com.example.myapplication.modesofoperation;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.overviewactivities.ShowSubjectsActivity;

public class QuizModeShowCards extends AppCompatActivity {
    private static final int REQUESTCODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode_show_cards);

    }

    public void goToPrevious(View view){
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void rightAnswer(View view) {
        // Lernstufe hoch setzen
        System.out.println("gewusst");
    }

    public void wrongAnswer(View view) {
        // Cornelia muss hier Karte in Lernstufe 0 schieben
        System.out.println("nicht gewusst");
    }

    public void partiallyRight(View view) {
        // Cornelia muss hier Karte in aktueller Lernstufe lassen
        System.out.println("teilweise gewusst");
    }
}