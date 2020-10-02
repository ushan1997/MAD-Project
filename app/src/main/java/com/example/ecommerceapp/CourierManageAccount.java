package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CourierManageAccount extends AppCompatActivity {

    private Button courier_update,courier_todelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_manage_account);

        courier_update =(Button)findViewById(R.id.update_button);
        courier_todelete =(Button)findViewById(R.id.todelete_button);

        courier_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierManageAccount.this,CourierDashboardActivity.class);
                startActivity(intent);
            }
        });

        /*courier_todelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierManageAccount.this,CourierDeleteAccount.class);
                startActivity(intent);
            }
        });*/
    }
}