package com.example.myapplication.createactivities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateTopicActivity extends AppCompatActivity {
    private TextInputEditText hintTextInputEditText;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects;
    private Spinner subjectSpinner;
    private IntentHelper ih;
    private int sortOrder;
    private Boolean duplicate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic);

        String user = getIntent().getExtras().getString("user");
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
        String newTopic = hintTextInputEditText.getText().toString();
        String selectedSubject = subjectSpinner.getSelectedItem().toString();
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (newTopic.trim().length() > 0) {
                    duplicate = false;
                    for (DataSnapshot subjectSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot topicSnapshot : subjectSnapshot.child("sorting").getChildren()) {
                            if (topicSnapshot.getValue(String.class).equals(newTopic)) {
                                duplicate = true;
                                break;
                            }
                        }
                    }
                    if (!checkForIllegalCharacters(newTopic)) {
                        Toast.makeText(getApplicationContext(), "Nicht erlaubte Zeichen in Themabezeichnung:  . , $ , # , [ , ] , / ,", Toast.LENGTH_SHORT).show();
                    } else if (!duplicate){
                        sortOrder = (int) snapshot.child(selectedSubject).child("sorting").getChildrenCount();
                        reference.child(selectedSubject).child("sorting").child(String.valueOf(sortOrder)).setValue(newTopic);
                    } else {
                        Toast.makeText(getApplicationContext(), "Thema existiert bereits!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        if (checkForIllegalCharacters(newTopic)) {
            this.finish();
        }
    }
    public void closeWindow(View view){
        ih.goToTopicOverview(checkedSubjects);
    }

    private boolean checkForIllegalCharacters(String s) {
        List<String> illegalChars = Arrays.asList(".", "$", "[", "]" , "#", "/");
        for (String c : illegalChars) {
            if (s.contains(c)) {
                return false;
            }
        }
        return true;
    }
}