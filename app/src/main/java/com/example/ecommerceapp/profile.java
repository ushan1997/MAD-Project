package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerceapp.Model.Admin;
import com.example.ecommerceapp.Prevalent.AdminPrevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profile extends AppCompatActivity {
    TextView AdProfileName, AdProfileEmail, AdProfilePhoneNumber, AdProfileAddress;
    Button update;
    DatabaseReference AdminRef;
    Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AdProfileName   = (TextView)findViewById(R.id.AdminProfileNameTextview);
        AdProfileEmail  = (TextView)findViewById(R.id.AdminProfileEmailTextview);
        AdProfilePhoneNumber    = (TextView)findViewById(R.id.AdminProfilePhoneTextview);
        AdProfileAddress = (TextView)findViewById(R.id.AdminProfileAddressTextview);

        AdminRef = FirebaseDatabase.getInstance().getReference().child("Admin");
        update =(Button) findViewById(R.id.UpdateProfilebtn);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(profile.this, AdminProfileUpdate.class);
                intent.putExtra("Phone", AdminPrevalent.currentOnlineAdmin.getPhone());

                startActivity(intent);
            }
        });
        adminInfoDisplay(AdProfileName,AdProfileEmail,AdProfilePhoneNumber,AdProfileAddress);

    }

    private void adminInfoDisplay(TextView adProfileName, TextView adProfileEmail, TextView adProfilePhoneNumber, TextView adProfileAddress) {
        DatabaseReference AdminRef= FirebaseDatabase.getInstance().getReference().child("Admin").child(AdminPrevalent.currentOnlineAdmin.getPhone());
        AdminRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String Name     = snapshot.child("Name").getValue().toString();
                    String Email    = snapshot.child("Email").getValue().toString();
                    String Phone    = snapshot.child("Phone").getValue().toString();
                    String Address  =snapshot.child("Address").getValue().toString();

                    AdProfileName.setText(Name);
                    AdProfileEmail.setText(Email);
                    AdProfilePhoneNumber.setText(Phone);
                    AdProfileAddress.setText(Address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}