package com.example.myapplication.createactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CreateTopicActivity extends AppCompatActivity {
    private TextInputEditText hintTextInputEditText;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects;
    private Spinner subjectSpinner;
    private IntentHelper ih;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);
        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");

        this.subjectSpinner = findViewById(R.id.select_subject_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, checkedSubjects);
        subjectSpinner.setAdapter(adapter);

        hintTextInputEditText = (TextInputEditText) findViewById(R.id.enterEditText);
    }
    public void saveTopic(View view){
        String newTopic = (String) hintTextInputEditText.getText().toString();
        String selectedSubject = (String) subjectSpinner.getSelectedItem().toString();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (newTopic.trim().length() > 0) {
                    int sortOrder = (int) snapshot.child(selectedSubject).getChildrenCount() - 1;
                    reference.child(selectedSubject).child(newTopic).child("sortOrder").setValue(sortOrder);
                    ih.goToTopicOverview(checkedSubjects);
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    public void closeWindow(View view){
        ih.goToTopicOverview(checkedSubjects);
    }
}