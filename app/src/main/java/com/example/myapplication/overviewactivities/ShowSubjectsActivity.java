package com.example.myapplication.overviewactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowSubjectsActivity extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen private static final int REQUESTCODE = 1;
    private ArrayList<String> checkedSubjects = new ArrayList<>();
    private ListView listView;
    private Context applicationContext;
    private ArrayList<String> showObjects;
    private ArrayAdapter<String> adapter;
    private IntentHelper ih;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subjects);

        this.user = getIntent().getExtras().getString("user");
        this.ih = new IntentHelper(this, user);

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        this.listView = findViewById(R.id.subjects_listView);
        this.applicationContext = getApplicationContext();
        this.showObjects = new ArrayList<>();

        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, showObjects);


        reference.child("subject_sorting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showObjects.clear();
                if (snapshot.exists()) {
                    int max = (int) snapshot.getChildrenCount();
                    for (int i=0; i<max; i++) {
                        String nameFromDB = snapshot.child(String.valueOf(i)).getValue(String.class);
                        showObjects.add(nameFromDB);
                    }
                    listView.setAdapter(adapter);
                    ListviewHelperClass subjectView = new ListviewHelperClass(listView, showObjects);
                    checkedSubjects = subjectView.getCheckeditems();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goToPrevious(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void goToTopics(View view) {
        if (checkedSubjects.size() == 0) {
            ih.openPopUp(1);
        } else {
            ih.goToTopicOverview(checkedSubjects);
        }
    }

    public void startStudyMode(View view){
        if (checkedSubjects.size() != 0){
            ih.startStudyMode(0, checkedSubjects);
        }
        else{
            ih.openPopUp(1);
        }
    }

    public void startTestMode(View view) {
        if (checkedSubjects.size() != 0){
            ih.startQuizmode(0, checkedSubjects);
    }
        else {
            ih.openPopUp(0);
        }
    }

    public void newSubject(View view){
        ih.newSubject(user);
    }
}