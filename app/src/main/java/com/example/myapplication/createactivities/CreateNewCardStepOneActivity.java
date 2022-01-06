package com.example.myapplication.createactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activities.ListviewHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateNewCardStepOneActivity extends AppCompatActivity {
    Button cancelBtn;
    Button createCardBtn;
    Spinner subjectSpinner;
    Spinner topicSpinner;
    FirebaseDatabase flashcardDB;
    DatabaseReference reference;
    ArrayList<String> checkedSubjects;
    ArrayList<String> checkedTopics;
    ArrayList<String> topicsFromSelectedSubject = new ArrayList<>();
    String selectedSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card_step_one);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");
        this.checkedTopics = getIntent().getStringArrayListExtra("checkedTopics");
        ArrayAdapter<String> topicAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, topicsFromSelectedSubject);


        this.subjectSpinner = findViewById(R.id.select_subject_spinner);
        ArrayAdapter<String> subjectAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, checkedSubjects);
        subjectSpinner.setAdapter(subjectAdapter);

        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedSubject = (String) subjectSpinner.getSelectedItem();
                reference.child("subjects").child(selectedSubject).child("topics").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            topicsFromSelectedSubject.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if (checkedTopics.contains(dataSnapshot.getKey())){
                                    topicsFromSelectedSubject.add(dataSnapshot.getKey());
                                }
                            }
                        }
                        topicSpinner = findViewById(R.id.select_topic_spinner);
                        topicSpinner.setAdapter(topicAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }

        });



        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });

        createCardBtn = (Button) findViewById((R.id.create_card_btn));
        createCardBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) { createCard(); }
        });
    }

    public void createCard(){
        String selectedTopic = (String) topicSpinner.getSelectedItem();
        if (selectedSubject != null && selectedTopic != null) {
            Intent card = new Intent(this, CreateCard.class);
            card.putExtra("selectedSubject",  selectedSubject);
            card.putExtra("selectedTopic",  selectedTopic);
            startActivity(card);
        }
    }

    public void closeWindow(){
        this.finish();
    }



}