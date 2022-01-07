package com.example.myapplication.createactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateTopicActivity extends AppCompatActivity {
    TextInputEditText hintTextInputEditText;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
    ArrayList<String> checkedSubjects;
    Spinner subjectSpinner;
    IntentHelper ih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        this.ih = new IntentHelper(this);
        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");

        this.subjectSpinner = findViewById(R.id.select_subject_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, checkedSubjects);
        subjectSpinner.setAdapter(adapter);

        hintTextInputEditText = (TextInputEditText) findViewById(R.id.enterEditText);
    }
    public void saveTopic(View view){
        String newTopic = (String) hintTextInputEditText.getText().toString();
        String selectedSubject = (String) subjectSpinner.getSelectedItem().toString();
        if (newTopic.trim().length() > 0) {
            this.reference.child("subjects").child(selectedSubject).child("topics").child(newTopic).child("cards").setValue("[]");
        }
        ih.goToTopicOverview(checkedSubjects);
    }
    public void closeWindow(View view){
        ih.goToTopicOverview(checkedSubjects);
    }
}