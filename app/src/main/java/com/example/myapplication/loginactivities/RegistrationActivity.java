package com.example.myapplication.loginactivities;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.helperclasses.CheckForIllegalChars;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {
    FirebaseDatabase flashcardDB;
    IntentHelper ih = new IntentHelper(this);
    EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");

        this.usernameEditText = findViewById(R.id.username_editText);
        this.passwordEditText = findViewById(R.id.password_editText);

        TextView login = (TextView)findViewById(R.id.lnkLogin);
        login.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void register(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, "Kein Benutzername eingegeben!", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(this, "Kein Passwort eingegeben!", Toast.LENGTH_SHORT).show();
        } else if (!CheckForIllegalChars.checkForIllegalCharacters(username)) {
            Toast.makeText(getApplicationContext(), "Nicht erlaubte Zeichen in Benutzername:  . , $ , # , [ , ] , / ,", Toast.LENGTH_SHORT).show();
        } else {
            checkUsernameAvailability(username);
        }
    }

    public void checkUsernameAvailability(String username) {
        DatabaseReference reference = flashcardDB.getReference(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(getApplicationContext(), "Benutzername bereits vergeben!", Toast.LENGTH_SHORT).show();
                    usernameEditText = findViewById(R.id.username_editText);
                } else {
                    reference.child("password").setValue(passwordEditText.getText().toString());
                    ih.setUser(usernameEditText.getText().toString());
                    ih.goToLogin();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void goBack(View view){
        ih.goToLogin();
    }
}