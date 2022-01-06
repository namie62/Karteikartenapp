package com.example.myapplication.modesofoperation;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.KartenClass;
import com.example.myapplication.R;
import com.example.myapplication.objectclasses.Flashcard;
import com.example.myapplication.overviewactivities.ShowSubjectsActivity;
import com.example.myapplication.overviewactivities.ShowTopicsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudyModeShowCards extends AppCompatActivity {
    private static final int REQUESTCODE = 1;
    String content ="Inhalt";
    String topic =  "Themenegebiet";
    Integer progress;
    Bitmap img;
    KartenClass card = new KartenClass();
    ArrayList<String> checkedSubjects;
    ArrayList<String> checkedTopics;
    ArrayList<String> checkedCards;
    ArrayList<Flashcard> cards = new ArrayList<>();
    ArrayList<Flashcard> allCards = new ArrayList<>();
    int index;
    int max;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode_show_cards);
        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");
        this.checkedTopics = getIntent().getStringArrayListExtra("checkedTopics");
        this.checkedCards = getIntent().getStringArrayListExtra("checkedCards");

        this.index = getIntent().getIntExtra("index", 0);
        if (checkedTopics == null) {
            reference.child("subjects").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (String subject : checkedSubjects) {
                            for(DataSnapshot subjectSnapshot : snapshot.child(subject).child("topics").getChildren()) {
                                for(DataSnapshot topicSnapshot : subjectSnapshot.child("cards").getChildren()) {
                                    String frontFromDB = topicSnapshot.child("front").getValue(String.class);
                                    String backFromDB = topicSnapshot.child("back").getValue(String.class);
                                    Integer progressFromDB = topicSnapshot.child("progress").getValue(Integer.class);
                                    Flashcard newCard = new Flashcard(frontFromDB, backFromDB, " ");
                                    newCard.setProgress(progressFromDB);
                                    cards.add(newCard);
                                }
                            }
                        }
                    }

                    max = cards.size();
                    Flashcard card = cards.get(index);
                    content = card.getFront();
                    topic = card.getBack();
                    fillBackTextView();
                    fillFrontTextView();
                }

                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }

            });
        }


    }

    public void fillBackTextView(){
        TextView textView = (TextView) findViewById(R.id.back_text_view);
        textView.setText(content);
    }

    public void fillFrontTextView(){
        TextView textview = (TextView) findViewById(R.id.front_text_view);
        textview.setText(topic);
    }

    public void nextCard(View view) {
        if (index < max-1) {
            Intent i = new Intent(this, StudyModeShowCards.class);
            i.putExtra("index", index + 1);
            i.putStringArrayListExtra("checkedSubjects", checkedSubjects);
            startActivityForResult(i, REQUESTCODE);
        } else {
            Intent i = new Intent(this, ShowSubjectsActivity.class);
            startActivityForResult(i, REQUESTCODE);
        }
    }

    public void previousCard(View view) {
        // Cornelia muss hier Karte in Lernstufe 0 schieben
        Intent i = new Intent(this, StudyModeShowCards.class);
        i.putExtra("index", index-1);
        i.putStringArrayListExtra("checkedSubjects", checkedSubjects);
        startActivityForResult(i, REQUESTCODE);
    }

    public void previous(View view) {
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    public void goToStart() {
        Intent i = new Intent(this, ShowSubjectsActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

}