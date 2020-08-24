package com.example.projectstore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public EditText emailId, passwordId;
    public Button signInButton;
    public TextView registerHere;

    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailId = (EditText)findViewById(R.id.email);
        passwordId = (EditText)findViewById(R.id.password);
        signInButton = (Button)findViewById(R.id.signInBtn);
        registerHere = (TextView)findViewById(R.id.registerHere);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = emailId.getText().toString();
                final String password = passwordId.getText().toString();
                if(email.isEmpty()) {
                    emailId.setError("Email cannot be empty");
                } else if(password.isEmpty()) {
                    passwordId.setError("Password cannot be empty");
                } else if(email.isEmpty() && password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Fields Cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(!(email.isEmpty() && password.isEmpty())) {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!(task.isSuccessful())) {
                                Toast.makeText(MainActivity.this, "Error Login", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
//                                Intent i = new Intent(MainActivity.this, HomePageActivity.class);
 //                               startActivity(i);
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, SelectUserTypeActivity.class);
                System.out.println("Marvin");
                startActivity(i);


            }
        });
    }

    public boolean isUserLoggedIn() {
        boolean bool = false;
        mFirebaseAuth = FirebaseAuth.getInstance();
        if (mFirebaseAuth.getCurrentUser() != null) {
            Toast.makeText(MainActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
            //Intent i = new Intent(MainActivity.this, HomePageActivity.class);
            //startActivity(i);
            bool = true;
        } else {
            Toast.makeText(MainActivity.this, "You are logged out. Please log in again.", Toast.LENGTH_SHORT).show();
        }
        return bool;
    }

    @Override
    public void onStart() {
        super.onStart();
        isUserLoggedIn();
    }
}