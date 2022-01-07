package com.example.myapplication.overviewactivities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.popups.HintPopUpActivity;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.example.myapplication.modesofoperation.QuizModeShowCards;
import com.example.myapplication.modesofoperation.StudyModeShowCards;
import com.example.myapplication.createactivities.CreateTopicActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class ShowTopicsActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> checkedSubjects;
    ArrayList<String> checkedTopics;
    FirebaseDatabase flashcardDB;
    DatabaseReference reference;
    ListView listView;
    Context applicationContext;
    ArrayList<String> showObjects;
    ArrayAdapter<String> adapter;
    IntentHelper ih;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topics);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        this.ih = new IntentHelper(this);

        this.checkedSubjects = getIntent().getExtras().getStringArrayList("checkedSubjects");
        this.listView = findViewById(R.id.topics_listView);
        this.applicationContext = getApplicationContext();
        this.showObjects = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, showObjects);
        reference.child("subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showObjects.clear();
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        for (DataSnapshot dataSnapshot : snapshot.child(subject).child("topics").getChildren()){
                            String nameFromDB = dataSnapshot.getKey();
                            showObjects.add(nameFromDB);
                        }
                    }
                    listView.setAdapter(adapter);
                    ListviewHelperClass subjectView = new ListviewHelperClass(listView, showObjects);
                    checkedTopics = subjectView.getCheckeditems();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        TextView textView = (TextView) findViewById(R.id.subject_name_textView);
        textView.setText(checkedSubjects.toString()); //vlt noch hübscher machen
    }


    public void goToPrevious(View view) {
        ih.goToStartMenu();
    }

    public void goToCards(View view) {  //öffnet Kartenerstellung
        if (checkedTopics.size() == 0) {
            ih.openPopUp(2);
        }
        else {
            ih.goToCardOverview(checkedSubjects, checkedTopics);
        }
    }

    public void startStudyMode(View view){
        if (checkedTopics.size() == 0) {
            ih.openPopUp(2);
        }
        else {
            ih.startStudyMode(0, checkedSubjects, checkedTopics);
        }
    }

    public void startQuiz(View view){
        if (checkedTopics.size() == 0) {
            ih.openPopUp(2);
        } else {
            ih.startQuizmode(0, checkedSubjects, checkedTopics);
        }
    }

    public void createTopic(View view){
        ih.newTopic(checkedSubjects);
    }

    public void editTopic(View view){
        if (checkedTopics.size() == 0){
            nothingSelectedError();
        }else if (checkedTopics.size() == 1){
            Intent editTopic = new Intent(this, ShowCardsActivity.class);
            editTopic.putExtra("Thema", checkedTopics.get(0));
            startActivity(editTopic);
        }else {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte nur 1 Thema zur Bearbeitung auswählen.");
        }
    }

    public void nothingSelectedError() {
        Intent popupWindow = new Intent(this, HintPopUpActivity.class);
        popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
        popupWindow.putStringArrayListExtra("checkedSubjects", checkedSubjects);
        startActivity(popupWindow);
    }
}