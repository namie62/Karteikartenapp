package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.myapplication.R;

public class CreateTopicPopUp extends AppCompatActivity {

    Button buttonabbrechen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_pop_up);

        Spinner spinnerFach = findViewById(R.id.spinner_Fachauswählen);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.Fachauswählen, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        spinnerFach.setAdapter(adapter);

        buttonabbrechen = (Button) findViewById(R.id.buttonabbrechen);
        buttonabbrechen.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWindow();
            }
        });
    }
    public void closeWindow(){
        this.finish();
    }
}