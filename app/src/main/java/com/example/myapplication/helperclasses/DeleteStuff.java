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
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DeleteStuff {

    private ArrayList<String> checkedSubjects, checkedTopics, checkedCards;
    private DatabaseReference reference;
    private Context applicationContext;
    private StorageReference storageReference;

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

    public void deleteSubjects() {
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

    public void deleteTopics() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cardsToDelete = new ArrayList<>();
                for (String subject : checkedSubjects) {
                    for (String topic : checkedTopics) {
                        for (DataSnapshot cardSnapshot : snapshot.child(subject).child(topic).getChildren()) {
                            cardsToDelete.add(cardSnapshot.getValue(String.class));
                        }
                        reference.child(subject).child(topic).removeValue();
                        ArrayList<String> remainingTopics = new ArrayList<>();
                        for (int i=0; i< (int) snapshot.child(subject).child("sorting").getChildrenCount(); i++) {
                            if (!checkedTopics.contains(snapshot.child(subject).child("sorting").child(String.valueOf(i)).getValue(String.class))){
                                remainingTopics.add(snapshot.child(subject).child("sorting").child(String.valueOf(i)).getValue(String.class));
                            }
                        }
                        reference.child(subject).child("sorting").removeValue();
                        for (int i=0; i<remainingTopics.size(); i++) {
                            reference.child(subject).child("sorting").child(String.valueOf(i)).setValue(remainingTopics.get(i));
                        }
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

    public void deleteCards() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (String subject : checkedSubjects) {
                    for (String topic : checkedTopics) {
                        ArrayList<String> remainingCards = new ArrayList<>();
                        for (int i=0; i< (int) snapshot.child(subject).child(topic).getChildrenCount(); i++) {
                            if (!checkedCards.contains(snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class))) {
                                remainingCards.add(snapshot.child(subject).child(topic).child(String.valueOf(i)).getValue(String.class));
                            }
                        }
                        reference.child(subject).child(topic).removeValue();
                        for (int i=0; i<remainingCards.size(); i++) {
                            reference.child(subject).child(topic).child(String.valueOf(i)).setValue(remainingCards.get(i));
                        }
                    }
                }
                for (String card : checkedCards) {
                    String picUrl = snapshot.child("cards").child(card).child("img_url").getValue(String.class);

                    reference.child("cards").child(card).removeValue();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
