package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;

public class EditUserDetailsForDelivery extends AppCompatActivity {

    private Button Submit,Edit;
    String colomboArea;
    Spinner colombo_area_txt ;
    private String orderAmount="";
    private String getUpdateId="";
    private TextView Total_order_price;
    private String OrderId;
    private EditText edit_Text_PersonName,edit_Text_Phone,edit_text_address;

    private String Ordername;
    private String PhoneNo;
    private String Address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details_for_delivery);

        edit_Text_PersonName    = (EditText)findViewById(R.id.edit_Text_PersonName_edit_delivery);
        edit_Text_Phone         = (EditText)findViewById(R.id.edit_Text_Phone_edit_delivery);
        edit_text_address       = (EditText)findViewById(R.id.edit_text_address_edit_delivery);


        Submit                  =(Button)findViewById(R.id.Submit_button_edit_delivery);


        OrderId = getIntent().getStringExtra("OrderId");

        showDetails();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateOrderDetails();

            }
        });



    }

    private void showDetails() {

        DatabaseReference showDetails = FirebaseDatabase.getInstance().getReference().child("Order").child(OrderId);

        showDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                     Ordername =snapshot.child("name").getValue().toString();
                     PhoneNo = snapshot.child("phoneNo").getValue().toString();
                     Address =snapshot.child("address").getValue().toString();

                        edit_Text_PersonName.setText(Ordername);
                        edit_Text_Phone.setText(PhoneNo);
                        edit_text_address.setText(Address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    private void updateOrderDetails() {

        DatabaseReference updateOrder = FirebaseDatabase.getInstance().getReference().child("Orders");

        HashMap<String,Object> orderMap =new HashMap<>();

        orderMap.put("name"         ,edit_Text_PersonName   .getText().toString());
        orderMap.put("phoneNo"      ,edit_Text_Phone        .getText().toString());
        orderMap.put("address"      ,edit_text_address      .getText().toString());
        orderMap.put("colomboArea"  ,colomboArea);

        updateOrder.child(OrderId).updateChildren(orderMap);

        Toast.makeText(EditUserDetailsForDelivery.this,"Delivery info Updated Successfully",Toast.LENGTH_SHORT).show();
        finish();

    }


}