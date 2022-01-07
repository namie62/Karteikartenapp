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
import com.example.myapplication.popups.HintPopUpActivity;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.example.myapplication.modesofoperation.QuizModeShowCards;
import com.example.myapplication.modesofoperation.StudyModeShowCards;
import com.example.myapplication.createactivities.CreateSubjectActivity;
import com.example.myapplication.objectclasses.Flashcard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowSubjectsActivity extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;
    ArrayList<String> checkedSubjects = new ArrayList<>();
    ArrayList<Flashcard> cardsFromSelectedSubjects = new ArrayList<>();
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
    private ListView listView;
    private Context applicationContext;
    private ArrayList<String> showObjects;
    ArrayAdapter<String> adapter;
    IntentHelper ih;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subjects);
        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen
        this.listView = findViewById(R.id.subjects_listView);
        this.applicationContext = getApplicationContext();
        this.showObjects = new ArrayList<>();

        this.ih = new IntentHelper(this);

        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, showObjects);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showObjects.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.child("subjects").getChildren()) {
                        String nameFromDB = dataSnapshot.getKey();
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
        startActivityForResult(i, REQUESTCODE);
    }

    public void goToTopics(View view){
        if (checkedSubjects.size() == 0) {
            ih.openPopUp(1);
        }
        else {
            ih.goToTopicOverview(checkedSubjects);

        } /*else if (checkeditems.size() > 1) {
            System.out.println("hadskfhäa");
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte nur 1 Fach auswählen.");
            startActivity(popupwindow);
        }*/
    }

    public void startStudyMode(View view){
        if (checkedSubjects.size() != 0){
            ih.startStudyMode(0, checkedSubjects);
        }
        else{
            ih.openPopUp(1);
        }
    }

    public void startTestMode(View view){
        if (checkedSubjects.size() != 0){
            ih.startQuizmode(0, checkedSubjects);
    }
        else {
            ih.openPopUp(0);
        }
    }

    public void newSubject(View view){
        ih.newSubject();
    }
}