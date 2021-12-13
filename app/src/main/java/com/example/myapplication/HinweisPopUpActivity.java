package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class HinweisPopUpActivity extends AppCompatActivity {

    Button buttonok;
    TextView text;
    String infotext;
    String abfrage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hinweis_popup);

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

        text = (TextView) findViewById(R.id.textViewInfo);
        this.infotext = getIntent().getExtras().getString("InfotextPoUp");
        text.setText(infotext);

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