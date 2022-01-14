package com.example.myapplication.editactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.objectclasses.Subject;
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
    private ArrayList<String> allSubjects;
    private int oldIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        this.user = getIntent().getExtras().getString("user");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.allSubjects = getIntent().getExtras().getStringArrayList("checkedSubjects");
        this.ih = new IntentHelper(this, user);

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);
        this.listView = findViewById(R.id.subjects_listView);
        this.oldIndex = allSubjects.indexOf(selectedSubject);

        subjectNameEditText = findViewById(R.id.subject_name_editText);
        subjectNameEditText.setText(selectedSubject);

        subjectOldPositionTextView = findViewById(R.id.subject_old_position_textView);
        subjectOldPositionTextView.setText(String.valueOf(oldIndex + 1));

        subjectNewPositionEditText = findViewById(R.id.subject_new_position_editTextNumber);
    }

    public void saveChanges(View view) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newSubjectName = subjectNameEditText.getText().toString();
                int newIndex =  Integer.parseInt(String.valueOf(subjectNewPositionEditText.getText())) - 1;
                if (newIndex < allSubjects.size()) {
                    if (newSubjectName.equals(selectedSubject)) {
                        allSubjects.remove(selectedSubject);
                        allSubjects.add(newIndex, selectedSubject);
                        for (int i=0; i<allSubjects.size(); i++) {
                            reference.child("subject_sorting").child(String.valueOf(i)).setValue(allSubjects.get(i));
                        }
                    } else {
                        if (newIndex<allSubjects.size()) {
                            reference.child(newSubjectName).setValue(snapshot.child(selectedSubject).getValue());
                        }
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}