package com.example.ecommerceapp;


import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dashboard extends AppCompatActivity implements View.OnClickListener {

    public CardView card1, card2, card3, card4, card5, card6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        card1 = (CardView) findViewById(R.id.c1);
        card2 = (CardView) findViewById(R.id.c2);
        card3 = (CardView) findViewById(R.id.c3);
        card4 = (CardView) findViewById(R.id.c4);
        card5 = (CardView) findViewById(R.id.c5);
        card6 = (CardView) findViewById(R.id.c6);

        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        card5.setOnClickListener(this);
        //card6.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.c1:
                intent = new Intent(this, addProducts.class);
                startActivity(intent);
                break;

            case R.id.c2:
                intent = new Intent(this, deliveryList.class);
                startActivity(intent);
                break;

            case R.id.c3:
                intent = new Intent(this, myProducts.class);
                startActivity(intent);
                break;

            case R.id.c4:
                intent = new Intent(this, profile.class);
                startActivity(intent);
                break;

            case R.id.c5:
                intent = new Intent(this, orderList.class);
                startActivity(intent);
                break;
        }

        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void openDialog() {
        LogoutDialog logoutdialog = new LogoutDialog();
        logoutdialog.show(getSupportFragmentManager(), "logoutdialog");
    }
}