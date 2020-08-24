package com.example.projectstore.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectstore.R;

public class HomePageActivity extends AppCompatActivity {

    Button logout_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        logout_btn = (Button)findViewById(R.id.logout_btn);

        logout_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
}