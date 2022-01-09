package com.example.myapplication.createactivities;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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

public class CreateNewCardActivity extends AppCompatActivity {
    private String selectedTopic;
    private String selectedSubject;
    private Bitmap img;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects;
    private ArrayList<String> checkedTopics;
    private ArrayList<String> checkedCards;
    private IntentHelper ih;
    private String uriForDB = " ";
    private String user;
    private String selectedCardFront;
    private String backImgFromDB;
    private int sortOrder;
    private int progress;
    private EditText frontEditText;
    private EditText backEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card);

        this.user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.frontEditText = findViewById(R.id.front_edit_text);
        this.backEditText = findViewById(R.id.back_edit_text);

        this.ih = new IntentHelper(this, user);
        this.selectedTopic = getIntent().getExtras().getString("selectedTopic");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");
        this.checkedTopics = getIntent().getStringArrayListExtra("checkedTopics");
        this.checkedCards = getIntent().getStringArrayListExtra("checkedCards");
        if (checkedCards != null) {
            this.selectedCardFront = checkedCards.get(0);
            showContent();
        }
    }

    public void cancelPopUp(View view) {
        ih.cancelCardPopUp(checkedSubjects, checkedTopics);
    }

    public void insertImg(View view) {
        ih.insertImg();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getParcelableExtra("pic");
                    uriForDB = uri.toString();
                    if (uri != null){
                    this.img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(this.img);
                    }else {
                        System.out.println("kein Bild ausgewählt");
                    }
                } catch (Exception e) {
                    Bitmap bitmap = null;
                    System.out.println("Bild konnte nicht geparsed werden"); // Stattdessen Errormessage dialog
                }
            }
        }
    }

    public void showContent() {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (String subject : checkedSubjects) {
                        for (String topic : checkedTopics) {
                            for (DataSnapshot dataSnapshot : snapshot.child(subject).child(topic).getChildren()){
                                if (dataSnapshot.child("front").exists() && dataSnapshot.child("front").getValue(String.class).equals(selectedCardFront)) {
                                    String backFromDB = dataSnapshot.child("back").getValue(String.class);
                                    backImgFromDB = dataSnapshot.child("backImg").getValue(String.class);
                                    selectedSubject = subject;
                                    selectedTopic = topic;
                                    frontEditText.setText(selectedCardFront);
                                    backEditText.setText(backFromDB);
                                }
                            }
                        }
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveContent(View view){    // setzt die Inhalte in Klasse Karte und sichert damit das Abspeichern
        Flashcard card = new Flashcard(getFrontText(), getBackText(), uriForDB);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int sortOrder = (int) (snapshot.child(selectedSubject).child(selectedTopic).getChildrenCount());
                    if (selectedCardFront == null) {
                        reference.child(selectedSubject).child(selectedTopic).push().setValue(card);
                        card.setSortOrder(sortOrder);
                    } else {
                        for (DataSnapshot dataSnapshot : snapshot.child(selectedSubject).child(selectedTopic).getChildren()) {
                            if (dataSnapshot.child("front").exists() && dataSnapshot.child("front").getValue(String.class).equals(selectedCardFront)){
                                String cardName = dataSnapshot.getKey();
                                reference.child(selectedSubject).child(selectedTopic).child(cardName).child("front").setValue(getFrontText());
                                reference.child(selectedSubject).child(selectedTopic).child(cardName).child("back").setValue(getBackText());
                            }
                        }
                    }

                    ih.goToCardOverview(checkedSubjects, checkedTopics);
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void share(View view) {
        ih.shareCard(getFrontText(), getBackText(), uriForDB);
    }

    public String getFrontText(){    // Getter für speichereInhalt Methode
        EditText topicEditText = (EditText) findViewById(R.id.front_edit_text);
        String front = topicEditText.getText().toString();
        return front;
    }

    public String getBackText(){
        EditText topicEditText = (EditText) findViewById(R.id.back_edit_text);
        String back = topicEditText.getText().toString();
        return back;
    }

    public Integer getProgress(){
        Integer progress = 0;
        return progress;
    }

    public Bitmap getImg(){
        Bitmap img = this.img;
        return img;
    }
}