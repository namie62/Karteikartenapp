package com.example.myapplication.activities;
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

public class CreateNewCardActivity extends AppCompatActivity {
    String topic;
    private static final int REQUESTCODE = 1;
    KartenClass karte = new KartenClass();
    Bitmap img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_card);

        topic = getIntent().getExtras().getString("Themenname");

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
        karte.setFrage(getFrontText());
        karte.setAntwort(getBackText());
        karte.setLernstufe(getProgress());
        karte.setGrafik(getImg());
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