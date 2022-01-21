package com.example.myapplication.overviewactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
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

public class ShowSubjectsActivity extends AppCompatActivity {
    private ArrayList<String> checkedSubjects = new ArrayList<>();
    private ListView listView;
    private Context applicationContext;
    private ArrayList<String> showObjects;
    private ArrayAdapter<String> adapter;
    private IntentHelper ih;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_subjects);

        String user = getIntent().getExtras().getString("user");
        this.ih = new IntentHelper(this, user);

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.listView = findViewById(R.id.subjects_listView);
        this.applicationContext = getApplicationContext();
        this.showObjects = new ArrayList<>();


        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, showObjects);


        reference.child("subject_sorting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkedSubjects = new ArrayList<>();
                showObjects.clear();
                if (snapshot.exists()) {
                    int max = (int) snapshot.getChildrenCount();
                    for (int i=0; i<max; i++) {
                        String nameFromDB = snapshot.child(String.valueOf(i)).getValue(String.class);
                        showObjects.add(nameFromDB);
                    }
                    listView.setAdapter(adapter);
                    ListviewHelperClass subjectView = new ListviewHelperClass(listView, showObjects);
                    checkedSubjects = subjectView.getCheckeditems();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goToPrevious(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void goToTopics(View view) {
        if (checkedSubjects.size() == 0) {
            Toast.makeText(this, "Bitte ein Fach auswählen!", Toast.LENGTH_SHORT).show();
        } else {
            ih.goToTopicOverview(checkedSubjects);
        }
    }

    public void startStudyMode(View view){
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cardsInSubjects = new ArrayList<>();
                for (String subject : checkedSubjects) {
                    for (DataSnapshot topicSnapshot : snapshot.child(subject).getChildren()) {
                        if (!topicSnapshot.getKey().equals("sorting")) {
                            for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                cardsInSubjects.add(cardSnapshot.getValue(String.class));
                            }
                        }
                    }
                }
                if (cardsInSubjects.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Keine Karten in ausgewählten Fächern gefunden!", Toast.LENGTH_SHORT).show();
                } else if (checkedSubjects.size() != 0) {
                    ih.startStudyMode(checkedSubjects);
                } else {
                    Toast.makeText(getApplicationContext(), "Bitte ein Fach auswählen!", Toast.LENGTH_SHORT).show();
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public void startQuizMode(View view) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cardsInSubjects = new ArrayList<>();
                for (String subject : checkedSubjects) {
                    for (DataSnapshot topicSnapshot : snapshot.child(subject).getChildren()) {
                        if (!topicSnapshot.getKey().equals("sorting")) {
                            for (DataSnapshot cardSnapshot : topicSnapshot.getChildren()) {
                                cardsInSubjects.add(cardSnapshot.getValue(String.class));
                            }
                        }
                    }
                }
                if (cardsInSubjects.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Keine Karten in ausgewählten Fächern gefunden!", Toast.LENGTH_SHORT).show();
                } else if (checkedSubjects.size() != 0) {
                        ih.startQuizmode(checkedSubjects);
                } else {
                    Toast.makeText(getApplicationContext(), "Bitte ein Fach auswählen!", Toast.LENGTH_SHORT).show();
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void newSubject(View view){
        ih.newSubject();
    }

    public void editSubject(View view) {
        if (checkedSubjects.size() == 1) {
            ih.editSubject(showObjects, checkedSubjects.get(0));
        } else {
            Toast.makeText(this, "Bitte exakt 1 Fach auswählen!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSubject(View view) {
        DeleteStuff ds = new DeleteStuff(getApplicationContext(), reference, checkedSubjects);
        ds.deleteSubjects();
    }
}