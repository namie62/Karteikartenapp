package com.example.myapplication.overviewactivities;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.DeleteStuff;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.helperclasses.PDFExport;
import com.example.myapplication.popups.HintPopUpActivity;
import com.example.myapplication.helperclasses.ListviewHelperClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowTopicsActivity extends AppCompatActivity {
    private ArrayList<String> checkedSubjects, checkedTopics, showObjects;
    private ListView listView;
    private Context applicationContext;
    private ArrayAdapter<String> adapter;
    private IntentHelper ih;
    private String user;
    DatabaseReference reference;
    private final Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_topics);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.ih = new IntentHelper(this, user);
        this.checkedSubjects = getIntent().getExtras().getStringArrayList("checkedSubjects");

        this.listView = findViewById(R.id.topics_listView);
        this.applicationContext = getApplicationContext();
        this.showObjects = new ArrayList<>();
        this.adapter = new ArrayAdapter<>(applicationContext, android.R.layout.simple_list_item_multiple_choice, showObjects);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                checkedTopics = new ArrayList<>();
                showObjects.clear();
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        int max = (int) snapshot.child(subject).child("sorting").getChildrenCount();
                        for (int i=0; i<max; i++) {
                            String nameFromDB = snapshot.child(subject).child("sorting").child(String.valueOf(i)).getValue(String.class);
                            showObjects.add(nameFromDB);
                        }
                    }
                    listView.setAdapter(adapter);
                    ListviewHelperClass subjectView = new ListviewHelperClass(listView, showObjects);
                    checkedTopics = subjectView.getCheckeditems();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        TextView textView = findViewById(R.id.subject_name_textView);
        textView.setText(checkedSubjects.toString()); //vlt noch hübscher machen
    }


    public void goToPrevious(View view) {
        ih.goToStartMenu();
    }

    public void goToCards(View view) {  //öffnet Kartenerstellung
        if (checkedTopics.size() == 0) {
            Toast.makeText(this, "Bitte mindestens 1 Thema auswählen!", Toast.LENGTH_SHORT).show();
        }
        else {
            ih.goToCardOverview(checkedSubjects, checkedTopics);
        }
    }

    public void startStudyMode(View view){
        reference.child("subject_sorting").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cardsInTopics = new ArrayList<>();
                for (String subject : checkedSubjects) {
                    for (String topic : checkedTopics) {
                        for (DataSnapshot cardSnapshot : snapshot.child(subject).child(topic).getChildren()) {
                            cardsInTopics.add(cardSnapshot.getValue(String.class));
                        }
                    }
                }
                if (cardsInTopics.isEmpty()) {
                    Toast.makeText(c, "Keine Karten in ausgewählten Themen gefunden!", Toast.LENGTH_SHORT).show();
                } else if (checkedTopics.size() != 0) {
                    ih.startStudyMode(checkedSubjects, checkedTopics);
                } else {
                    Toast.makeText(c, "Bitte ein Thema auswählen!", Toast.LENGTH_SHORT).show();
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void startQuiz(View view){
        reference.child("subject_sorting").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<String> cardsInTopics = new ArrayList<>();
                for (String subject : checkedSubjects) {
                    for (String topic : checkedTopics) {
                        for (DataSnapshot cardSnapshot : snapshot.child(subject).child(topic).getChildren()) {
                            cardsInTopics.add(cardSnapshot.getValue(String.class));
                        }
                    }
                }
                if (cardsInTopics.isEmpty()) {
                    Toast.makeText(c, "Keine Karten in ausgewählten Themen gefunden!", Toast.LENGTH_SHORT).show();
                } else if (checkedTopics.size() != 0) {
                    ih.startQuizmode(checkedSubjects, checkedTopics);
                } else {
                    Toast.makeText(c, "Bitte ein Thema auswählen!", Toast.LENGTH_SHORT).show();
                }
            }
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(applicationContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createTopic(View view){
        ih.newTopic(checkedSubjects);
    }

    public void editTopic(View view){
        if (checkedTopics.size() == 0) {
            Toast.makeText(c, "Bitte mindestens ein Thema auswählen!", Toast.LENGTH_SHORT).show();;
        } else if (checkedTopics.size() == 1){
            Intent editTopic = new Intent(this, ShowCardsActivity.class);
            editTopic.putExtra("Thema", checkedTopics.get(0));
            startActivity(editTopic);
        } else {
            Intent popupWindow = new Intent(this, HintPopUpActivity.class);
            popupWindow.putExtra("InfotextPoUp", "Bitte nur 1 Thema zur Bearbeitung auswählen.");
        }
    }

    public void pdfexport(View view){
        PDFExport pdfexport = new PDFExport();
        pdfexport.PDFExport(this, this.user, this.checkedSubjects, checkedTopics);
        Toast.makeText(this,"PDF created", Toast.LENGTH_LONG).show();
    }

    public void deleteTopic(View view) {
        DeleteStuff ds = new DeleteStuff(getApplicationContext(), reference, checkedSubjects, checkedTopics);
        ds.deleteTopics();
    }
}