package com.example.myapplication.helperclasses;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.objectclasses.Flashcard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeleteStuff {

    private ArrayList<String> checkedSubjects, checkedTopics, checkedCards;
    private DatabaseReference reference;
    private String user;
    private Context applicationContext;
    ArrayList<String> remainingSubjects = new ArrayList<>();
    ArrayList<String> remainingCards = new ArrayList<>();

    public DeleteStuff(Context applicationContext, DatabaseReference reference, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics, ArrayList<String> checkedCards) {
        this.reference = reference;
        this.checkedSubjects = checkedSubjects;
        this.checkedTopics = checkedTopics;
        this.checkedCards = checkedCards;
        this.applicationContext = applicationContext;
    }

    public DeleteStuff(Context applicationContext, DatabaseReference reference, ArrayList<String> checkedSubjects, ArrayList<String> checkedTopics) {
        this.reference = reference;
        this.checkedSubjects = checkedSubjects;
        this.checkedTopics = checkedTopics;
        this.applicationContext = applicationContext;
    }

    public DeleteStuff(Context applicationContext, DatabaseReference reference, ArrayList<String> checkedSubjects) {
        this.reference = reference;
        this.checkedSubjects = checkedSubjects;
        this.applicationContext = applicationContext;
    }

    public void deleteeee() {
        ArrayList<String> sortedSubjects = new ArrayList<>();
        ArrayList<String> sortedTopics = new ArrayList<>();
        ArrayList<String> sortedCards = new ArrayList<>();
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
                                String key = snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class);
                                String s = snapshot.child(key).child("front").getValue(String.class);
                                if (checkedCards == null) {
                                    sortedCards.add(s);
                                } else if (checkedCards.contains(s)) {
                                    sortedCards.add(key);
                                }
                            }
                            for (DataSnapshot dataSnapshot : snapshot.child(subject).child(topic).getChildren()) {
                                if (!sortedCards.contains(dataSnapshot.getValue(String.class))) {
                                    remainingCards.add(dataSnapshot.getValue(String.class));
                                }
                            }
                            reference.child(subject).child(topic).removeValue();
                            for (int j=0; j<remainingCards.size(); j++) {
                                String key = remainingCards.get(j);
                                String s = snapshot.child(key).child("front").getValue(String.class);
                                reference.child(subject).child(topic).child(String.valueOf(j)).setValue(s);
                            }
                        }
                    }
                }

                for (String card : sortedCards) {
                    reference.child(card).removeValue();
                }

                if (sortedCards.isEmpty()) {
                    for(String oldSubject : sortedSubjects){
                        for(String oldTopic : sortedTopics) {
                            reference.child(oldSubject).child(oldTopic).removeValue();
                            ArrayList<String> remainingTopics = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.child(oldSubject).child("sorting").getChildren()){
                                if (dataSnapshot.exists() && !dataSnapshot.getValue(String.class).equals(oldTopic)) {
                                    remainingTopics.add(dataSnapshot.getValue(String.class));
                                }
                                reference.child(oldSubject).child("sorting").removeValue();
                                for (String newTopic : remainingTopics) {
                                    int sortOrder = (int) (snapshot.child(oldSubject).child("sorting").getChildrenCount());
                                    reference.child(oldSubject).child("sorting").child(String.valueOf(sortOrder)).setValue(newTopic);
                                }
                            }
                        }
                        if (sortedTopics.isEmpty()) {
                            reference.child(oldSubject).removeValue();
                            for (DataSnapshot dataSnapshot : snapshot.child("subject_sorting").getChildren()) {
                                if (dataSnapshot.exists() && !dataSnapshot.getValue(String.class).equals(oldSubject)) {
                                    remainingSubjects.add(dataSnapshot.getValue(String.class));
                                }
                            }
                        }
                    }
                } else {

                }
                if (!remainingSubjects.isEmpty()) {
                    reference.child("subject_sorting").removeValue();
                    for (int i=0; i<remainingSubjects.size(); i++) {
                        reference.child("subject_sorting").child(String.valueOf(i)).setValue(remainingSubjects.get(i));
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
