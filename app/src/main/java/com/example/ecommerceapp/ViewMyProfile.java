package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewMyProfile extends AppCompatActivity {
    public Button edit_btn,delete_btn,logout_btn;
    private TextView u_name,u_email,u_phone,u_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_profile);

        edit_btn =(Button)findViewById(R.id.edit_button);
        delete_btn=(Button)findViewById(R.id.delete_button) ;
        u_name=(TextView)findViewById(R.id.textView7);
        u_email=(TextView)findViewById(R.id.textView8);
        u_phone=(TextView)findViewById(R.id.textView10);
        u_address=(TextView)findViewById(R.id.textView11);
        logout_btn=(Button)findViewById(R.id.logout_button);

        userInfoDisplay(u_name,u_email,u_phone,u_address);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(ViewMyProfile.this,EditProfile.class);
                startActivity(intent);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteUser(u_phone);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ViewMyProfile.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void deleteUser(final TextView u_phone) {

        final DatabaseReference userListRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhno());


        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("This action is irreversible..");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userListRef.removeValue();

                Toast.makeText(ViewMyProfile.this, "Your account has been deactivated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewMyProfile.this, MainActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog ad = builder.create();
        ad.show();
    }


    private void userInfoDisplay(final TextView u_name, final TextView u_email,
                                 final TextView u_phone, final TextView u_address) {
        DatabaseReference usersref= FirebaseDatabase.getInstance().getReference().child("Users")
                .child(Prevalent.currentOnlineUser.getPhno());
        usersref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {

                    String username = snapshot.child("username").getValue().toString();
                    String email = snapshot.child("email").getValue().toString();
                    String phno = snapshot.child("phno").getValue().toString();
                    String address=snapshot.child("address").getValue().toString();

                    u_name.setText(username);
                    u_email.setText(email);
                    u_phone.setText(phno);
                    u_address.setText(address);

                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}