package com.example.myapplication.helperclasses;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.objectclasses.Flashcard;
import com.example.myapplication.objectclasses.Subject;
import com.example.myapplication.objectclasses.Topic;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyFirebaseHelper extends AppCompatActivity {
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;
    private ArrayList<String> selectedSubjects;
    private ArrayList<String> selectedTopics;
    private ArrayList<String> selectedCards;
    private ArrayList<String> checkeditems = new ArrayList<String>();

    public MyFirebaseHelper(String user)  {
        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);
        this.selectedCards = new ArrayList<>();
        this.selectedTopics = new ArrayList<>();
        this.selectedSubjects = new ArrayList<>();
    }

    public FirebaseDatabase getFlashcardDB() {
        return flashcardDB;
    }

    // add new instances to database
    public void addSubject(Subject subject) {
        this.reference.child(subject.getName()).setValue(subject);
    }

    public void addTopic(String subject, Topic topic) {
        this.reference.child(subject).child(topic.getName()).setValue(topic);
    }

    public void addFlashcard(String subject, String topic, Flashcard flashcard) {
        this.reference.child(subject).child(topic).child(flashcard.getFront()).setValue(flashcard);
    }

    // show stuff in listview
    public ArrayList<String> showSubjects(ListView listview, Context applicationContext) {
        ArrayList<String> allSubjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, allSubjects);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allSubjects.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        allSubjects.add(nameFromDB);
                    }
                    ListviewHelperClass subjectView = new ListviewHelperClass(listview, allSubjects);
                    checkeditems= subjectView.getCheckeditems();
                    listview.setAdapter(adapter);
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        return checkeditems;
    }

    public void showTopics(ArrayList<String> selectedSubjects, ListView listview, Context applicationContext) {
        this.selectedSubjects = selectedSubjects;
        ArrayList<String> topicsFromSelectedSubjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_1, topicsFromSelectedSubjects);
        for (String subject : selectedSubjects) {
            DatabaseReference subjectReference = reference.child(subject);
            subjectReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String nameFromDB = dataSnapshot.child("name").getValue(String.class);
                        if(nameFromDB != null) {
                            topicsFromSelectedSubjects.add(nameFromDB);
                        }
                    }
                    listview.setAdapter(adapter);
                }

                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public void showCards(ArrayList<String> selectedTopics, ListView listview, Context applicationContext) {
        this.selectedTopics = selectedTopics;
        ArrayList<String> topicsFromSelectedSubjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_1, topicsFromSelectedSubjects);
        for (String subject : this.selectedSubjects) {
            for (String topic : selectedTopics) {
                DatabaseReference topicReference = reference.child(subject).child(topic);
                topicReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String frontFromDB = dataSnapshot.child("front").getValue(String.class);
                            if(frontFromDB != null) {
                                topicsFromSelectedSubjects.add(frontFromDB);
                            }
                        }
                        listview.setAdapter(adapter);
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void showBackOfCard(String cardFront, TextView textview, Context applicationContext) {
        for (String subject : this.selectedSubjects) {
            for (String topic : this.selectedTopics) {
                DatabaseReference topicReference = reference.child(subject).child(topic);
                topicReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String frontFromDB = dataSnapshot.child("front").getValue(String.class);
                            String backFromDB = dataSnapshot.child("back").getValue(String.class);
                            if(backFromDB != null && frontFromDB.equals(cardFront)) {
                                textview.setText(backFromDB);

                            }
                        }
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void showFrontOfCard(String cardBack, TextView textview, Context applicationContext) {
        for (String subject : this.selectedSubjects) {
            for (String topic : this.selectedTopics) {
                DatabaseReference topicReference = reference.child(subject).child(topic);
                topicReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String frontFromDB = dataSnapshot.child("front").getValue(String.class);
                            String backFromDB = dataSnapshot.child("back").getValue(String.class);
                            if(frontFromDB != null && backFromDB.equals(cardBack)) {
                                textview.setText(frontFromDB);

                            }
                        }
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void upProgress(ArrayList<String> selectedSubjects, ArrayList<String> selectedTopics,String cardFrontOrBack, Context applicationContext) {
        for (String subject : selectedSubjects) {
            for (String topic : selectedTopics) {
                DatabaseReference topicReference = reference.child(subject).child(topic);
                topicReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String frontFromDB = dataSnapshot.child("front").getValue(String.class);
                            String backFromDB = dataSnapshot.child("back").getValue(String.class);
                            if(frontFromDB != null && (backFromDB.equals(cardFrontOrBack) || frontFromDB.equals(cardFrontOrBack))) {
                                int progress = dataSnapshot.child("progress").getValue(Integer.class) + 1;
                                topicReference.child(frontFromDB).child("progress").setValue(progress);
                            }
                        }
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    public void downProgress(ArrayList<String> selectedSubjects, ArrayList<String> selectedTopics,String cardFrontOrBack, Context applicationContext) {
        for (String subject : selectedSubjects) {
            for (String topic : selectedTopics) {
                DatabaseReference topicReference = reference.child(subject).child(topic);
                topicReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String frontFromDB = dataSnapshot.child("front").getValue(String.class);
                            String backFromDB = dataSnapshot.child("back").getValue(String.class);
                            if(frontFromDB != null && (backFromDB.equals(cardFrontOrBack) || frontFromDB.equals(cardFrontOrBack))) {
                                int progress = dataSnapshot.child("progress").getValue(Integer.class) - 1;
                                topicReference.child(frontFromDB).child("progress").setValue(progress);
                            }
                        }
                    }

                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }


//    public void pollingAllInOne(ArrayList<String> selectedCards,
//                                Context applicationContext,
//                                TextView textview,
//                                Button switchBtn,
//                                Button nextBtn,
//                                Button okBtn,
//                                Button wrongBtn){
//        this.selectedTopics = selectedTopics;
//        ArrayList<String> topicsFromSelectedSubjects = new ArrayList<>();
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_1, topicsFromSelectedSubjects);
//        for (String subject : this.selectedSubjects) {
//            for (String topic : selectedTopics) {
//                DatabaseReference topicReference = reference.child(subject).child(topic);
//                topicReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            switchBtn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    //switch between front and back JENNY
//                                }
//                            });
//                            nextBtn.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    //next card
//                                }
//                            });
//
//                            okBtn
//                            String frontFromDB = dataSnapshot.child("front").getValue(String.class);
//                            if(frontFromDB != null) {
//                                topicsFromSelectedSubjects.add(frontFromDB);
//                            }
//                        }
//                        listview.setAdapter(adapter);
//                    }
//
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        }
//    }
}

