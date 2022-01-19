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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditSubjectActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private String selectedSubject;
    private EditText subjectNameEditText, subjectNewPositionEditText;
    private ArrayList<String> allSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_subject);

        String user = getIntent().getExtras().getString("user");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.allSubjects = getIntent().getExtras().getStringArrayList("checkedSubjects");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);
        int oldIndex = allSubjects.indexOf(selectedSubject);

        subjectNameEditText = findViewById(R.id.subject_name_editText);
        subjectNameEditText.setText(selectedSubject);

        TextView subjectOldPositionTextView = findViewById(R.id.subject_old_position_textView);
        subjectOldPositionTextView.setText(String.valueOf(oldIndex +1));

        subjectNewPositionEditText = findViewById(R.id.subject_new_position_editTextNumber);
        subjectNewPositionEditText.setText(String.valueOf(oldIndex +1));
    }

    public void saveChanges(View view) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newSubjectName = subjectNameEditText.getText().toString();
                int newIndex =  Integer.parseInt(String.valueOf(subjectNewPositionEditText.getText())) - 1;
                if (newIndex >= allSubjects.size()) {
                    newIndex = allSubjects.size() -1;
                }
                reference.child(newSubjectName).setValue(snapshot.child(selectedSubject).getValue());
                reference.child(selectedSubject).removeValue();
                allSubjects.remove(selectedSubject);
                allSubjects.add(newIndex, newSubjectName);
                for (int i=0; i<allSubjects.size(); i++) {
                    reference.child("subject_sorting").child(String.valueOf(i)).setValue(allSubjects.get(i));
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        this.finish();
    }

    public void cancel(View view) {
        this.finish();
    }
}