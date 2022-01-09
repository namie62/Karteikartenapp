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

public class StudyModeActivity extends AppCompatActivity {
    private String content;
    private String topic;
    private KartenClass card = new KartenClass();
    private ArrayList<String> checkedSubjects;
    private ArrayList<String> checkedTopics;
    private ArrayList<String> checkedCards;
    private ArrayList<Flashcard> cards = new ArrayList<>();
    private ArrayList<Flashcard> allCards = new ArrayList<>();
    private IntentHelper ih;
    private int index;
    private int max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode_show_cards);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);

        Bundle b = getIntent().getExtras();
        this.checkedSubjects = b.getStringArrayList("checkedSubjects");
        this.checkedTopics = b.getStringArrayList("checkedTopics");
        this.checkedCards = b.getStringArrayList("checkedCards");
        this.index = getIntent().getIntExtra("index", 0);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        DataSnapshot subjectSnapshot = snapshot.child(subject);
                        if (subjectSnapshot.exists() && checkedTopics != null) {
//                            int subjectSortOrder = Integer.parseInt(subjectSnapshot.child("sortOrder").getValue(String.class));
                            for (String topic : checkedTopics) {
                                DataSnapshot topicSnapshot = subjectSnapshot.child(topic);
                                if (topicSnapshot.exists() && checkedCards != null) {
                                    for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                        for (String card : checkedCards) {
                                            if (cardSnapshot.exists()
                                                    && cardSnapshot.child("front").exists()) {
                                                if (cardSnapshot.child("front").getValue(String.class).equals(card)) {
                                                    fillCardsArray(cardSnapshot);
                                                }
                                            }
                                        }
//                                        DataSnapshot cardSnapshot = topicSnapshot.child(card);
//                                        fillCardsArray(cardSnapshot);
                                    }
                                } else {
                                    for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                        if (cardSnapshot.child("front").exists()){
                                            fillCardsArray(cardSnapshot);
                                        }
                                    }
                                }
                            }
                        } else {
                            for (DataSnapshot topicSnapshot : subjectSnapshot.getChildren()) {
                                for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                    if (cardSnapshot.child("front").exists()){
                                        fillCardsArray(cardSnapshot);
                                    }
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
        String backImgFromDB = ds.child("backImg").getValue(String.class);
        Integer progressFromDB = ds.child("progress").getValue(Integer.class);
        Integer sortOrderFromDB = ds.child("sortOrder").getValue(Integer.class);
        if (progressFromDB == null) {
            progressFromDB = 0;
        }
        if (sortOrderFromDB == null) {
            sortOrderFromDB = 0;
        }
        Flashcard newCard = new Flashcard(frontFromDB, backFromDB, backImgFromDB, progressFromDB);
        newCard.setSortOrder(sortOrderFromDB);
        cards.add(newCard);

    }

    public void nextCard(View view) {
        if (index < max-1) {
            ih.nextCardStudyMode(index+1, this.checkedSubjects, this.checkedTopics, this.checkedCards);
        } else {
            ih.goToStartMenu();
        }
    }

    public void previousCard(View view) {
        if (index > 0) {
            ih.nextCardStudyMode(index-1, this.checkedSubjects, this.checkedTopics, this.checkedCards);
        } else {
            ih.goToStartMenu();
        }
    }

    public void previous(View view) {
        ih.goToStartMenu();
    }

    public void goToStart() {
        ih.goToStartMenu();
    }

}