package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourierResetPasswordActivity extends AppCompatActivity {

    private Button courier_reset_password_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_reset_password);

        courier_reset_password_btn =(Button)findViewById(R.id.reset_button);

        courier_reset_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierResetPasswordActivity.this,CourierLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}