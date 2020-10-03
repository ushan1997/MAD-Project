package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {

    private Button verify_btn;
    private EditText phonenumber_u, q1, q2;
    private TextView pageTitle, titleQestions;

    private String check = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        verify_btn = (Button) findViewById(R.id.verify_button);
        phonenumber_u = (EditText) findViewById(R.id.phone_num);
        q1=(EditText)findViewById(R.id.question1);
        q2=(EditText)findViewById(R.id.qestion2);
        pageTitle=(TextView)findViewById(R.id.textView2);
        titleQestions=(TextView)findViewById(R.id.qa);

        check= getIntent().getStringExtra("check");


    }

    @Override
    protected void onStart() {
        super.onStart();

        phonenumber_u.setVisibility(View.GONE);

        if(check.equals("editprofile")){

            pageTitle.setText("Set Questions");
            titleQestions.setText("Plase set answer to this security questions");
            verify_btn.setText("Set");

            displayPreviousAnswers();

            verify_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    setAnswer();


                }
            });

        }else if(check.equals("login")){
            phonenumber_u.setVisibility(View.VISIBLE);

            verify_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    verifyUser();
                }
            });

        }




    }

    private void setAnswer() {
        String answer1= q1.getText().toString().toLowerCase();
        String answer2= q2.getText().toString().toLowerCase();

        if(q1.equals("") && q2.equals("")){
            Toast.makeText(ResetPasswordActivity.this, "Please answer both questions", Toast.LENGTH_SHORT).show();
        }
        else{
            DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhno());

            HashMap<String,Object> userdataMap=new HashMap<>();
            userdataMap.put("answer1",answer1);
            userdataMap.put("answer2",answer2);

            ref.child("Security Quastions").updateChildren(userdataMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPasswordActivity.this, "You have anser the security quastions successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(ResetPasswordActivity.this, HomeActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    private void displayPreviousAnswers(){
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhno());

        ref.child("Security Quastions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String ans1= snapshot.child("answer1").getValue().toString();
                    String ans2= snapshot.child("answer2").getValue().toString();

                    q1.setText(ans1);
                    q2.setText(ans2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void verifyUser(){
        final String phno=phonenumber_u.getText().toString();
        final String answer1= q1.getText().toString().toLowerCase();
        final String answer2= q2.getText().toString().toLowerCase();

        if(!phno.equals("") && !answer1.equals("") && !answer2.equals("")){
            final DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users").child(phno);

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.exists()){
                        String mPhone = snapshot.child("phno").getValue().toString();


                        if(snapshot.hasChild("Security Quastions")){
                            String ans1= snapshot.child("Security Quastions").child("answer1").getValue().toString();
                            String ans2= snapshot.child("Security Quastions").child("answer2").getValue().toString();

                            if(!ans1.equals(answer1)){
                                Toast.makeText(ResetPasswordActivity.this, "Your 1st answer is wrong.", Toast.LENGTH_SHORT).show();
                            }
                            else if(!ans2.equals(answer2)){
                                Toast.makeText(ResetPasswordActivity.this, "Your 2nd answer is wrong.", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setTitle("New Password");

                                final EditText newPassword = new EditText(ResetPasswordActivity.this);
                                newPassword.setHint("Write a new password here");
                                builder.setView(newPassword);

                                builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if(!newPassword.getText().toString().equals("")){
                                            ref.child("password").setValue(newPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(ResetPasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                                                        startActivity(intent);
                                                    }
                                                }
                                            });

                                        }
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });

                                builder.show();
                            }

                        }
                        else {
                            Toast.makeText(ResetPasswordActivity.this, "You have not set the security questions", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(ResetPasswordActivity.this, "This phone number not exist", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else{
            Toast.makeText(this, "Please complete the form", Toast.LENGTH_SHORT).show();
        }


    }

}