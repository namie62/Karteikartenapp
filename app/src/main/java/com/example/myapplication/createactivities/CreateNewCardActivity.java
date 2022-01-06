package com.example.myapplication.createactivities;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.myapplication.KartenClass;
import com.example.myapplication.R;
import com.example.myapplication.activities.CancelNewCardPopupActivity;
import com.example.myapplication.activities.InsertImgHelperClassActivity;
import com.example.myapplication.objectclasses.Flashcard;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewCardActivity extends AppCompatActivity {
    String topic;
    String subject;
    private static final int REQUESTCODE = 1;
    Bitmap img;
    private FirebaseDatabase flashcardDB;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        this.reference = flashcardDB.getReference("cornelia"); //cornelia mit username ersetzen

        topic = getIntent().getExtras().getString("selectedTopic");
        subject = getIntent().getExtras().getString("selectedSubject");


        Button cancelBtn = (Button) findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPopUpWindow();
            }
        });
    }

    private void openPopUpWindow() {
        Intent popupWindow = new Intent(CreateNewCardActivity.this, CancelNewCardPopupActivity.class);
        popupWindow.putExtra("Themenname", topic);
        startActivity(popupWindow);
    }

    public void onClick(View view) {
        Intent i = new Intent(this, InsertImgHelperClassActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getParcelableExtra("pic");
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

    public void saveContent(View view){    // setzt die Inhalte in Klasse Karte und sichert damit das Abspeichern
        Flashcard card = new Flashcard(getFrontText(), getBackText(), getImg());
        this.reference.child("subjects").child(subject).child("topics").child(topic).child("cards").push().setValue(card);
        this.finish();
    }

    public String getFrontText(){    // Getter für speichereInhalt Methode
        EditText topicEditText = (EditText) findViewById(R.id.topicEditText);
        String front = topicEditText.getText().toString();
        return front;
    }

    public String getBackText(){
        EditText topicEditText = (EditText) findViewById(R.id.contentEditText);
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