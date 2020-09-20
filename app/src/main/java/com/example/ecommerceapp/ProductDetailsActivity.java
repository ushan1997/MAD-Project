package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailsActivity extends AppCompatActivity {

    Button Buynow,addCart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        Buynow=(Button)findViewById(R.id.btn_Buy_now_product);
        addCart=(Button)findViewById(R.id.btn_Add_to_cart_product);

        Buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductDetailsActivity.this, GetUserDetailsForDelivery.class);
                startActivity(intent);

            }
        });

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(ProductDetailsActivity.this,"Added to cart ", Toast.LENGTH_SHORT).show();

            }
        });



    }
}
