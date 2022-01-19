package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.helperclasses.IntentHelper;
import com.example.myapplication.loginactivities.RegistrationActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseDatabase flashcardDB;
    private final IntentHelper ih = new IntentHelper(this);
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.flashcardDB = FirebaseDatabase.getInstance("https://karteikar-default-rtdb.europe-west1.firebasedatabase.app/");

        this.usernameEditText = findViewById(R.id.username_editText);
        this.passwordEditText = findViewById(R.id.password_editText);
        ih.setUser("J");
        ih.goToStartMenu();

        TextView login = (TextView)findViewById(R.id.lnkRegister);
        login.setMovementMethod(LinkMovementMethod.getInstance());

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

    public void login(View view) {
        String username = usernameEditText.getEditableText().toString();
        String password = passwordEditText.getText().toString();
        if (username.equals("")) {
            Toast.makeText(this, "Kein Benutzername eingegeben!", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            Toast.makeText(this, "Kein Passwort eingegeben!", Toast.LENGTH_SHORT).show();
        } else if (!checkForIllegalCharacters(username)) {
            Toast.makeText(this, "Nicht erlaubte Zeichen in Benutzername:  . , $ , # , [ , ] , / ,", Toast.LENGTH_SHORT).show();
        } else {
            checkExistenceAndLogin(username, password);
        }
    }

    public void checkExistenceAndLogin(String username, String password) {
        DatabaseReference reference = flashcardDB.getReference(username);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("password").getValue(String.class).equals(password)) {
                        ih.setUser(username);
                        ih.goToStartMenu();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Passwort inkorrekt!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Benutzername existiert nicht!", Toast.LENGTH_SHORT).show();
                }
            }

            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean checkForIllegalCharacters(String s) {
        List<String> illegalChars = Arrays.asList(".", "$", "[", "]" , "#", "/");
        for (String c : illegalChars) {
            if (s.contains(c)) {
                return false;
            }
        }
        return true;
    }
}