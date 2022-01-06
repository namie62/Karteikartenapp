package com.example.myapplication.createactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateSubjectActivity extends AppCompatActivity {

    Button cancelBtn;
    Button createSubjectBtn;
    TextInputEditText hintTextInputEditText;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        hintTextInputEditText = (TextInputEditText) findViewById(R.id.enterEditText);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });

        createSubjectBtn = (Button)  findViewById(R.id.create_subject_btn);
        createSubjectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {saveSubject();}
        });
    }

    public void closeWindow(){
        this.finish();
    }
    public void saveSubject(){
        String newSubject = (String) hintTextInputEditText.getText().toString();
        if (newSubject.trim().length() > 0) {
            this.reference.child("subjects").child(newSubject).child("topics").setValue("[]");
        }
        this.finish();
    }
}