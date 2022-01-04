package com.example.myapplication.activities;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowTopicsActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> checkedSubjects;
    ArrayList<String> topicsFromCheckedSubjects = new ArrayList<>();
    ArrayList<String> checkedTopics;
    ArrayList<String> subjectName = new ArrayList<String>();
    FirebaseDatabase flashcardDB;
    DatabaseReference reference;
    ListView listView;
    Context applicationContext;
    ArrayList<String> showObjects;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topics);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

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
                    checkedSubjects = subjectView.getCheckeditems();
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
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void goToNext(View view) {  //öffnet Kartenerstellung
        if (checkedTopics.size() == 0) {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte ein Thema auswählen.");
            startActivity(popupWindow);
        }
        else if(checkedTopics.size() > 0 ){
            System.out.println("Should open");
            Intent i = new Intent(this, CardsOverviewActivity.class);
            i.putExtra("Themenname", checkedTopics);
            i.putExtra("Fachname", subjectName);
            startActivityForResult(i, REQUESTCODE);
        }
    }

    public void startStudyMode(View view){
        if (checkedTopics.size() != 0){
            Intent studyMode = new Intent(this, StudyModeShowCards.class);
            studyMode.putStringArrayListExtra("Themenliste", checkedTopics);
            studyMode.putExtra("Fachname", this.subjectName);
            studyMode.putExtra("Abfrage", "Themenuebersicht");
            startActivityForResult(studyMode, REQUESTCODE);
        }
        else{
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            //popupWindow.putExtra("Abfrage", "Themenuebersicht");
            startActivity(popupWindow);
        }
    }
    public void startQuiz(View view){
        if (checkedTopics.size() != 0){
            Intent quizMode = new Intent(this, QuizModeShowCards.class);
            quizMode.putStringArrayListExtra("Themenliste", checkedTopics);
            quizMode.putExtra("Fachname", this.subjectName);
            quizMode.putExtra("Abfrage", "Themenuebersicht");
            startActivityForResult(quizMode, REQUESTCODE);
        }
        else{
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }
    }
    public void newTopic(View view){
        Intent entryPopup = new Intent(this, CreateSubjectOrTopicPopUpActivity.class);
        entryPopup.putExtra("Kategorie", "Thema");
        startActivity(entryPopup);
    }

    public void editTopic(View view){
        if (checkedTopics.size() == 0){
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Thema auswählen.");
            // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }else if (checkedTopics.size() == 1){
            Intent editTopic = new Intent(this, CardsOverviewActivity.class);
            editTopic.putExtra("Thema", checkedTopics.get(0));
            startActivity(editTopic);
        }else {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte nur 1 Thema zur Bearbeitung auswählen.");
        }
    }
}