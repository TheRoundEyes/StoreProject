package com.example.projectstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.projectstore.R;

public class SelectUserTypeActivity extends AppCompatActivity {
    Button customer, storeOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        customer = (Button)findViewById(R.id.customer_id);
        storeOwner = (Button)findViewById(R.id.store_owner_id);

        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserTypeActivity.this, RegisterActivity.class);
                i.putExtra("UserType", "Customer");
                startActivity(i);
            }
        });

        storeOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SelectUserTypeActivity.this, RegisterActivity.class);
                i.putExtra("UserType", "StoreOwner");
                startActivity(i);
            }
        });
    }
}