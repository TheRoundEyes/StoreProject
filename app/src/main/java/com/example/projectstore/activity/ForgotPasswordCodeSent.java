package com.example.projectstore.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projectstore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordCodeSent extends AppCompatActivity {
    String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_code_sent);
        String pn = getIntent().getStringExtra("phoneNumber");
        sendVerificationCodeToUser(pn);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken force) {
            verificationCode = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential cred) {
            String code = cred.getSmsCode();
            if(code != null) verifyCode(code);
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgotPasswordCodeSent.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode, code);
        signInCredentials(credential);
    }

    private void signInCredentials(PhoneAuthCredential cred) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithCredential(cred).addOnCompleteListener(ForgotPasswordCodeSent.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Intent i = new Intent(ForgotPasswordCodeSent.this, ResetPasswordActivity.class);
                    i.putExtra("Username", getIntent().getStringExtra("username"));
                    startActivity(i);
                }
                else Toast.makeText(ForgotPasswordCodeSent.this, "It is not your number.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void sendVerificationCodeToUser(String pn) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+63" + pn,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
}