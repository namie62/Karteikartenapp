package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.helperclasses.IntentHelper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailExportActivity_02 extends AppCompatActivity {
    private EditText recipientEditText, subjectEditText, msgEditText;
    private String front;
    private String back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_export02);

        String user = getIntent().getExtras().getString("user");
        FirebaseDatabase flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = flashcardDB.getReference(user);

        IntentHelper ih = new IntentHelper(this, user);

        this.front = getIntent().getExtras().getString("front");
        this.back = getIntent().getExtras().getString("back");
        String img = getIntent().getExtras().getString("img");

        recipientEditText = findViewById(R.id.recipient_edit_text);
        subjectEditText = findViewById(R.id.subject_edit_text);
        msgEditText = findViewById(R.id.msg_edit_text);

        TextView frontTextView = findViewById(R.id.front_text_view);
        TextView backTextView = findViewById(R.id.back_text_view);

        frontTextView.setText(front);
        backTextView.setText(back);
    }

    public void send(View view) {
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + recipientEditText.getText().toString()));
        i.putExtra(Intent.EXTRA_SUBJECT, subjectEditText.getText().toString());
        String emailText = msgEditText.getText().toString() +"\n\nThemengebiet: " + front+ "\n \nInhalt: " + back;
        i.putExtra(Intent.EXTRA_TEXT, emailText);
        startActivity(i);
    }
}