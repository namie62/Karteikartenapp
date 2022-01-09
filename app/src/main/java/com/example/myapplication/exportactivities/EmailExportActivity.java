package com.example.myapplication.exportactivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class EmailExportActivity extends AppCompatActivity {
    EditText etTo, etSubject, etMessage;
    Button btnSend;
    TextView txThema, txInhalt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_export);
        etTo = findViewById(R.id.editTextTo);
        etSubject = findViewById(R.id.editTextSubject);
        etMessage = findViewById(R.id.editTextMessage);
        btnSend = findViewById(R.id.buttonSend);

        txThema = findViewById(R.id.textThema);
        txInhalt = findViewById(R.id.textInhalt);

        Intent sendmail = getIntent();
        String th = sendmail.getStringExtra("thema");
        String in = sendmail.getStringExtra("inhalt");
        txThema.setText(th);
        txInhalt.setText(in);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + etTo.getText().toString()));
                intent.putExtra(Intent.EXTRA_SUBJECT, etSubject.getText().toString());
                intent.putExtra(Intent.EXTRA_STREAM, txThema.getText().toString());
                intent.putExtra(Intent.EXTRA_STREAM, txInhalt.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, etMessage.getText().toString());
                startActivity(intent);
            }
        });

    }
}