package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.loginactivities.RegistrationActivity;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {

    private IntentHelper ih;
    private String user;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextView register = (TextView)findViewById(R.id.lnkRegister);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(View view){
        IntentHelper ih = new IntentHelper(this, "Princess Rainbowfart");
        ih.goToStartMenu();
    }
}