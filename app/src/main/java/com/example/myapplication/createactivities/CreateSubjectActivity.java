package com.example.myapplication.createactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.example.myapplication.objectclasses.Subject;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CreateSubjectActivity extends AppCompatActivity {
    private TextInputEditText hintTextInputEditText;
    private ArrayList<String> allSubjects;
    private DatabaseReference reference;
    private int sortOrder = 0;
    Context c = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);
        String user = getIntent().getExtras().getString("user");

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        hintTextInputEditText = findViewById(R.id.enterEditText);
    }

    public void closeWindow(View view){
        this.finish();
    }

    public void saveSubject(View view){
        String newSubject = Objects.requireNonNull(hintTextInputEditText.getText()).toString();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (newSubject.trim().length() > 0) {
                    if (!checkForIllegalCharacters(newSubject)) {
                        Toast.makeText(c, "Nicht erlaubte Zeichen in Fachbezeichnung:  . , $ , # , [ , ] , / ,", Toast.LENGTH_SHORT).show();
                    } else {
                        sortOrder = (int) (snapshot.child("subject_sorting").getChildrenCount());
                        allSubjects = new ArrayList<>();
                        for (int i=0; i<sortOrder; i++) {
                            allSubjects.add(snapshot.child("subject_sorting").child(String.valueOf(i)).getValue(String.class));
                        }
                        if (allSubjects.contains(newSubject)){
                            Toast.makeText(c, "Fach existiert bereits!", Toast.LENGTH_SHORT).show();
                        } else {
                            reference.child("subject_sorting").child(String.valueOf(sortOrder)).setValue(newSubject);
                        }
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        if (checkForIllegalCharacters(newSubject)) {
            this.finish();
        }
    }

    public boolean checkForIllegalCharacters(String s) {
        List<String> illegalChars = Arrays.asList(".", "$", "[", "]" , "#", "/");
        for (String c : illegalChars) {
            if (s.contains(c)) {
                return false;
            }
        }
        return true;
    }
}