package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonabbrechen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonabbrechen = (Button) findViewById(R.id.buttonabbrechen);
        buttonabbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openPopUpWindow();
            }
        });
    }

    private void openPopUpWindow() {
        Intent popupwindow = new Intent(MainActivity.this, PopUpWindow.class);
        startActivity(popupwindow);
    }
}