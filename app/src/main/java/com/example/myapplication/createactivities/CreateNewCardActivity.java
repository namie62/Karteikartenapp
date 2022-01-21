package com.example.myapplication.createactivities;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.objectclasses.Flashcard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
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
    private String selectedSubject, selectedTopic, selectedCard, uriFromDB;
    private DatabaseReference reference;
    private ArrayList<String> checkedSubjects, checkedTopics;
    private IntentHelper ih;
    private EditText frontEditText, backEditText;
    private ImageView imageView;
    private TextInputLayout TextContent;
    private ScrollView ScrollView;
    private StorageReference storageReference;
    private Uri uri;
    private boolean newPicture = false;

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
        this.TextContent = findViewById(R.id.TextInhalt);
        this.ScrollView = (ScrollView) findViewById(R.id.ScrollView);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Sind Sie sicher, dass Sie abbrechen möchten?");
        builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ih.goToCardOverview(checkedSubjects, checkedTopics);
            }
        });

        builder.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        });

        builder.create();
        builder.show();

    }

    public void insertImg(View view) {
        if (selectedCard != null) {
            newPicture = true;
        }
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

    private void showContent() {
        reference.child("cards").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    try {
                        uriFromDB = snapshot.child(selectedCard).child("img_uri").getValue(String.class);
                        Picasso.get().load(uriFromDB).into(imageView);
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
                        if (uri != null && newPicture) {
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
        if (selectedCard != null) {
            try {
                BitmapDrawable bitmapDrawable = ((BitmapDrawable) imageView.getDrawable());
                Bitmap bitmap = bitmapDrawable.getBitmap();
                String bitmapPath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,"test", null);
                Uri bitmapUri = Uri.parse(bitmapPath);
                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/jpeg");
                shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Thema: " + getFrontText() + "\nInhalt: " + getBackText());
                startActivity(Intent.createChooser(shareIntent,"Share Image"));
            } catch (Exception e) {
                Toast.makeText(this, "Karte hat noch nicht fertig geladen, bitte einen Moment Gedult oder Verbindung mit Internet überprüfen!", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Bitte Karte zuerst speichern!", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFrontText(){
        EditText topicEditText = findViewById(R.id.front_edit_text);
        return topicEditText.getText().toString();
    }

    private String getBackText(){
        EditText topicEditText = findViewById(R.id.back_edit_text);
        return topicEditText.getText().toString();
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
                Toast.makeText(getApplicationContext(), "Bildupload fehlgeschlagen!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension() {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void scrollToBottom() {
        ScrollView.post(new Runnable() {
            public void run() {
                ScrollView.smoothScrollTo(0, TextContent.getBottom());
            }
        });
    }
}