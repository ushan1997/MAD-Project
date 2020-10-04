/**IT1975140
 Gunaratne U.A
 Metro Weekday*/
package com.example.ecommerceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.ContentLoadingProgressBar;

import com.example.ecommerceapp.Model.Cart;
import com.example.ecommerceapp.Model.Orders;
import com.example.ecommerceapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class GetUserDetailsForDelivery extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button Submit,Edit;
    String colomboArea;
    Spinner  colombo_area_txt ;
    private String orderAmount="";
    private String order="";
    private TextView Total_order_price;
    private EditText edit_Text_PersonName,edit_Text_Phone,edit_text_address;
    private ArrayList<Cart> cartlist;
    private String OrderId;
    private DatabaseReference dbRef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details_for_delivery);

        edit_Text_PersonName    = (EditText)findViewById(R.id.edit_Text_PersonName);
        edit_Text_Phone         = (EditText)findViewById(R.id.edit_Text_Phone);
        edit_text_address       = (EditText)findViewById(R.id.edit_text_address);
        colombo_area_txt         =(Spinner)findViewById(R.id.colombo_area_txt);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.numbers,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        colombo_area_txt.setAdapter(adapter);
        colombo_area_txt.setOnItemSelectedListener(GetUserDetailsForDelivery.this);


        orderAmount = getIntent().getStringExtra("TotalOrderPrice");


        cartlist = new ArrayList<Cart>();


        Total_order_price = (TextView) findViewById(R.id.Total_order_price);

        Total_order_price.setText("Your Total Order Price Rs"+orderAmount);

        Toast.makeText(this,"Total Order Rs "+orderAmount,Toast.LENGTH_SHORT).show();

        Submit=(Button)findViewById(R.id.Submit_button);
        Edit =(Button)findViewById(R.id.edit_in_order_button);


        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                submittingOrderFrom();
//                Intent intent= new Intent(GetUserDetailsForDelivery.this,PendingListActivity.class);
//                startActivity(intent);
            }
        });

        dbRef = FirebaseDatabase.getInstance().getReference().child("Cart").child("Users View")
                .child(Prevalent.currentOnlineUser.getPhno()).child("Product");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                /**A DataSnapshot instance contains data from a Firebase Database location.
                 Any time you read Database data, you receive the data as a DataSnapshot.*/
                if(snapshot.hasChildren()){
                    for(DataSnapshot snapshot1:snapshot.getChildren()){

                        Cart cart = snapshot1.getValue(Cart.class);

                        Log.e("Cheaking list size 1",String.valueOf(cartlist.size()) );

                        cartlist.add(cart);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }


    private void submittingOrderFrom() {

        if(TextUtils.isEmpty(edit_Text_PersonName.getText().toString())){

            Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(edit_Text_Phone.getText().toString())){

            Toast.makeText(this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(edit_text_address.getText().toString())){

            Toast.makeText(this,"Please Enter Address",Toast.LENGTH_SHORT).show();

        } else  if(TextUtils.isEmpty(colomboArea)){

            Toast.makeText(this,"Please Enter Colombo Area",Toast.LENGTH_SHORT).show();
        }
        else{

                ConfirmOrder();
        }

    }


    private void ConfirmOrder() {

        final String saveCurrentTime,saveCurrentDate;

        Calendar calendar =  Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());//get the current date in international format

        SimpleDateFormat currentTime= new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());//get the current time in international format

         OrderId = saveCurrentDate+"_"+saveCurrentTime+"OrderId";

        final DatabaseReference orderRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(OrderId);



        HashMap<String,Object> orderMap =new HashMap<>();

        orderMap.put("orderId",OrderId );
        orderMap.put("name"         ,edit_Text_PersonName   .getText().toString());
        orderMap.put("phoneNo"      ,edit_Text_Phone        .getText().toString());
        orderMap.put("address"      ,edit_text_address      .getText().toString());
        orderMap.put("colomboArea"  ,colomboArea);
        orderMap.put("orderDate"    ,saveCurrentDate);
        orderMap.put("orderTime"    ,saveCurrentTime);
        /**orderMap.put("cartLit",      colomboArea);*/
        orderMap.put("state","Not Accepted");

        orderRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Cart cart = new Cart();
                    FirebaseDatabase.getInstance().getReference().child("Cart Backup").child(Prevalent.currentOnlineUser.getPhno())
                            .child(OrderId).setValue(cartlist);

                    FirebaseDatabase.getInstance().getReference()/**when confirm order cart list of users get clear*/
                            .child("Cart")
                            .child("Users View")
                            .child(Prevalent.currentOnlineUser.getPhno())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        Toast.makeText(GetUserDetailsForDelivery.this,"Your order is successful",Toast.LENGTH_SHORT).show();
                                        Toast.makeText(GetUserDetailsForDelivery.this,"If you want you can edit order right now",Toast.LENGTH_SHORT).show();
                                        clearConroles();
                                       // Intent intent = new Intent(GetUserDetailsForDelivery.this,HomeActivity.class);
                                        //startActivity(intent);
                                    }
                                }
                            });

                        Edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //  editOrderForm();
                            Intent intent = new Intent(GetUserDetailsForDelivery.this,EditUserDetailsForDelivery.class);
                            intent.putExtra("OrderId",OrderId);
                            startActivity(intent);


                        }
                    });

                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        colomboArea = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(),colomboArea,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

//    private void editOrderForm() {
//
//        final Orders order = new Orders();
//        final DatabaseReference updateOrderRef = FirebaseDatabase.getInstance().getReference().child("Orders");
//        updateOrderRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.hasChild(Prevalent.currentOnlineUser.getPhno())){
//                    try{
//
//                        order.setAddress(edit_text_address.getText().toString().trim());
//                        order.setPhoneNo(edit_Text_Phone .getText().toString().trim());
//                        order.setName(edit_Text_PersonName.getText().toString().trim());
//                        order.setColomboArea(colomboArea);
//
//                        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhno());
//                        dbRef.setValue(order);
//
//                        Toast.makeText(getApplicationContext(),"Data Uploaded Successfully",Toast.LENGTH_SHORT).show();
//
//
//                    }catch (NumberFormatException e){
//
//                        Toast.makeText(getApplicationContext(),"Data Uploaded Failure",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
    public void clearConroles(){

        edit_Text_PersonName.setText("");
        edit_text_address.setText("");
        edit_Text_Phone.setText("");
    }



}
