package com.example.myapplication.modesofoperation;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.overviewactivities.ShowSubjectsActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class QuizModeShowCards extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    private IntentHelper ih;
    private String user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode_show_cards);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);



    }

    public void goToPrevious(View view){
        ih.goToStartMenu(this.user);
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