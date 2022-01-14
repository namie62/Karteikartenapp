package com.example.myapplication.editactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

public class EditSubjectActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private String selectedSubject;
    private ListView listView;
    private IntentHelper ih;
    private String user;
    private EditText subjectNameEditText, subjectNewPositionEditText;
    private TextView subjectOldPositionTextView;
    private ArrayList<String> checkedSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        this.user = getIntent().getExtras().getString("user");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.checkedSubjects = getIntent().getExtras().getStringArrayList("checkedSubjects");
        this.ih = new IntentHelper(this, user);

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.listView = findViewById(R.id.subjects_listView);

        subjectNameEditText = findViewById(R.id.subject_edit_text);
        subjectNameEditText.setText(selectedSubject);

        subjectOldPositionTextView = findViewById(R.id.subject_old_position_textView);
        subjectOldPositionTextView.setText(String.valueOf(checkedSubjects.indexOf(selectedSubject) + 1));
    }


}