package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;


public class OpenGrafik extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {   // Die Methode openGalery muss in Jennys Knopf onClick rein
        openGalery();
    }

    public void openGalery(){   // Öffnet Galerie und returnt dann ein Bild
        final int RESULT_GALLERY = 1;
        Intent galery = new Intent(Intent.ACTION_GET_CONTENT);
        galery.setType("image/*");
        // galery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(galery, RESULT_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageView grafik = (ImageView) findViewById(R.id.imageView);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri imgUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgUri);
                    grafik.setImageBitmap(bitmap);
                    if (bitmap != null){
                        isimage(true);
                    }
                } catch (Exception e) {
                    Bitmap bitmap = null;
                    System.out.println("konnte Bild nicht laden"); // Stattdessen Errormessage dialog
                    isimage(false);
                }
            }
        }
    }
    public boolean isimage(boolean status){
        return status;
    }
}
