package com.example.myapplication.createactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class CreateNewSubjectTopicCard extends AppCompatActivity {

    Button buttonNeuesFach;
    Button buttonNeuesThema;
    Button buttonNeueKarte;
    Button buttonAbbrechen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_subject_topic_card);

        buttonNeuesFach = (Button) findViewById(R.id.buttonNeuesFach);
        buttonNeuesThema = (Button) findViewById(R.id.buttonNeuesThema);
        buttonNeueKarte = (Button) findViewById(R.id.buttonNeueKarte);
        buttonAbbrechen = (Button) findViewById(R.id.abbrechen_button);

        buttonAbbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });


        buttonNeuesFach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeuesFach();
            }
        });

        buttonNeuesThema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeuesThema();
            }
        });

        buttonNeueKarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NeueKarte();
            }
        });
    }

    public void closeWindow(){
        this.finish();
    }


    public void NeuesFach() {
        Intent neuesfach = new Intent(this, CreateSubjectActivity.class);
        startActivity(neuesfach);
    }

    public void NeuesThema() {
        Intent neuesthema = new Intent(this, CreateTopicPopUp.class);
        startActivity(neuesthema);
    }

    public void NeueKarte() {
        Intent neuekarte = new Intent (this, CreateNewCardToTopicandSubject.class);
        startActivity(neuekarte);
    }
}
