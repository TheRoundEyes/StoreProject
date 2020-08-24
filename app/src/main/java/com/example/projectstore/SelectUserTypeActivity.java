package com.example.projectstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectUserTypeActivity extends AppCompatActivity {

    Button customer, store_owner;
    User user;
    boolean isCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        customer = (Button)findViewById(R.id.customer_id);
        store_owner = (Button)findViewById(R.id.store_owner_id);

        user = new User();
        isCustomer = false;
        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isCustomer = true;
                user.setCustomer(isCustomer);
                Toast.makeText(SelectUserTypeActivity.this, "User: Customerrrrrrrrrr", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SelectUserTypeActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        store_owner.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                isCustomer = false;
                Toast.makeText(SelectUserTypeActivity.this, "User: Store ownerrrrrrrrrrr", Toast.LENGTH_LONG).show();
                user.setCustomer(isCustomer);
                Intent i = new Intent(SelectUserTypeActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}