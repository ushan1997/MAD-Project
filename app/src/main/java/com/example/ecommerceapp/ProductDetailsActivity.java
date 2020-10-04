package com.example.ecommerceapp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.ecommerceapp.Model.Cart;
import com.example.ecommerceapp.Model.Product;
import com.example.ecommerceapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailsActivity extends AppCompatActivity {

    private static int cartId = 0;
    private Button Buynow, addCart;
    private ElegantNumberButton numberButton;
    private ImageView productImage;
    private TextView displayPrice, displayName, displayDescription;
    private String code = "";
    private int calcAmount;
    private static ArrayList<Integer> list;
    private static HashMap<String, Object> cartMap;
    private Cart cart;
    DatabaseReference dbRef;
    public static final int stratid= 0;



    private RatingBar ratingBar;
    private Button submit;
    private EditText comment_add;
    private TextView addcommentbtn,itemcode,editcommentbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details);

        Buynow = (Button) findViewById(R.id.btn_Buy_now_product);
        addCart = (Button) findViewById(R.id.btn_Add_to_cart_product);
        numberButton = (ElegantNumberButton) findViewById(R.id.quantity_btn);
        productImage = (ImageView) findViewById(R.id.product_image_details);
        displayName = (TextView) findViewById(R.id.product_name_details);
        displayPrice = (TextView) findViewById(R.id.product_price);
        displayDescription = (TextView) findViewById(R.id.product_description_details);

        submit = (Button) findViewById(R.id.submit_button);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        addcommentbtn = (TextView) findViewById(R.id.add_comment_btn);
        comment_add = (EditText) findViewById(R.id.product_comments);
        itemcode =(TextView)findViewById(R.id.product_code);

        list = new ArrayList<>();

        code = getIntent().getStringExtra("code");

        getProductDetails(code);

        Buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductDetailsActivity.this, GetUserDetailsForDelivery.class);
                intent.putExtra("TotalOrderPrice", String.valueOf(calcAmount));
                startActivity(intent);

            }
        });



        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addingToCastList();
                //   Toast.makeText(ProductDetailsActivity.this,"Added to cart ", Toast.LENGTH_SHORT).show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String s = String.valueOf(ratingBar.getRating());
                //Toast.makeText(ProductDetailsActivity.this, s+"Star", Toast.LENGTH_SHORT).show();
                addRatings();

            }
        });



        addcommentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
                //Intent intent = new Intent(ProductDetailsActivity.this,AddCommentsToProduct.class);
                //intent.putExtra("code",code);
                //startActivity(intent);

            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference().child("Cart").child("Users View").child(Prevalent.currentOnlineUser.getPhno()).child("Product");
       dbRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               /**A DataSnapshot instance contains data from a Firebase Database location.
            Any time you read Database data, you receive the data as a DataSnapshot.*/
               if(snapshot.hasChildren()){
                   for(DataSnapshot snapshot1:snapshot.getChildren()){

                       Cart cart = snapshot1.getValue(Cart.class);

                       Log.e("Cheaking list size 1",String.valueOf(list.size()) );

                       list.add(cart.getPcode());

                   }
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });


    }


    private void AddComment() {

        String comment= comment_add.getText().toString().toLowerCase();
        // String code =itemcode.getText().toString();

        if(comment.equals("")){
            Toast.makeText(this, "Please add your comment ", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Product");

            HashMap<String,Object> userdataMap=new HashMap<>();
            userdataMap.put("comment",comment);

            ref.child(code).child("Comments").child(Prevalent.currentOnlineUser.getPhno())
                    .updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {

                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProductDetailsActivity.this, "You have commented", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                        startActivity(intent);

                        displayComment();
                    }
                }
            });
        }

    }
    private void addRatings() {
        String rating = String.valueOf(ratingBar.getRating());

        if(rating.equals("")){
            Toast.makeText(this, "Please rate ", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Product");

            HashMap<String,Object> userdataMap=new HashMap<>();
            userdataMap.put("rating",rating);

            ref.child(code).child("Customer Ratings").child(Prevalent.currentOnlineUser.getPhno())
                    .updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ProductDetailsActivity.this, "You have successfully added your rate to this product", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                        startActivity(intent);

                        //displayComment();
                    }
                }
            });
        }

    }


    private void displayComment() {

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Product").child(code).child(Prevalent.currentOnlineUser.getPhno());

        ref.child("Comments").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String comment= snapshot.child("comment").getValue().toString();

                    comment_add.setText(comment);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




    /**
     * Adding Product To CART LIST
     ***/
    private void addingToCastList() {

        String saveCurrentTime, saveCurrentDate;

        Calendar calendar = Calendar.getInstance();


        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());//get the current date in international format

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());//get the current date in international format


        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart");

        cart = new Cart();

        cart.setPcode(generateCartId());
        cart.setPdate(saveCurrentDate);
        cart.setPtime(saveCurrentTime);
        cart.setPdescription(displayDescription.getText().toString().trim());
        cart.setPdiscount(displayPrice.getText().toString().trim());
        cart.setPname(displayName.getText().toString().trim());
        cart.setPprice( displayPrice.getText().toString().trim());
        cart.setPquantity(numberButton.getNumber());
        cart.setPimage(productImage.toString());
        cart.setPdiscount("");

        cartListRef.child("Users View").child(Prevalent.currentOnlineUser.getPhno())
                .child("Product").child(String.valueOf(generateCartId()))
                .setValue(cart)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            final DatabaseReference cartBackup = FirebaseDatabase.getInstance().getReference();

                            Toast.makeText(ProductDetailsActivity.this, "Added to Cart to List", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                            intent.putExtra("CartProductList", displayName.getText().toString());/**PUT PRODUCT NAME FOR GETTING ORDER INFORMATION*/
                            intent.putExtra("CartListQuantity", numberButton.getNumber());/**PUT PRODUCT QUANTITY FOR GETTING ORDER INFORMATION*/
                            startActivity(intent);
                        }
                    }
                });

    }


    public void getProductDetails(String code) {
        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product").child(code);

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    Product product = snapshot.getValue(Product.class);

                    displayName.setText(product.getName());
                    displayPrice.setText(product.getPrice());
                    displayDescription.setText(product.getDescription());
                    Picasso.get().load(product.getImage()).into(productImage);
                    calcAmount = (Integer.valueOf(product.getPrice()) * Integer.valueOf(numberButton.getNumber()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public int generateCartId(){

        int cartId;
        int next =list.size();
        Log.e("Cheaking the list size 2",String.valueOf(next) );
        cartId=CommonConstraints.STARTING_ID_OF_ORDERS+next;

        if(list.contains(cartId)){
            next++;
            cartId=CommonConstraints.STARTING_ID_OF_ORDERS+next;
        }
        return cartId;
       // String.valueOf(cartId)
    }

    public int showEligibleItemQuantity(int x){
        if(x>10){
            return 1;
        }else{
           return  0;
        }

    }

}
