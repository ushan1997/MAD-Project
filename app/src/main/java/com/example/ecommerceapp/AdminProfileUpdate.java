package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminProfileUpdate extends AppCompatActivity {
    private EditText AdminNameUpdate, AdminEmailUpdate, AdminPhoneUpdate, AdminAddressUpdate;
    private Button updateBtn2;
    private String Phone;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile_update);
        reference = FirebaseDatabase.getInstance().getReference("Admin");

        AdminNameUpdate = (EditText)findViewById(R.id.adminNameUpdate);
        AdminEmailUpdate = (EditText)findViewById(R.id.adminEmailUpdate);
        AdminPhoneUpdate = (EditText)findViewById(R.id.adminPhoneUpdate);
        AdminAddressUpdate = (EditText)findViewById(R.id.adminAddressUpdate);
        updateBtn2 = (Button)findViewById(R.id.updatebtn2);

        Phone = getIntent().getStringExtra("Phone");

        AdminInfoDisplay();

        updateBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateOnlyAdminInfo();
            }
        });
    }


    private void updateOnlyAdminInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("Phone", AdminPhoneUpdate.getText().toString());
        userMap.put("Name", AdminNameUpdate.getText().toString());
        userMap.put("Email", AdminEmailUpdate.getText().toString());
        userMap.put("Address", AdminAddressUpdate.getText().toString());


        ref.child(Phone).updateChildren(userMap);

        startActivity(new Intent(AdminProfileUpdate.this, profile.class));
        Toast.makeText(AdminProfileUpdate.this, "Admin Info updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void AdminInfoDisplay() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Admin").child(Phone);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    String Phone         = snapshot.child("Phone").getValue().toString();
                    String Email         = snapshot.child("Email").getValue().toString();
                    String Name          = snapshot.child("Name").getValue().toString();
                    String Address        =snapshot.child("Address").getValue().toString();


                    AdminPhoneUpdate.setText(Phone);
                    AdminNameUpdate.setText(Name);
                    AdminEmailUpdate.setText(Email);
                    AdminAddressUpdate.setText(Address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}