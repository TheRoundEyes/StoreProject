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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import com.example.projectstore.R;
import com.example.projectstore.obj.Security;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public EditText username, password;
    public Button signInButton;
    public TextView registerHere, forgotPassword, alertText;
    FirebaseAuth mFirebaseAuth;
    int pressCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        signInButton = (Button)findViewById(R.id.signInBtn);
        registerHere = (TextView)findViewById(R.id.registerHere);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        alertText = (TextView)findViewById(R.id.alertText);

        forgotPasswordClickMethod();
        registerHereClickMethod();
        signInButtonClickMethod();
    }

    private void forgotPasswordClickMethod() {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ForgotPasswordActivity.class));
            }
        });
    }

    private void registerHereClickMethod() {
        registerHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelectUserTypeActivity.class));
            }
        });
    }

    private void signInButtonClickMethod() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String un = username.getText().toString();
                final String pw = password.getText().toString();
                if(!un.matches("[a-zA-Z0-9]+") || un.length() < 4) {
                    username.setError("Enter a valid Username.");
                }
                else if(pw.length() < 8) {
                    password.setError("Enter a valid Password.");
                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    Query checkUser = reference.orderByChild("username").equalTo(un);
                    checkUser.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                String pw_ = snapshot.child(un).child("password").getValue().toString();
                                String decryptedPassword = new Security().decryptData(pw_, un);
                                if(decryptedPassword.equals(pw)) {
                                    String email = snapshot.child(un).child("email").getValue().toString();
                                    mFirebaseAuth.signInWithEmailAndPassword(email, pw_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()) {
                                                startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                                            }
                                            else {
                                                Toast.makeText(MainActivity.this, "Error logging in.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                                else alertText.setText("Incorrect username or password.");
                            }
                            else alertText.setText("Incorrect username or password.");
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {}
                    });
                }
            }
        });
    }

    public void isUserLoggedIn() {
        mFirebaseAuth = FirebaseAuth.getInstance();

        if(mFirebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
        }
        else {
            Toast.makeText(MainActivity.this, "You are logged out. Please log in again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
//        isUserLoggedIn();
    }

    @Override
    public void onBackPressed() {
        if(pressCount >= 1) {
            finish();
            super.onBackPressed();
        }
        else {
            Toast.makeText(MainActivity.this, "Press again to exit.", Toast.LENGTH_SHORT).show();
            pressCount++;
        }
    }
}