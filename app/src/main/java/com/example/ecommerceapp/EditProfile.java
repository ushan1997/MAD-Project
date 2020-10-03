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
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.Prevalent.Prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    private Button save_btn,security_btn;
    private CircleImageView profileImageView;
    private EditText user_name,email_u,phone_u,address_u;
    private TextView imageChange;

    private Uri imageUri;
    private String myUri="";
   // private StorageTask uploadTask;
    //private StorageReference strorageProfilePictureRef;
    private String checker="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        save_btn =(Button)findViewById(R.id.save_button);
        security_btn=(Button)findViewById(R.id.security_question_button);

        profileImageView=(CircleImageView)findViewById(R.id.settings_profile_image) ;
        user_name=(EditText)findViewById(R.id.user);
        email_u=(EditText)findViewById(R.id.email);
        phone_u=(EditText)findViewById(R.id.phone);
        imageChange=(TextView)findViewById(R.id.name1) ;
        address_u=(EditText)findViewById(R.id.address);

        userInfoDisplay(profileImageView,user_name,email_u,phone_u,address_u);

        security_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EditProfile.this, ResetPasswordActivity.class);
                intent.putExtra("check","editprofile");
                startActivity(intent);
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checker.equals("clicked")){
                    userInfoSaved();
                }
                else{
                    updateOnlyUserInfo();
                }
            }
        });


       /* save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(EditProfile.this,ViewMyProfile.class);
                startActivity(intent);
            }
        });*/
    }

    private void updateOnlyUserInfo() {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");

        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put("username",user_name.getText().toString());
        userMap.put("email",email_u.getText().toString());
        userMap.put("phno",phone_u.getText().toString());
        userMap.put("address",address_u.getText().toString());

        ref.child(Prevalent.currentOnlineUser.getPhno()).updateChildren(userMap);

        startActivity(new Intent(EditProfile.this,ViewMyProfile.class));
        Toast.makeText(EditProfile.this, "profile Info updated successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && requestCode==RESULT_OK  && data!=null)
        {
            CropImage.ActivityResult result= CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profileImageView.setImageURI(imageUri);
        }
        else{
            Toast.makeText(this, "Error, Try again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(EditProfile.this, EditProfile.class));
            finish();
        }
    }*/

    private void userInfoSaved() {
        if(TextUtils.isEmpty(user_name.getText().toString())){
            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(email_u.getText().toString())){
            Toast.makeText(this, "Email is mandatory", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(phone_u.getText().toString())){
            Toast.makeText(this, "Phone is mandatory", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(address_u.getText().toString())){
            Toast.makeText(this, "address is mandatory", Toast.LENGTH_SHORT).show();
        }



        //else if(checker.equals("clicked")){
        // uploadImage();
        //}

    }



    /*private void uploadImage() {
        //final ProgressBar progressBar= new ProgressBar(this);
        //progressBar.show

        if(imageUri!=null){
            final StorageReference fileref= strorageProfilePictureRef.child(Prevalent.currentOnlineUser.getPhno() + ".jpg");
            uploadTask=fileref.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if(!task.isSuccessful()){
                        throw  task.getException();
                    }
                    return fileref.getDownloadUrl();
                }
            })
                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){
                                Uri downloadUrl = task.getResult();
                                myUri = downloadUrl.toString();

                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference().child("Users");

                                HashMap<String, Object> userMap = new HashMap<>();
                                userMap.put("username",user_name.getText().toString());
                                userMap.put("email",email_u.getText().toString());
                                userMap.put("phno",phone_u.getText().toString());
                                userMap.put("image",myUri);
                                ref.child(Prevalent.currentOnlineUser.getPhno()).updateChildren(userMap);

                                startActivity(new Intent(EditProfile.this,MainActivity.class));
                                Toast.makeText(EditProfile.this, "profile Info updated successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else {
                                Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else{
            Toast.makeText(this, "Image is not selected", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void userInfoDisplay(final CircleImageView profileImageView, final EditText user_name, final EditText email_u, final EditText phone_u, final EditText address_u)
    {
        DatabaseReference userref= FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhno());
        userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    //if(snapshot.child("image").exists()){
                    // String image= snapshot.child("image").getValue().toString();
                    String username= snapshot.child("username").getValue().toString();
                    String email= snapshot.child("email").getValue().toString();
                    String phno= snapshot.child("phno").getValue().toString();
                    String address=snapshot.child("address").getValue().toString();
                    //String password= snapshot.child("password").getValue().toString();

                    //Picasso.get().load(image).into(profileImageView);
                    user_name.setText(username);
                    email_u.setText(email);
                    phone_u.setText(phno);
                    address_u.setText(address);

                    //}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}