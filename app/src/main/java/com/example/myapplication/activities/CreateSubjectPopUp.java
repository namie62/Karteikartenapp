package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class CreateSubjectPopUp extends AppCompatActivity {

    Button buttonabbrechen;
    Button buttonFacherstellen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_subject_pop_up);

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