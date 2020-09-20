package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    public TextView productName, productDescription;
    public ImageButton productImage;
    public View itemView;
    public Button btn_Buy_now1,btn_Buy_now2,btn_Buy_now3,btn_Buy_now4;
    public Button cart_button1,cart_button2,cart_button3,cart_button4;
    public ImageButton bottom_cart_navigation;
    public ImageButton user_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btn_Buy_now1 = (Button) findViewById(R.id.btn_Buy_now1);
        btn_Buy_now2 = (Button) findViewById(R.id.btn_Buy_now2);
        btn_Buy_now3 = (Button) findViewById(R.id.btn_Buy_now3);
        btn_Buy_now4 = (Button) findViewById(R.id.btn_Buy_now4);

        productImage = (ImageButton) findViewById(R.id.product_image_1);
        user_profile =(ImageButton)findViewById(R.id.bottom_navigation);

        bottom_cart_navigation=(ImageButton)findViewById(R.id.bottom_cart_navigation);

        cart_button1=(Button)findViewById(R.id.btn_Add_to_cart1);
        cart_button2=(Button)findViewById(R.id.btn_Add_to_cart2);
        cart_button3=(Button)findViewById(R.id.btn_Add_to_cart3);
        cart_button4=(Button)findViewById(R.id.btn_Add_to_cart4);


        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, ProductDetailsActivity.class);
                startActivity(intent);

            }
        });

        bottom_cart_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });


        btn_Buy_now1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent1=new Intent(HomeActivity.this, GetUserDetailsForDelivery.class);
                startActivity(intent1);
            }
        });

        cart_button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeActivity.this,"Added to cart ",Toast.LENGTH_SHORT).show();



            }
        });
        btn_Buy_now2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent1=new Intent(HomeActivity.this, GetUserDetailsForDelivery.class);
                startActivity(intent1);
            }
        });

        cart_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeActivity.this,"Added to cart ",Toast.LENGTH_SHORT).show();



            }
        });
        btn_Buy_now3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent1=new Intent(HomeActivity.this,GetUserDetailsForDelivery.class);
                startActivity(intent1);
            }
        });

        cart_button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeActivity.this,"Added to cart ",Toast.LENGTH_SHORT).show();



            }
        });
        btn_Buy_now4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent intent1=new Intent(HomeActivity.this, GetUserDetailsForDelivery.class);
                startActivity(intent1);
            }
        });

        cart_button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(HomeActivity.this,"Added to cart ",Toast.LENGTH_SHORT).show();



            }
        });


        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeActivity.this, MyProfile.class);
                startActivity(intent);
            }
        });
    }


}


