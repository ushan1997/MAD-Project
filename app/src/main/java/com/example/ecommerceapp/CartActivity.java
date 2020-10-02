package com.example.ecommerceapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerceapp.Model.Cart;
import com.example.ecommerceapp.Model.Product;
import com.example.ecommerceapp.Prevalent.Prevalent;
import com.example.ecommerceapp.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recylarView;
     LinearLayoutManager layoutManager;

    private TextView totalamount;
    private Button Cartbutton_buy;
    private TextView getTotalamount;
    private int AllProductPrice;
    private int calcTotPrice;
    private ArrayList<Cart> cartList= new ArrayList<>() ;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_activity);

        recylarView         = (RecyclerView) findViewById(R.id.recycler_menu_for_cart);
        recylarView.setHasFixedSize(true);
        layoutManager       = new LinearLayoutManager(this);
        recylarView.setLayoutManager(layoutManager);

        Cartbutton_buy      = (Button)findViewById(R.id.cart_button_BuyNow);
        getTotalamount      = (TextView) findViewById(R.id.cart_total);



        Cartbutton_buy.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        getTotalamount.setText("Rs "+String.valueOf(AllProductPrice));
                        getOrderDetails();
                        Intent intent = new Intent(CartActivity.this,GetUserDetailsForDelivery.class);

                        intent.putExtra("Cart_List",cartList);


                        intent.putExtra("TotalOrderPrice",String.valueOf(AllProductPrice));
                        startActivity(intent);

                    }
                });


    }

    private void getOrderDetails() {


    }

    @Override
    protected void onStart() {
        super.onStart();
        final Product product = null;
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");

        FirebaseRecyclerOptions <Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery( cartListRef.child("Users View" ).child( Prevalent.currentOnlineUser .getPhno()) .child("Product") , Cart.class) .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model) {
                
                holder.productName.setText(model.getPname());
                holder.productDescription.setText(model.getPdescription());
                holder.productPrice.setText("Price Rs"+model.getPprice());
                holder.productQuantity.setText("Quantity "+model.getPquantity());
                Picasso.get().load(model.getPimage()).into(holder.product_image );

                calcTotPrice= (Integer.valueOf(model.getPprice())*Integer.valueOf(model.getPquantity()));

                AllProductPrice = AllProductPrice + calcTotPrice;

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            CharSequence options[] = new CharSequence[]{ "Remove"};

                            AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                            builder.setTitle("My Cart List Options");

                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    if(i==0) {
                                       Task<Void> deleteFile=cartListRef.child("Users View").child(Prevalent.currentOnlineUser.getPhno()).child("Product").child(String.valueOf(model.getPcode())).removeValue();
                                       deleteFile .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(CartActivity.this, "Removed Selected Item", Toast.LENGTH_SHORT).show();
                                                            Intent intent = new Intent(CartActivity.this, CartActivity.class);
                                                            startActivity(intent);
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                            builder.show();
                        }
                    });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recylarView.setAdapter(adapter);
        adapter.startListening();
    }

}