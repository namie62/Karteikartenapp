package com.example.myapplication.createactivities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateTopicPopUpActivity extends AppCompatActivity {


    Button okBtn;
    TextInputEditText hintTextInputEditText;
    String hint;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
    ArrayList<String> checkedSubjects;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_popup);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");
        this.dropdown = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, checkedSubjects);
        this.dropdown.setAdapter(adapter);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -10;

        getWindow().setAttributes(params);
        this.hint = getIntent().getExtras().getString("Kategorie");
        hintTextInputEditText = (TextInputEditText) findViewById(R.id.enterEditText);
        hintTextInputEditText.setHint(hint);
        okBtn = (Button) findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });
    }
    public void closeWindow(){
        String newTopic = (String) hintTextInputEditText.getText().toString();
        String selectedSubject = (String) dropdown.getSelectedItem().toString();
        if (newTopic.trim().length() > 0) {
            this.reference.child("subjects").child(selectedSubject).child("topics").child(newTopic).child("cards").setValue("[]");
        }
        this.finish();
    }
}