package com.example.myapplication.createactivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.InsertImgHelperClassActivity;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.objectclasses.Flashcard;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CreateNewCardActivity extends AppCompatActivity {
    private String selectedSubject, selectedTopic, selectedCard, uriForDB;
    private Bitmap img;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects, checkedTopics;
    private IntentHelper ih;
    private EditText frontEditText, backEditText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);

        this.frontEditText = findViewById(R.id.front_edit_text);
        this.backEditText = findViewById(R.id.back_edit_text);
        this.imageView = (ImageView) findViewById(R.id.imageView);

        this.ih = new IntentHelper(this, user);
        this.selectedTopic = getIntent().getExtras().getString("selectedTopic");
        this.selectedSubject = getIntent().getExtras().getString("selectedSubject");
        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");
        this.checkedTopics = getIntent().getStringArrayListExtra("checkedTopics");
        ArrayList<String> checkedKeys = getIntent().getStringArrayListExtra("checkedCards");
        if (checkedKeys != null) {
            this.selectedCard = checkedKeys.get(0);
            showContent();
        }
    }

    public void cancelPopUp(View view) {
        ih.cancelCardPopUp(checkedSubjects, checkedTopics);
    }

    public void insertImg(View view) {
        final int RESULT_GALLERY = 1;
        Intent i = new Intent(this, InsertImgHelperClassActivity.class);
        startActivityForResult(i, RESULT_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getParcelableExtra("pic");
                    uriForDB = uri.toString();
                    this.img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(this.img);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Bild konnte nicht geparsed werden!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void showContent() {
        reference.child("cards").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        Uri uri = Uri.parse(snapshot.child(selectedCard).child("img_uri").getValue(String.class));
                        img = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imageView.setImageBitmap(img);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), "Bild existiert nicht!", Toast.LENGTH_SHORT).show();
                    } catch (IOException ignored) {}
                    frontEditText.setText(snapshot.child(selectedCard).child("front").getValue(String.class));
                    backEditText.setText(snapshot.child(selectedCard).child("back").getValue(String.class));
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveContent(View view) {
        Flashcard card = new Flashcard(getFrontText(), getBackText(), uriForDB);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (selectedCard == null) {
                        String newUniqueKey = reference.child("cards").push().getKey();
                        reference.child("cards").child(newUniqueKey).setValue(card);
                        int sortOrder = (int) snapshot.child(selectedSubject).child(selectedTopic).getChildrenCount();
                        reference.child(selectedSubject).child(selectedTopic).child(String.valueOf(sortOrder)).setValue(newUniqueKey);
                    } else {
                        reference.child("cards").child(selectedCard).child("front").setValue(getFrontText());
                        reference.child("cards").child(selectedCard).child("back").setValue(getBackText());
                        reference.child("cards").child(selectedCard).child("img_uri").setValue(getBackText());
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

    public String getFrontText(){ 
        EditText topicEditText = (EditText) findViewById(R.id.front_edit_text);
        String front = topicEditText.getText().toString();
        return front;
    }

    public String getBackText(){
        EditText topicEditText = (EditText) findViewById(R.id.back_edit_text);
        String back = topicEditText.getText().toString();
        return back;
    }
}