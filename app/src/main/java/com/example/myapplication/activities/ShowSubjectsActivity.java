package com.example.myapplication.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowSubjectsActivity extends AppCompatActivity {   //später dann durch DB iterieren um Fächer zu holen

    private static final int REQUESTCODE = 1;
    ArrayList<String> items = new ArrayList<String>();
    boolean[] checkchecker;
    ArrayList<String> checkedSubjects = new ArrayList<String>();
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
    private ListView listView;
    private Context applicationContext;
    private ArrayList<String> allSubjects;
    ArrayAdapter<String> adapter;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subjects);
        this.flashcardDB = getIntent().getExtras().getParcelable("database");
        this.reference = getIntent().getExtras().getParcelable("reference");
//        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
//        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen
        this.listView = findViewById(R.id.subjects_list_view);
        this.applicationContext = getApplicationContext();
        this.allSubjects = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, allSubjects);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allSubjects.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        allSubjects.add(nameFromDB);
                    }
                    listView.setAdapter(adapter);
                    ListviewHelperClass subjectView = new ListviewHelperClass(listView, adapter, allSubjects);
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
            System.out.println(checkedSubjects.toString());
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPopUp", "Bitte ein Fach auswählen.");
            startActivity(popupwindow);
        }
        else if(checkedSubjects.size() >0 ){
            Intent i = new Intent(this, ShowTopicsActivity.class);
            i.putExtra("database", (Parcelable) flashcardDB);
            i.putExtra("reference", (Parcelable) reference);
            i.putExtra("Subjects", checkedSubjects);
            startActivityForResult(i, REQUESTCODE);

        } /*else if (checkeditems.size() > 1) {
            System.out.println("hadskfhäa");
            Intent popupwindow = new Intent(this, HintPopUpActivity.class);
            popupwindow.putExtra("InfotextPoUp", "Bitte nur 1 Fach auswählen.");
            startActivity(popupwindow);
        }*/
    }


    public void startStudyMode(View view){
        if (checkedSubjects.size() != 0){
            //KartensammlerfürAnzeigeClass studyMode = new KartensammlerfürAnzeigeClass();
            //studyMode.starteLernmodus("Fachuebersicht", checkeditems, themen, karten);
            Intent studyMode = new Intent(this, StudyModeShowCards.class);
            studyMode.putStringArrayListExtra("Faecherliste", checkedSubjects);
            studyMode.putExtra("Abfrage", "Fachuebersicht");
            startActivityForResult(studyMode, REQUESTCODE);
        }
        else{
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Fach auswählen.");
            // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }
    }

    public void startTestMode(View view){
        if (checkedSubjects.size() != 0){
            Intent testMode = new Intent(this, QuizModeShowCards.class);
            testMode.putStringArrayListExtra("Faecherliste", checkedSubjects);
            testMode.putExtra("Abfrage", "Fachuebersicht");
            startActivityForResult(testMode, REQUESTCODE);
    }
        else {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte mindestens 1 Fach auswählen.");
           // popupWindow.putExtra("Abfrage", "Fachuebersicht");
            startActivity(popupWindow);
        }
    }
    public void newSubject(View view){
        Intent entryPopup = new Intent(this, CreateSubjectOrTopicPopUpActivity.class);
        entryPopup.putExtra("Kategorie", "Fach");
        startActivity(entryPopup);
    }
}