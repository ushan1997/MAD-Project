package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button join_btn,login_btn;
    private TextView as_admin_link, delivery_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        join_btn    =(Button)findViewById(R.id.main_join_button);
        login_btn   =(Button)findViewById(R.id.main_login_button);
        as_admin_link=(TextView)findViewById(R.id.textView4);
        delivery_link=(TextView)findViewById(R.id.textView5);

        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                    startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        as_admin_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,adminLogin.class);
                startActivity(intent);
            }
        });

        delivery_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CourierLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}