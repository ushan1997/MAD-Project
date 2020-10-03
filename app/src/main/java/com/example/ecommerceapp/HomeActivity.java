/**IT1975140
 Gunaratne U.A
 Metro Weekday*/
package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Model.Product;
import com.example.ecommerceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity {

    public TextView productName, productDescription;
    public ImageButton productImage;
    public View itemView;
    public Button btn_Buy_now1,btn_Buy_now2,btn_Buy_now3,btn_Buy_now4;
    public Button cart_button1,cart_button2,cart_button3,cart_button4;
    public ImageButton bottom_cart_navigation;
    public ImageButton user_profile;
    private DatabaseReference ProductRef;
    private RecyclerView recyclerView;
     LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_home);


        ProductRef= FirebaseDatabase.getInstance().getReference().child("Product");

        user_profile =(ImageButton)findViewById(R.id.bottom_navigation_profile);

        bottom_cart_navigation=(ImageButton)findViewById(R.id.bottom_cart_navigation);

        bottom_cart_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });
        user_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(HomeActivity.this, ViewMyProfile.class);
                startActivity(intent);
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_menu_for_home);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        onBackPressed();
    }

    @Override
    protected void onStart() {/**Retrieve product from firebase*/
        super.onStart();

        FirebaseRecyclerOptions<Product> options = new FirebaseRecyclerOptions.Builder <Product> ().setQuery(ProductRef,Product.class).build();

        FirebaseRecyclerAdapter<Product, ProductViewHolder> adapter = new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Product model) {

                    holder.productName.setText(model.getName());
                    holder.productDescription.setText(model.getDescription());

                    Picasso.get().load(model.getImage()).into(holder.imageViewButton);

                    holder.imageViewButton.setOnClickListener(new View.OnClickListener() {/**When image click it */

                        @Override
                        public void onClick(View view) {

                            Intent intent = new Intent(HomeActivity.this,ProductDetailsActivity.class);
                            intent.putExtra("code",model.getPid());
                            startActivity(intent);
                        }
                    });
            }

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home,parent,false);
                ProductViewHolder productViewHolder = new ProductViewHolder(view);
                return productViewHolder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}


