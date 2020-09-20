package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class GetUserDetailsForDelivery extends AppCompatActivity {

    Button Submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_for_delivery);

        Submit=(Button)findViewById(R.id.Submit_button);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(GetUserDetailsForDelivery.this,PendingListActivity.class);
                startActivity(intent);
            }
        });

    }
}
