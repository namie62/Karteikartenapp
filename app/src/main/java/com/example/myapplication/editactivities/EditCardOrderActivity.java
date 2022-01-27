package com.example.myapplication.editactivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditCardOrderActivity extends AppCompatActivity {
    private DatabaseReference reference;
    private String selectedTopic, selectedSubject, selectedCard;
    private ArrayList<String> allCards;
    private EditText cardNewPositionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card_order);

        String user = getIntent().getExtras().getString("user");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.selectedTopic = getIntent().getExtras().getString("selectedTopic");
        this.selectedCard = getIntent().getExtras().getString("selectedCard");
        this.allCards = getIntent().getExtras().getStringArrayList("allCards");

        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);
        int oldIndex = allCards.indexOf(selectedCard);


        TextView cardOldPositionTextView = findViewById(R.id.card_old_position_textView);
        cardOldPositionTextView.setText(String.valueOf(oldIndex +1));

        cardNewPositionEditText = findViewById(R.id.card_new_position_editTextNumber);
        cardNewPositionEditText.setText(String.valueOf(oldIndex +1));
    }

    public void saveChanges(View view) {
        int newIndex =  Integer.parseInt(String.valueOf(cardNewPositionEditText.getText())) - 1;
        allCards.remove(selectedCard);
        if (newIndex >= allCards.size()) {
            allCards.add(selectedCard);;
        } else {
            allCards.add(newIndex, selectedCard);
        }
        reference.child(selectedSubject).child(selectedTopic).removeValue();
        for (int i=0; i<allCards.size(); i++) {
            reference.child(selectedSubject).child(selectedTopic).child(String.valueOf(i)).setValue(allCards.get(i));
        }
        this.finish();
    }

    public void cancel(View view) {
        this.finish();
    }
}