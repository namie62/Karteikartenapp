package com.example.myapplication.createactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChooseSubjectAndTopicForNewCardActivity extends AppCompatActivity {
    private Spinner subjectSpinner;
    private Spinner topicSpinner;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects;
    private ArrayList<String> checkedTopics;
    private String selectedSubject;
    private IntentHelper ih;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_subject_and_topic_for_new_card);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        ArrayList<String> topicsFromSelectedSubject = new ArrayList<>();

        this.ih = new IntentHelper(this, user);
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
                reference.child(selectedSubject).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            topicsFromSelectedSubject.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if (checkedTopics.contains(dataSnapshot.getValue(String.class))){
                                    topicsFromSelectedSubject.add(dataSnapshot.getValue(String.class));
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
    }

    public void createCard(View view){
        String selectedTopic = (String) topicSpinner.getSelectedItem();
        if (selectedSubject != null && selectedTopic != null) {
            ih.newCard(selectedSubject, selectedTopic, checkedSubjects, checkedTopics);
        }
    }

    public void closeWindow(View view){
        ih.goToCardOverview(checkedSubjects, checkedTopics);
    }



}