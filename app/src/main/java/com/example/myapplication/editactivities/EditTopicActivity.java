package com.example.myapplication.editactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

public class EditTopicActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private String selectedTopic, selectedSubject, user;
    private ArrayList<String> allTopics;
    private IntentHelper ih;
    private EditText topicNameEditText, topicNewPositionEditText;
    private TextView topicOldPositionTextView;
    private int oldIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_topic);

        this.user = getIntent().getExtras().getString("user");
        this.allTopics = getIntent().getStringArrayListExtra("allTopics");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.selectedTopic = getIntent().getExtras().getString("selectedTopic");
        this.ih = new IntentHelper(this, user);

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);
        this.oldIndex = allTopics.indexOf(selectedTopic);

        topicNameEditText = findViewById(R.id.topic_name_editText);
        topicNameEditText.setText(selectedTopic);

        topicOldPositionTextView = findViewById(R.id.topic_old_position_textView);
        topicOldPositionTextView.setText(String.valueOf(oldIndex+1));

        topicNewPositionEditText = findViewById(R.id.topic_new_position_editTextNumber);
        topicNewPositionEditText.setText(String.valueOf(oldIndex+1));
    }

    public void saveChanges(View view) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newTopicName = topicNameEditText.getText().toString();
                int newIndex =  Integer.parseInt(String.valueOf(topicNewPositionEditText.getText())) - 1;
                if (newIndex >= allTopics.size()) {
                    newIndex = allTopics.size()-1;
                }
                reference.child(selectedSubject).child(newTopicName).setValue(snapshot.child(selectedSubject).child(selectedTopic).getValue());
                reference.child(selectedSubject).child(selectedTopic).removeValue();
                allTopics.remove(selectedTopic);
                allTopics.add(newIndex, newTopicName);
                for (int i=0; i<allTopics.size(); i++) {
                    reference.child(selectedSubject).child("sorting").child(String.valueOf(i)).setValue(allTopics.get(i));
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