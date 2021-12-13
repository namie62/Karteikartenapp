package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Fach_oder_Thema_erstellen_PopUp extends AppCompatActivity {


    Button buttonok;
    TextInputEditText hinweistext;
    String texthinweis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fach_oder_thema_erstellen_pop_up);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int heigth = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (heigth * .3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -10;

        getWindow().setAttributes(params);
        this.texthinweis = getIntent().getExtras().getString("Kategorie");
        hinweistext = (TextInputEditText) findViewById(R.id.editTexteingabe);
        hinweistext.setHint(texthinweis);
        buttonok = (Button) findViewById(R.id.buttonok);
        buttonok.setOnClickListener(new View.OnClickListener() {
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