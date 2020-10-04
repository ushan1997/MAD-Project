package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Model.Admin;
import com.example.ecommerceapp.Model.Users;
import com.example.ecommerceapp.Prevalent.AdminPrevalent;
import com.example.ecommerceapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;


public class adminLogin extends AppCompatActivity {

    private Button signup,login;
    private EditText AdminLoginInputPhone, AdminLoginInputPassword;
    private TextView AdminForgetPassword;
    private String parentDbName = "Admin";
    private ProgressBar loadingBar;
    private CheckBox AdminRememberMeCheckBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        signup                  = (Button)findViewById(R.id.signup_btn);
        login                   = (Button)findViewById(R.id.login_btn);
        AdminLoginInputPhone    =  (EditText) findViewById(R.id.phone_input);

        AdminLoginInputPassword = (EditText) findViewById(R.id.password_input);
        // AdminForgetPassword     = (TextView) findViewById(R.id.AdminForgetPassword);

        AdminRememberMeCheckBox = (CheckBox) findViewById(R.id.AdminRememberMe);
        Paper.init(this);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(adminLogin.this,createAccount.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginAdmin();
                // Intent intent = new Intent(adminLogin.this,dashboard.class);
                //startActivity(intent);

            }
        });
    }

    private void LoginAdmin() {
        String Phone = AdminLoginInputPhone.getText().toString();
        String Password = AdminLoginInputPassword.getText().toString();

        if (TextUtils.isEmpty(Phone)) {
            Toast.makeText(this, "Please enter Your Phone Number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Password)) {
            Toast.makeText(this, "Please enter your Password...", Toast.LENGTH_SHORT).show();
        }
        else {
            AllowAccessToAdminAccount(Phone, Password);
        }
    }

    private void AllowAccessToAdminAccount(final String Phone, final String Passassword) {

        if(AdminRememberMeCheckBox.isChecked()) {
            Paper.book().write(AdminPrevalent.AdminPhoneKey, Phone);
            Paper.book().write(AdminPrevalent.AdminPasswordKey, Passassword);
        }
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentDbName).child(Phone).exists()) {
                    Admin usersData = dataSnapshot.child(parentDbName).child(Phone).getValue(Admin.class);
                    if (usersData.getPhone().equals(Phone)) {
                        if (usersData.getPassword().equals(Passassword)) {
                            if (parentDbName.equals("Admin")) {
                                Toast.makeText(adminLogin.this, "Logged in Successfully...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(adminLogin.this, dashboard.class);
                                AdminPrevalent.currentOnlineAdmin = usersData;
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(adminLogin.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                else {
                    Toast.makeText(adminLogin.this, "Account with this " + Phone + "  do not exists.", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}