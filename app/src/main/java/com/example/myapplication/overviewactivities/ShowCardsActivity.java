package com.example.myapplication.overviewactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.DeleteStuff;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class ShowCardsActivity extends AppCompatActivity {
    private ArrayList<String> checkedCards = new ArrayList<>();
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects;
    private ArrayList<String> checkedTopics;
    private ListView listView;
    private Context applicationContext;
    private ArrayList<String> showObjects;
    private ArrayAdapter<String> adapter;
    private IntentHelper ih;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cards);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);
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
                            int max = (int) snapshot.child(subject).child(topic).getChildrenCount();
                            for (int i=0; i<max; i++) {
                                String cardpath = snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class);
                                showObjects.add(snapshot.child(cardpath).child("front").getValue(String.class));
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
        TextView textView = (TextView) findViewById(R.id.topics_text_view);
        textView.setText(checkedTopics.toString());
    }



    public void goToPrevious(View view) {
        ih.goToTopicOverview(checkedSubjects);
    }

    public void createCard(View view){
        ih.chooseCategoriesForNewCard(checkedSubjects, checkedTopics);
    }

    public void editCard(View view) {  //öffnet Kartenerstellung
        if (checkedCards.size() != 1) {
            ih.openPopUp(4);
        } else {
            ih.newCard(checkedSubjects, checkedTopics, checkedCards);
            }
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

    public void deleteCard(View view) {
        DeleteStuff ds = new DeleteStuff(getApplicationContext(), reference, checkedSubjects, checkedTopics, checkedCards);
        ds.deleteeee();
    }
}