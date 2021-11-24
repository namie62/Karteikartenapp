package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Grafikeinfuegen extends AppCompatActivity {
    public Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openGalery();
    }

    public void openGalery() {   // Öffnet Galerie und returnt dann ein Bild
        final int RESULT_GALLERY = 1;
        Intent galery = new Intent(Intent.ACTION_GET_CONTENT);
        galery.setType("image/*");
        startActivityForResult(galery, RESULT_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                try {
                    this.imgUri = data.getData();
                    finish();
                } catch (Exception e) {
                    this.imgUri = null;
                    finish();
                }
            }
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("pic", this.imgUri);
                //"pic", this.imgUri.toString());
        setResult(RESULT_OK, data);
        super.finish();
    }
}
