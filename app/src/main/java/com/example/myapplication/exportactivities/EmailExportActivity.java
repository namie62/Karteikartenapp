package com.example.myapplication.exportactivities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class EmailExportActivity extends AppCompatActivity {
    private EditText recipientEditText, subjectEditText, msgEditText;
    private String front;
    private String back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_export);

        this.front = getIntent().getExtras().getString("front");
        this.back = getIntent().getExtras().getString("back");
        String img = getIntent().getExtras().getString("img");

        URL imageurl = null;
        try {
            imageurl = new URL(img);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

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