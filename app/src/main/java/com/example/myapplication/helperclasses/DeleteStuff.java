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

    public void deleteSubject() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cardsToDelete = new ArrayList<>();
                for (String subject : checkedSubjects) {
                    for (DataSnapshot topicSnapshot : snapshot.child(subject).getChildren()) {
                        for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                            cardsToDelete.add(cardSnapshot.getValue(String.class));
                        }
                    }
                    reference.child(subject).removeValue();
                    ArrayList<String> remainingSubjects = new ArrayList<>();
                    for (int i=0; i< (int) snapshot.child("subject_sorting").getChildrenCount(); i++) {
                        if (!checkedSubjects.contains(snapshot.child("subject_sorting").child(String.valueOf(i)).getValue(String.class))){
                            remainingSubjects.add(snapshot.child("subject_sorting").child(String.valueOf(i)).getValue(String.class));
                        }
                    }
                    reference.child("subject_sorting").removeValue();
                    for (int i=0; i<remainingSubjects.size(); i++) {
                        reference.child("subject_sorting").child(String.valueOf(i)).setValue(remainingSubjects.get(i));
                    }
                }
                for (String cardpath : cardsToDelete) {
                    reference.child("cards").child(cardpath).removeValue();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void deleteeee() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (checkedCards == null && checkedTopics == null) {
                        for (String subject : checkedSubjects) {
                            ArrayList<String> cardsToDelete = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.child(subject).getChildren()){
                                int numberOfCardsInTopic = (int) dataSnapshot.getChildrenCount();
                                for (int i=0; i<numberOfCardsInTopic; i++) {
                                    cardsToDelete.add(dataSnapshot.child(String.valueOf(i)).getValue(String.class));
                                }
                            }
                            for (String card : cardsToDelete) {
                                reference.child(card).removeValue();
                            }
                            reference.child(subject).removeValue();
                        }
                        ArrayList<String> remainingSubjects = new ArrayList<>();
                        for (int i=0; i< (int) snapshot.child("subject_sorting").getChildrenCount(); i++) {
                            if (!checkedSubjects.contains(snapshot.child("subject_sorting").child(String.valueOf(i)).getValue(String.class))){
                                remainingSubjects.add(snapshot.child("subject_sorting").child(String.valueOf(i)).getValue(String.class));
                            }
                        }
                        reference.child("subject_sorting").setValue(remainingSubjects);
                    } else if (checkedCards == null && checkedTopics != null){
                        for (String subject : checkedSubjects) {
                            for (String topic : checkedTopics) {
                                ArrayList<String> cardsToDelete = new ArrayList<>();
                                int numberOfCardsInTopic = (int) snapshot.child(subject).child(topic).getChildrenCount();
                                for (int i=0; i<numberOfCardsInTopic; i++) {
                                    cardsToDelete.add(snapshot.child(subject).child(topic).getValue(String.class));
                                }
                                for (String card : cardsToDelete) {
                                    reference.child(card).removeValue();
                                }
                                reference.child(subject).child(topic).removeValue();
                            }
                            ArrayList<String> remainingTopics = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.child(subject).child("sorting").getChildren()) {
                                if (!checkedTopics.contains(dataSnapshot.getValue(String.class))){
                                    remainingTopics.add(dataSnapshot.getValue(String.class));
                                }
                            }
                               for (int i=0; i<remainingTopics.size(); i++) {
                                reference.child(subject).child("sorting").child(String.valueOf(i)).setValue(remainingTopics.get(i));
                            }
                        }
                    } else {
                        for (String subject : checkedSubjects){
                            for (String topic : checkedTopics) {
                                for (String card : checkedCards) {
                                    reference.child(card).removeValue();
                                }

                                ArrayList<String> remainingCards = new ArrayList<>();
                                int numberOfCardsInTopic = (int) snapshot.child(subject).child(topic).getChildrenCount();
                                for (int i=0; i<numberOfCardsInTopic; i++) {
                                    if (!checkedCards.contains(snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class))){
                                        remainingCards.add(snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class));
                                    }
                                }
                                reference.child(subject).child(topic).setValue(remainingCards);
                            }
                        }
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
