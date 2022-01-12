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

public class StudyModeActivity extends AppCompatActivity {
    private ArrayList<String> checkedSubjects, sortedSubjects, checkedTopics, sortedTopics, checkedCards, sortedCards, uniqueKeys;
    private ArrayList<Flashcard> cards = new ArrayList<>();
    private ArrayList<Flashcard> allCards = new ArrayList<>();
    private IntentHelper ih;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_mode);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);

        Bundle b = getIntent().getExtras();
        this.checkedSubjects = b.getStringArrayList("checkedSubjects");
        this.checkedTopics = b.getStringArrayList("checkedTopics");
        this.checkedCards = b.getStringArrayList("checkedCards");
        sortedSubjects = new ArrayList<>();
        sortedTopics = new ArrayList<>();
        sortedCards = new ArrayList<>();
        this.index = getIntent().getIntExtra("index", 0);

        reference.addValueEventListener(new ValueEventListener() {
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
                        Flashcard c = snapshot.child(key).getValue(Flashcard.class);
                        cards.add(c);
                    }
                }
                Flashcard card = cards.get(index);
                fillFrontTextView(card.getFront());
                fillBackTextView(card.getBack());
                setProgressTextView(card.getProgress());
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void fillBackTextView(String back){
        TextView textView = (TextView) findViewById(R.id.back_text_view);
        textView.setText(back);
    }

    public void fillFrontTextView(String front){
        TextView textview = (TextView) findViewById(R.id.front_text_view);
        textview.setText(front);
    }

    public void setProgressTextView(int progress) {
        TextView textView = findViewById(R.id.progress_study_textView);
        textView.setText(String.valueOf(progress));
    }

    public void nextCard(View view) {
        int max = cards.size();
        if (index < max-1) {
            index += 1;
            Flashcard card = cards.get(index);
            fillFrontTextView(card.getFront());
            fillBackTextView(card.getBack());
        } else {
            ih.goToStartMenu();
        }
    }

    public void previousCard(View view) {
        if (index > 0) {
            index -= 1;
            Flashcard card = cards.get(index);
            fillFrontTextView(card.getFront());
            fillBackTextView(card.getBack());
        } else {
            ih.goToStartMenu();
        }
    }

    public void previous(View view) {
        ih.goToStartMenu();
    }
}