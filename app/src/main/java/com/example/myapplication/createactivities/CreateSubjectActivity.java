package com.example.myapplication.createactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.Objects;

public class CreateSubjectActivity extends AppCompatActivity {
    private TextInputEditText hintTextInputEditText;
    private DatabaseReference reference;
    private int sortOrder = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        hintTextInputEditText = (TextInputEditText) findViewById(R.id.enterEditText);
    }

    public void closeWindow(View view){
        this.finish();
    }

    public void saveSubject(View view){
        String newSubject = (String) Objects.requireNonNull(hintTextInputEditText.getText()).toString();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (newSubject.trim().length() > 0) {
                    sortOrder = (int) (snapshot.child("subject_sorting").getChildrenCount());
                    reference.child("subject_sorting").child(String.valueOf(sortOrder)).setValue(newSubject);
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        this.finish();
    }
}