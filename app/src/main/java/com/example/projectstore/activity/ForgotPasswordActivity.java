package com.example.projectstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectstore.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button cancel, send;
    EditText phoneNumber;
    TextView alertText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        cancel = (Button)findViewById(R.id.cancelButton);
        send = (Button)findViewById(R.id.send);
        phoneNumber = (EditText)findViewById(R.id.password);
        alertText = (TextView)findViewById(R.id.alertText);
        cancelButtonClick();
        sendButtonClick();
    }

    private void cancelButtonClick() {
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
            }
        });
    }

    boolean isExisting = false;

    private void sendButtonClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ForgotPasswordActivity.this, "Please wait for a second", Toast.LENGTH_SHORT).show();
                final String pn = phoneNumber.getText().toString();
                if(pn.equals("")) {
                    phoneNumber.setError("Phone number is required.");
                }
                else if(pn.matches("[\\D]+") || !pn.substring(0, 2).equals("09")) {
                    phoneNumber.setError("Enter a valid Phone number.");
                }
                else if(pn.length() != 11) {
                    phoneNumber.setError("Phone number should be 11 digits");
                }
                else {
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String un = "";
                            for(DataSnapshot data : snapshot.getChildren()) {
                                String cn = data.child("contactNumber").getValue().toString();
                                if(cn.equals(pn)) {
                                    isExisting = true;
                                    un = data.child("username").getValue().toString();
                                    break;
                                }
                                else isExisting = false;
                            }
                            if(isExisting) {
                                Intent i = new Intent(ForgotPasswordActivity.this, ForgotPasswordCodeSent.class);
                                i.putExtra("phoneNumber", pn);
                                i.putExtra("username", un);
                                startActivity(i);
                            }
                            else alertText.setText("Phone number is not existing.");
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {}
                    });
                }
            }
        });
    }
}