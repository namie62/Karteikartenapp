package com.example.myapplication.modesofoperation;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.objectclasses.Flashcard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class QuizModeActivity extends AppCompatActivity {
    private IntentHelper ih;
    private ArrayList<String> checkedSubjects, sortedSubjects, checkedTopics, sortedTopics, checkedCards, sortedCards, uniqueKeys;
    private ArrayList<Flashcard> cards = new ArrayList<>();
    private ArrayList<Flashcard> allCards = new ArrayList<>();
    private int index = 0;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_mode);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);

        Bundle b = getIntent().getExtras();
        this.checkedSubjects = b.getStringArrayList("checkedSubjects");
        this.checkedTopics = b.getStringArrayList("checkedTopics");
        this.checkedCards = b.getStringArrayList("checkedCards");
        sortedSubjects = new ArrayList<>();
        sortedTopics = new ArrayList<>();
        sortedCards = new ArrayList<>();

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int numberOfSubjects = (int) snapshot.child("subject_sorting").getChildrenCount();
                    for (int i = 0; i < numberOfSubjects; i++) {
                        String s = snapshot.child("subject_sorting").child(String.valueOf(i)).getValue(String.class);
                        if (checkedSubjects.contains(s)) {
                            sortedSubjects.add(s);
                        }
                    }
                    for (String subject : sortedSubjects) {
                        int numberOfTopics = (int) snapshot.child(subject).child("sorting").getChildrenCount();
                        for (int i = 0; i < numberOfTopics; i++) {
                            String s = snapshot.child(subject).child("sorting").child(String.valueOf(i)).getValue(String.class);
                            if (checkedTopics == null) {
                                sortedTopics.add(s);
                            } else if (checkedTopics.contains(s)) {
                                sortedTopics.add(s);
                            }
                        }
                        for (String topic : sortedTopics) {
                            int numberOfCards = (int) snapshot.child(subject).child(topic).getChildrenCount();
                            for (int i = 0; i < numberOfCards; i++) {
                                String s = snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class);
                                if (checkedCards == null) {
                                    sortedCards.add(s);
                                } else if (checkedCards.contains(s)) {
                                    sortedCards.add(s);
                                }
                            }
                        }
                    }

                    for (String key : sortedCards) {
                        Flashcard c = snapshot.child("cards").child(key).getValue(Flashcard.class);
                        c.setKey(key);
                        cards.add(c);
                    }
                }
                Collections.shuffle(cards);
                Flashcard card = cards.get(index);
                if (card.getProgress() < 5) {
                    setFrontTextView(card.getFront());
                    setBackTextView(card.getBack());
                    setProgressTextView(card.getProgress());
                } else {
                    nextCard();
                }

            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

    }

    public void setBackTextView(String back){
        TextView textView = findViewById(R.id.card_front);
        textView.setText(back);
    }

    public void setFrontTextView(String front){
        TextView textview = findViewById(R.id.card_back);
        textview.setText(front);
    }

    public void setProgressTextView(int progress) {
        TextView textView = findViewById(R.id.progress_textView);
        textView.setText(String.valueOf(progress));
    }

    public void nextCard() {
        int max = cards.size();
        if (index < max-1) {
            index += 1;
            Flashcard card = cards.get(index);
            setFrontTextView(card.getFront());
            setBackTextView(card.getBack());
        } else {
            ih.goToStartMenu();
        }
    }

    public void goToPrevious(View view){
        ih.goToStartMenu();
    }

    public void rightAnswer(View view) {
        Flashcard flashcard = cards.get(index);
        int progress = flashcard.getProgress();
        String key = flashcard.getKey();
        if (progress < 5 ) {
            reference.child(key).child("progress").setValue(progress+1);
        }
        nextCard();
    }

    public void wrongAnswer(View view) {
        Flashcard flashcard = cards.get(index);
        int progress = flashcard.getProgress();
        String key = flashcard.getKey();
        if (progress > 0) {
            reference.child(key).child("progress").setValue(0);
        }
        nextCard();
    }

    public void partiallyRight(View view) {
        nextCard();
    }
}