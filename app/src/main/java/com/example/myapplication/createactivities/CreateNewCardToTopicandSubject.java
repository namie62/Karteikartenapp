package com.example.myapplication.createactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.myapplication.R;

public class CreateNewCardToTopicandSubject extends AppCompatActivity {
    Button buttonabbrechen;
    Button buttonKarteerstellen;
    String fachname;
    String themenname;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card_to_topicand_subject);

        Spinner spinnerFach = findViewById(R.id.select_subject_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Fachauswählen, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerFach.setAdapter(adapter);

        Spinner spinnerThema = findViewById(R.id.spinner_Themaauswählen);
        ArrayAdapter<CharSequence> adapterthema=ArrayAdapter.createFromResource(this, R.array.Themaauswählen, android.R.layout.simple_spinner_item);
        adapterthema.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerFach.setAdapter(adapterthema);


        //String fachname = spinnerFach.getSelectedItem().toString();
        //String themenname = spinnerThema.getSelectedItem().toString();

        buttonabbrechen = (Button) findViewById(R.id.cancel_btn);
        buttonabbrechen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });
    }

    public void karteerstellen(View view){
        Intent karte = new Intent(this, CreateCard.class);
        karte.putExtra("Fachname", fachname);
        karte.putExtra("Themenname", themenname);
        startActivity(karte);
    }

    public void closeWindow(){
        this.finish();
    }



}