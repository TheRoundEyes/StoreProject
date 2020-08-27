package com.example.projectstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.projectstore.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    Button cancel, send;
    EditText phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        cancel = (Button)findViewById(R.id.cancelButton);
        send = (Button)findViewById(R.id.sendButton);
        phoneNumber = (EditText)findViewById(R.id.passwordField);
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

    private void sendButtonClick() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pn = phoneNumber.getText().toString();
                if(pn.equals("")) {
                    phoneNumber.setError("Phone number is required.");
                }
                else if(!pn.matches("\\d.*")) {
                    phoneNumber.setError("Phone Number should only contain digits.");
                }
                else if(pn.length() != 11) {
                    phoneNumber.setError("Phone number should be 11 digits");
                }
                else {
                    Intent i = new Intent(ForgotPasswordActivity.this, ForgotPasswordCodeSent.class);
                    i.putExtra("phoneNumber", pn);
                    startActivity(i);
                }
            }
        });
    }


}