package com.example.myapplication.activities;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    private static final int REQUESTCODE = 1;

    public void onClick(View view) {   // Die Methode openGalery muss in Jennys Knopf onClick rein
        Intent i = new Intent(this, SubjectOverviewActivity.class);
        startActivityForResult(i, REQUESTCODE);
    }
}