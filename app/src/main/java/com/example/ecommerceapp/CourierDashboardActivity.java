package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CourierDashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView manage_account;


    public CardView card1, card2, card3, card4, card5, card6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier_dashboard);
        manage_account=(TextView)findViewById(R.id.manage_account);
        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);

        manage_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CourierDashboardActivity.this,CourierManageAccount.class);
                startActivity(intent);
            }
        });
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            // case R.id.c1:
            // intent = new Intent(this, PendingListActivity.class);
            // startActivity(intent);
            //break;

            case R.id.c2:
                intent = new Intent(this, OrdersInProgress.class);
                startActivity(intent);
                break;

            case R.id.c3:
                intent = new Intent(this, CourierNotifications.class);
                startActivity(intent);
                break;

            case R.id.c4:
                intent = new Intent(this, MyEarnings.class);
                startActivity(intent);
                break;

        }


    }


}