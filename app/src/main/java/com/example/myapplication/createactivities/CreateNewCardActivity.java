package com.example.myapplication.createactivities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.objectclasses.Flashcard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CreateNewCardActivity extends AppCompatActivity {
    private String selectedSubject, selectedTopic, selectedCard;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects, checkedTopics;
    private IntentHelper ih;
    private EditText frontEditText, backEditText;
    private ImageView imageView;
    private StorageReference storageReference;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference(user);
        this.storageReference = FirebaseStorage.getInstance().getReference();

        this.frontEditText = findViewById(R.id.front_edit_text);
        this.backEditText = findViewById(R.id.back_edit_text);
        this.imageView = findViewById(R.id.imageView);

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
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    public void showContent() {
        reference.child("cards").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        String uri = snapshot.child(selectedCard).child("img_uri").getValue(String.class);
                        Picasso.get().load(uri).into(imageView);
                    } catch (Exception ignored) {
                    }
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
        Flashcard card = new Flashcard(getFrontText(), getBackText());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (selectedCard == null) {
                        String newUniqueKey = reference.child("cards").push().getKey();
                        reference.child("cards").child(newUniqueKey).setValue(card);
                        int sortOrder = (int) snapshot.child(selectedSubject).child(selectedTopic).getChildrenCount();
                        reference.child(selectedSubject).child(selectedTopic).child(String.valueOf(sortOrder)).setValue(newUniqueKey);
                        if (uri != null) {
                            uploadToFirebase(newUniqueKey);
                        }
                    } else {
                        reference.child("cards").child(selectedCard).child("front").setValue(getFrontText());
                        reference.child("cards").child(selectedCard).child("back").setValue(getBackText());
                        if (uri != null) {
                            uploadToFirebase(selectedCard);
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
        ih.shareCard(getFrontText(), getBackText(), uri.toString());
    }

    public String getFrontText(){
        EditText topicEditText = findViewById(R.id.front_edit_text);
        String front = topicEditText.getText().toString();
        return front;
    }

    public String getBackText(){
        EditText topicEditText = findViewById(R.id.back_edit_text);
        String back = topicEditText.getText().toString();
        return back;
    }

    private void uploadToFirebase(String uniqueKey) {
        StorageReference fileRef = storageReference.child(System.currentTimeMillis() + "." + getFileExtension());
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        reference.child("cards").child(uniqueKey).child("img_uri").setValue(uri.toString());
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Bildupload fehlgeschlagen!", Toast.LENGTH_SHORT);
            }
        });
    }

    private String getFileExtension() {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}