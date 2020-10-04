package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ecommerceapp.Prevalent.AdminPrevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.HashMap;

public class editProducts extends AppCompatActivity {
    private EditText editProductCode, editProductName, editProductQuantity, editProductPrice, editProductDescription;
    private Button updateButton;
    private ImageView editProductImage;
    DatabaseReference reference;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference strorageProductPictureRef;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_products);

        reference = FirebaseDatabase.getInstance().getReference("Product");

        editProductCode = (EditText) findViewById(R.id._productCode);
        editProductName = (EditText) findViewById(R.id._productName);
        editProductQuantity = (EditText) findViewById(R.id._productQuantity);
        editProductPrice = (EditText) findViewById(R.id._productPrice);
        editProductDescription = (EditText) findViewById(R.id._productDescription);
        editProductImage = (ImageView) findViewById(R.id._productImage);
        updateButton = (Button) findViewById(R.id.product_update__btn);
        code = getIntent().getStringExtra("code");
        productInfoDisplay();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnlyProductInfo();
            }
        });
    }

    private void productInfoSaved() {
        if (TextUtils.isEmpty(editProductCode.getText().toString())) {
            Toast.makeText(this, "Code is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editProductName.getText().toString())) {
            Toast.makeText(this, "name is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editProductQuantity.getText().toString())) {
            Toast.makeText(this, "quantity is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editProductPrice.getText().toString())) {
            Toast.makeText(this, "price is mandatory", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(editProductDescription.getText().toString())) {
            Toast.makeText(this, "description is mandatory", Toast.LENGTH_SHORT).show();
        } else {
            updateOnlyProductInfo();
        }

    }


    private void updateOnlyProductInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("code", editProductCode.getText().toString());
        userMap.put("name", editProductName.getText().toString());
        userMap.put("quantity", editProductQuantity.getText().toString());
        userMap.put("price", editProductPrice.getText().toString());
        userMap.put("description", editProductDescription.getText().toString());

        ref.child(code).updateChildren(userMap);

        startActivity(new Intent(editProducts.this, myProducts.class));
        Toast.makeText(editProducts.this, "Product Info updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void productInfoDisplay() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Product").child(code);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String code         = snapshot.child("code").getValue().toString();
                    String name         = snapshot.child("name").getValue().toString();
                    String quantity     = snapshot.child("quantity").getValue().toString();
                    String price        =snapshot.child("price").getValue().toString();
                    String description       =snapshot.child("description").getValue().toString();

                    editProductCode.setText(code);
                    editProductName.setText(name);
                    editProductQuantity.setText(quantity);
                    editProductPrice.setText(price);
                    editProductDescription.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}