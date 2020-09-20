package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CourierLoginActivity extends AppCompatActivity {


    private TextView forget_password;
    private Button courier_signup_btn,courier_login_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_login);


        courier_login_btn =(Button)findViewById(R.id.login_button);
        courier_signup_btn =(Button)findViewById(R.id.signup_button);
        forget_password =(TextView)findViewById(R.id.forget_password);
        courier_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierLoginActivity.this,CourierDashboardActivity.class);
                startActivity(intent);
            }
        });

        courier_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierLoginActivity.this,CourierCreateAccount.class);
                startActivity(intent);
            }
        });

        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierLoginActivity.this,CourierResetPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}