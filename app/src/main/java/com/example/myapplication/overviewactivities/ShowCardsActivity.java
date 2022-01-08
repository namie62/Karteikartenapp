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
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.example.myapplication.modesofoperation.StudyModeShowCards;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowCardsActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    ArrayList<String> checkedCards = new ArrayList<>();
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
        setContentView(R.layout.activity_show_cards);
        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        this.ih = new IntentHelper(this);
        this.checkedSubjects = getIntent().getExtras().getStringArrayList("checkedSubjects");
        this.checkedTopics = getIntent().getExtras().getStringArrayList("checkedTopics");

        this.listView = findViewById(R.id.cards_listView);
        this.applicationContext = getApplicationContext();
        this.showObjects = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, showObjects);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                showObjects.clear();
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        for (String topic : checkedTopics) {
                            for (DataSnapshot dataSnapshot : snapshot.child(subject).child(topic).getChildren()){
                                if (!dataSnapshot.getKey().equals("sortOrder")) {
                                    String nameFromDB = dataSnapshot.child("front").getValue(String.class);
                                    showObjects.add(nameFromDB);
                                }
                            }
                        }
                    }
                    listView.setAdapter(adapter);
                    ListviewHelperClass cardView = new ListviewHelperClass(listView, showObjects);
                    checkedCards = cardView.getCheckeditems();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        TextView textView = (TextView) findViewById(R.id.topicsTextView);
        textView.setText(checkedTopics.toString());
    }

    public void goToPrevious(View view) {
        ih.goToTopicOverview(checkedSubjects);
    }

    public void createCard(View view){
        ih.chooseCategoriesForNewCard(checkedSubjects, checkedTopics);
    }

    public void vorwaerts(View view) {  //öffnet Kartenerstellung
        if (checkedCards.size() == 0) {
            ih.openPopUp(3);
        } else {


            //KartensammlerfürAnzeigeClass lernmodus = new KartensammlerfürAnzeigeClass();
            //lernmodus.starteLernmodus("Fachuebersicht", checkeditems, themen, karten);
            Intent lernmodus = new Intent(this, StudyModeShowCards.class);
            lernmodus.putStringArrayListExtra("checkedCards", checkedCards);
            lernmodus.putStringArrayListExtra("checkedTopics", checkedTopics);
            lernmodus.putStringArrayListExtra("checkedSubjects", checkedSubjects);
            startActivityForResult(lernmodus, REQUESTCODE);
            } /*
        else{
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte nur eine Karte auswählen.");
            startActivity(popupwindow);
        }*/
    }

    public void startStudyMode(View view){
        if (checkedCards.size() == 0) {
            ih.openPopUp(3);
        } else {
            ih.startStudyMode(0, checkedSubjects, checkedTopics, checkedCards);
        }
    }
    public void startQuizMode(View view){
        if (checkedCards.size() == 0) {
            ih.openPopUp(3);
        } else {
            ih.startQuizmode(0, checkedSubjects, checkedTopics, checkedCards);
        }
    }
}