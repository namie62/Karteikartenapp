package com.example.myapplication.createactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateTopicPopUp extends AppCompatActivity {

    Button buttonabbrechen;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
//    this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
//    this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_pop_up);

        Spinner spinnerFach = findViewById(R.id.spinner_Fachauswählen);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Fachauswählen, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerFach.setAdapter(adapter);

        buttonabbrechen = (Button) findViewById(R.id.cancel_btn);
        buttonabbrechen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });
    }
    public void closeWindow(){
        this.finish();
    }
}