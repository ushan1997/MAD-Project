package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ecommerceapp.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private Button CreateRegisterButton;
    private EditText InputEmail,InputUsername,InputPhoneNum,InputPassword,InputConfirmpw;
    private ProgressBar LoadingBar;


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_register);

                CreateRegisterButton =(Button)findViewById(R.id.register_button);
                CreateRegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                });

                CreateRegisterButton  =(Button)findViewById(R.id.register_button);
                InputEmail           =(EditText)findViewById(R.id.register_Email_input);
                InputUsername        =(EditText)findViewById(R.id.register_username_input);
                InputPhoneNum        =(EditText)findViewById(R.id.register_phone_number_input);
                InputPassword        =(EditText)findViewById(R.id.register_password_input) ;
                InputConfirmpw       =(EditText)findViewById(R.id.register_confirm_password_input);




                CreateRegisterButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CreateAccount(); //calling create account function in onCreate function
                    }
                });
            }


            private void CreateAccount() {

                    String email    = InputEmail.getText().toString();
                    String username = InputUsername.getText().toString();
                    String phno     = InputPhoneNum.getText().toString();
                    String password = InputPassword.getText().toString();
                    String confirmPw = InputConfirmpw.getText().toString();



                    if(TextUtils.isEmpty(email)){
                        Toast.makeText(this, "Please Enter your Email", Toast.LENGTH_SHORT).show();

                    }
                    else if(TextUtils.isEmpty(username)){
                        Toast.makeText(this, "Please Enter your Username", Toast.LENGTH_SHORT).show();

                    }
                    else if(TextUtils.isEmpty(phno)){
                        Toast.makeText(this, "Please Enter your PhoneNumber", Toast.LENGTH_SHORT).show();

                    }
                    else if(TextUtils.isEmpty(password)){
                        Toast.makeText(this, "Please Enter your PassWord", Toast.LENGTH_SHORT).show();

                    }
                    else if(TextUtils.isEmpty(confirmPw)){
                        Toast.makeText(this, "Enter  PassWord Again", Toast.LENGTH_SHORT).show();

                    }else if(!password.equals(confirmPw)){

                        Toast.makeText(this,"password should be equal",Toast.LENGTH_SHORT).show();

                    } else{

                       ValidatePhoneNumber(email,username,phno,password);//calling database connection if all details are ready


                    }

            }

            private void ValidatePhoneNumber(final String email, final String username, final String phno, final String password) {

                 final DatabaseReference RootRef;//getting the database connection
                 RootRef = FirebaseDatabase.getInstance().getReference();

                RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if(!( snapshot.child("Users").child(phno).exists() )){

                            HashMap<String,Object> userdatamap=new HashMap<>();
                            userdatamap.put("phno",phno);
                            userdatamap.put("email",email);
                            userdatamap.put("username",username);
                            userdatamap.put("password",password);


                            RootRef.child("Users").child(phno).updateChildren(userdatamap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this,"Account successful ",Toast.LENGTH_SHORT).show();

                                                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                                                startActivity(intent);

                                            }else{
                                                Toast.makeText(RegisterActivity.this,"Network error",Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });
                        }
                        else{
                            Toast.makeText(RegisterActivity.this,"This "+phno+ "already exist",Toast.LENGTH_SHORT).show();

                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }


}