package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class createAccount extends AppCompatActivity {
    private Button AdminRegisterButton;
    private EditText txtAdName, txtAdEmail, txtAdPhone, txtAdAddress, txtAdPassword, txtAdConfirmPassword;
    private ProgressBar LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        AdminRegisterButton =(Button)findViewById(R.id.Admin_Register_Button);

        AdminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(createAccount.this,adminLogin.class);
                startActivity(intent);
            }
        });


        AdminRegisterButton  =(Button)findViewById(R.id.Admin_Register_Button);

        txtAdName           =(EditText)findViewById(R.id.create_account_username);
        txtAdEmail          =(EditText)findViewById(R.id.create_account_email_input);
        txtAdPhone          =(EditText)findViewById(R.id.create_account_phonenumber_input);
        txtAdAddress        =(EditText)findViewById(R.id.create_account_address_input) ;
        txtAdPassword       =(EditText)findViewById(R.id.create_account_password);
        txtAdConfirmPassword       =(EditText)findViewById(R.id.create_account_confirmpassword);

        AdminRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateAdminAccount();
            }
        });
    }

    private void CreateAdminAccount() {

        String Name        = txtAdName.getText().toString();
        String Email        = txtAdEmail.getText().toString();
        String Phone        = txtAdPhone.getText().toString();
        String Address      = txtAdAddress.getText().toString();
        String Password     = txtAdPassword.getText().toString();
        String ConfirmPassword     = txtAdConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(Name)) {
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(txtAdEmail.getText().toString()).matches()){
            txtAdEmail.setError("Please enter a valid email");
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Phone)){
            Toast.makeText(this, "Please Enter your Phone number", Toast.LENGTH_SHORT).show();
        }
        else if(!txtAdPhone.getText().toString().matches("[0-9]{10}")){
            txtAdPhone.setError("Enter only 10 digit mobile number");
        }
        else if(TextUtils.isEmpty(Address)){
            Toast.makeText(this, "Please Enter your Address", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Password)){
            Toast.makeText(this, "Please Enter your Password", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(ConfirmPassword)){
            Toast.makeText(this, "Enter  Password Again", Toast.LENGTH_SHORT).show();
        }
        else if(!Password.equals(ConfirmPassword)){
            Toast.makeText(this,"password should be equal",Toast.LENGTH_SHORT).show();
        }
        else{
            ValidatePhone(Name, Email, Phone, Address, Password);
        }
    }

    private void ValidatePhone(final String Name, final String Email, final String Phone, final String Address, final String Password) {
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!( snapshot.child("Admin").child(Phone).exists() )){
                    HashMap<String,Object> admindatamap = new HashMap<>();
                    admindatamap.put("Name",Name);
                    admindatamap.put("Email",Email);
                    admindatamap.put("Phone",Phone);
                    admindatamap.put("Address", Address);
                    admindatamap.put("Password",Password);
                    RootRef.child("Admin").child(Phone).updateChildren(admindatamap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(createAccount.this,"Account Successful ",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(createAccount.this,adminLogin.class);
                                        startActivity(intent);
                                    }
                                    else{
                                        Toast.makeText(createAccount.this,"Network error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(createAccount.this,"This "+Phone+ "number already exist",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(createAccount.this,MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}