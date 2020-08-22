package com.example.projectstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    public EditText fullname, location, contactnumber, storename, emailId, passwordId, repassword;
    public Button signUp;
    public TextView loginHere, alertTitle;

    DatabaseReference dbRef;
    FirebaseAuth mFireBaseAuth;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullname = (EditText)findViewById(R.id.fullname);
        location = (EditText)findViewById(R.id.location);
        contactnumber = (EditText)findViewById(R.id.contactnumber);
        storename = (EditText)findViewById(R.id.storename);
        emailId = (EditText)findViewById(R.id.email);
        passwordId = (EditText)findViewById(R.id.password);
        repassword = (EditText)findViewById(R.id.re_password);
        loginHere = (TextView)findViewById(R.id.loginHere);
        alertTitle = (TextView)findViewById(R.id.alertTitle);
        signUp = (Button)findViewById(R.id.signUpBtn);

        mFireBaseAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String full_name = fullname.getText().toString();
                final String loc = location.getText().toString();
                final String contact = contactnumber.getText().toString().trim();
                final String store = storename.getText().toString();
                final String email = emailId.getText().toString();
                final String repass = repassword.getText().toString();
                final String password = passwordId.getText().toString();
                if(TextUtils.isEmpty(full_name)) {
                    fullname.setError("Fullname cannot be empty");
                } else if(TextUtils.isEmpty(loc)) {
                    location.setError("Location cannot be empty");
                } else if(TextUtils.isEmpty(contact)) {
                    contactnumber.setError("Contact cannot be empty");
                } else if(TextUtils.isEmpty(store)) {
                    storename.setError("Storename cannot be empty");
                } else if(TextUtils.isEmpty(email)) {
                    emailId.setError("Password cannot be empty");
                } else if(TextUtils.isEmpty(password)) {
                    passwordId.setError("Password cannot be empty");
                } else if(!(TextUtils.isEmpty(full_name) && TextUtils.isEmpty(loc) && TextUtils.isEmpty(contactnumber.getText().toString()) && TextUtils.isEmpty(store) && TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(repass) && password.equals(repass))) {
                    if(passwordId.getText().toString().equals(repassword.getText().toString())) {
                        writeNewUser(full_name, loc, contact, store, email, password);
                        mFireBaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!(task.isSuccessful())) {
                                    Toast.makeText(RegisterActivity.this, "Login unsuccessful: ", Toast.LENGTH_SHORT).show();
                                } else if (task.isSuccessful()) {
                                    Toast.makeText(RegisterActivity.this, "Registration Complete: ", Toast.LENGTH_SHORT).show();
                                    Intent inToHome = new Intent(RegisterActivity.this, HomePageActivity.class);
                                    startActivity(inToHome);
                                }
                            }
                        });
                    }
                    else {  
                        alertTitle.setVisibility(TextView.VISIBLE);
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void writeNewUser(String fullname, String location, String contactnumber, String store, String email, String password) {
        user = new User();
        Long cn = Long.parseLong(contactnumber);
        user.setFullname(fullname);
        user.setLocation(location);
        user.setContactnumber(cn);
        user.setStorename(store);
        user.setEmailaddress(email);
        user.setPassword(password);
        dbRef.push().child("users").setValue(user);
    }
}