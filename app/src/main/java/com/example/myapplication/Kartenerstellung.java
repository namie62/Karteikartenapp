package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Kartenerstellung extends AppCompatActivity {
    String themenname;
    private static final int REQUESTCODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartenerstellung);

        themenname = getIntent().getExtras().getString("Themenname");


        Button buttonabbrechen = (Button) findViewById(R.id.buttonabbrechen);
        buttonabbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPopUpWindow();
            }
        });
    }
    private void openPopUpWindow() {
        Intent popupwindow = new Intent(Kartenerstellung.this, PopUpWindow.class);
        popupwindow.putExtra("Themenname", themenname);
        startActivity(popupwindow);
    }


    public void onClick(View view) {   // Die Methode openGalery muss in Jennys Knopf onClick rein
        Intent i = new Intent(this, Grafikeinfuegen.class);
        startActivityForResult(i, REQUESTCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView grafik = (ImageView) findViewById(R.id.imageView);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try{
                    Uri uri = data.getParcelableExtra("pic");
                    if (uri != null){
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    grafik.setImageBitmap(bitmap);
                    }else {
                        System.out.println("kein Bild ausgewählt");
                    }
                } catch (Exception e) {
                    Bitmap bitmap = null;
                    System.out.println("konnte Bild konnte nicht geparsed werden"); // Stattdessen Errormessage dialog
                }
            }
        }
    }
}