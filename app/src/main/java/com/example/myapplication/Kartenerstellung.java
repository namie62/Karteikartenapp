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

public class Kartenerstellung extends AppCompatActivity {
    String fachname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kartenerstellung);
        fachname = getIntent().getExtras().getString("Fachname");
    }
    private static final int REQUESTCODE = 1;

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

    public void zurueck(View view) {   // Die Methode openGalery muss in Jennys Knopf onClick rein
        try{
        Intent i = new Intent(this, Themenuebersicht.class);
        i.putExtra("Fachname", fachname);
        startActivityForResult(i, REQUESTCODE);}
        catch (Exception e){
            System.out.println("Ging was schief");
        }
    }

}