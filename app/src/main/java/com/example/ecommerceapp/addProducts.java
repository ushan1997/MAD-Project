package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ecommerceapp.Model.Product;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addProducts extends AppCompatActivity {

    private String  saveCurrentDate, saveCurrentTime, downloadImageUrl;
    private EditText txtProductCode, txtProductName, txtProductQuantity, txtProductPrice, txtProductDescription;
    private ImageView ivProductImageInput;
    private ImageButton btnIAddProduct;
    private DatabaseReference dbRef;
    private String ProductRandomKey;
    public String id;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    // private ProgressDialog loadingBar;
    String code,name,quantity,price,description;
    StorageReference filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        dbRef = FirebaseDatabase.getInstance().getReference().child("Product");

        txtProductCode = (EditText) findViewById(R.id.product_code_input);
        txtProductName = (EditText) findViewById(R.id.product_name_input);
        txtProductQuantity = (EditText) findViewById(R.id.product_quantity_input);
        txtProductPrice = (EditText) findViewById(R.id.product_price_input);
        txtProductDescription = (EditText) findViewById(R.id.product_Description_input);

        btnIAddProduct = (ImageButton) findViewById(R.id.addProductButton);

        ivProductImageInput = (ImageView) findViewById(R.id.product_image);
        // loadingBar = new ProgressBar(this);

        ivProductImageInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });


        btnIAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(addProducts.this, dashboard.class);
                startActivity(intent);
                AddProduct();
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick &&  resultCode == RESULT_OK && data!= null){
            ImageUri = data.getData();
            ivProductImageInput.setImageURI(ImageUri);
        }
    }

    private void AddProduct () {

        code = txtProductCode.getText().toString();
        name = txtProductName.getText().toString();
        quantity = txtProductQuantity.getText().toString();
        price = txtProductPrice.getText().toString();
        description = txtProductDescription.getText().toString();

        if(ImageUri == null){
            Toast.makeText(this,"Product image is mandatory..", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "Please Enter the product code", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Please Enter product name", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(quantity)) {
            Toast.makeText(this, "Please Enter quantity", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please Enter product price", Toast.LENGTH_SHORT).show();

        } else if (TextUtils.isEmpty(description)) {
            Toast.makeText(this, "Enter product description", Toast.LENGTH_SHORT).show();
        } else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        ProductRandomKey = saveCurrentDate +" "+ saveCurrentTime;
        filePath =  ProductImagesRef.child(ImageUri.getLastPathSegment() +ProductRandomKey );
        final UploadTask uploadTask = filePath.putFile(ImageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(addProducts.this,"Error" + message, Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(addProducts.this, "Image uploaded Successfully ...", Toast.LENGTH_SHORT).show();
                Task<Uri>  urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>()
                {@Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw  task.getException();
                    }
                    downloadImageUrl = filePath.getDownloadUrl().toString();
                    return  filePath.getDownloadUrl();
                }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful()){
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(addProducts.this, "got the Product Image url successfully", Toast.LENGTH_SHORT).show();
                            saveProductInformationToDatabase();
                        }
                    }
                });
            }
        });
    }

    private void saveProductInformationToDatabase ( ) {

        HashMap<String, Object> it = new HashMap<>();
        it.put("pid", ProductRandomKey);
        it.put("code", code);
        it.put("name", name);
        it.put("quantity", quantity);
        it.put("price", price);
        it.put("description", description);
        it.put("image", downloadImageUrl);
        it.put("date", saveCurrentDate);
        it.put("time", saveCurrentTime);

        dbRef.child(ProductRandomKey).updateChildren(it)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(addProducts.this, "Product Added Successfully ", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(addProducts.this, dashboard.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(addProducts.this, "Network error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}