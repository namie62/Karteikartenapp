package com.example.myapplication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.overviewactivities.ShowCardsActivity;

public class CancelNewCardPopupActivity extends AppCompatActivity {
    private static final int REQUESTCODE = 1;

    Button yesBtn;
    Button noBtn;
    String topic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        topic = getIntent().getExtras().getString("Themenname");

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width * .7), (int) (height * .3));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -10;

        getWindow().setAttributes(params);


        yesBtn = (Button) findViewById(R.id.yesBtn);
        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CancelNewCardPopupActivity.this, ShowCardsActivity.class);
                i.putExtra("Themenname", topic);
                startActivityForResult(i, REQUESTCODE);
            }
        });

        noBtn = (Button) findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
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