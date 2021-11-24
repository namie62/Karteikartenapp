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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private static final int REQUESTCODE = 1;

    public void onClick(View view) {   // Die Methode openGalery muss in Jennys Knopf onClick rein
        Intent i = new Intent(this, Karte.class);
        startActivityForResult(i, REQUESTCODE);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        ImageView grafik = (ImageView) findViewById(R.id.imageView);
//        if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                try{
//                    Uri uri = data.getParcelableExtra("pic");
//                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                   // System.out.println("bitmap");
//                    grafik.setImageBitmap(bitmap);
//                } catch (Exception e) {
//                    Bitmap bitmap = null;
//                    System.out.println("konnte Bild nicht laden"); // Stattdessen Errormessage dialog
//                }
//            }
//        }
//    }
}