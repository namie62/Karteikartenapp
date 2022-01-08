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
import com.example.myapplication.helperclasses.IntentHelper;
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
    private String content ="Inhalt";
    private String topic =  "Themenegebiet";
    private Integer progress;
    private Bitmap img;
    private KartenClass card = new KartenClass();
    private ArrayList<String> checkedSubjects;
    private ArrayList<String> checkedTopics;
    private ArrayList<String> checkedCards;
    private ArrayList<Flashcard> cards = new ArrayList<>();
    private ArrayList<Flashcard> allCards = new ArrayList<>();
    private IntentHelper ih = new IntentHelper(this);
    private Bundle b;
    private int index;
    private int max;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode_show_cards);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        this.b = getIntent().getExtras();
        this.checkedSubjects = b.getStringArrayList("checkedSubjects");
        this.checkedTopics = b.getStringArrayList("checkedTopics");
        this.checkedCards = b.getStringArrayList("checkedCards");

        this.index = getIntent().getIntExtra("index", 0);
        reference.child("subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        DataSnapshot subjectSnapshot = snapshot.child(subject).child("topics");
                        if (checkedTopics != null) {
                            for (String topic : checkedTopics) {
                                DataSnapshot topicSnapshot = subjectSnapshot.child(topic).child("cards");
                                if (checkedCards != null) {
                                    for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                        for (String card : checkedCards) {
                                            if (cardSnapshot.child("front").getValue(String.class).equals(card)) {
                                                fillCardsArray(cardSnapshot);
                                            }
                                        }
//                                        DataSnapshot cardSnapshot = topicSnapshot.child(card);
//                                        fillCardsArray(cardSnapshot);
                                    }
                                } else {
                                    for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                        fillCardsArray(cardSnapshot);
                                    }
                                }
                            }
                        } else {
                            for (DataSnapshot topicSnapshot : subjectSnapshot.getChildren()) {
                                for (DataSnapshot cardSnapshot : topicSnapshot.child("cards").getChildren()) {
                                    fillCardsArray(cardSnapshot);
                                }
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




    public void fillBackTextView(){
        TextView textView = (TextView) findViewById(R.id.back_text_view);
        textView.setText(content);
    }

    public void fillFrontTextView(){
        TextView textview = (TextView) findViewById(R.id.front_text_view);
        textview.setText(topic);
    }

    public void fillCardsArray(DataSnapshot ds) {
        String frontFromDB = ds.child("front").getValue(String.class);
        String backFromDB = ds.child("back").getValue(String.class);
        Integer progressFromDB = ds.child("progress").getValue(Integer.class);
        Flashcard newCard = new Flashcard(frontFromDB, backFromDB, " ", progressFromDB);
        cards.add(newCard);
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