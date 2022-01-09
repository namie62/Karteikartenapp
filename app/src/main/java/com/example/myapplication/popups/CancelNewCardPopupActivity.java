package com.example.myapplication.popups;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.overviewactivities.ShowCardsActivity;

import java.util.ArrayList;

public class CancelNewCardPopupActivity extends AppCompatActivity {
    private IntentHelper ih;
    private ArrayList<String> checkedSubjects;
    private ArrayList<String> checkedTopics;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_new_card_pop_up);

        this.user = getIntent().getExtras().getString("user");
        this.ih = new IntentHelper(this, user);
        this.checkedSubjects = getIntent().getStringArrayListExtra("checkedSubjects");
        this.checkedTopics = getIntent().getStringArrayListExtra("checkedTopics");

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
    }

    public void yes(View view) {
        ih.goToCardOverview(checkedSubjects, checkedTopics);
    }

    public void no(View view) {this.finish();}

}