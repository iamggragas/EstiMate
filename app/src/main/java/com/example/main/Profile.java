package com.example.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Profile extends AppCompatActivity {

    private EditText editName, editPhone, editEmail, editPassword;
    // private ConstraintLayout layout;
    // private AnimationDrawable animationDrawable;
    private ImageView profilePic;
    private String name, phone, email, password;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

//        layout = findViewById(R.id.main);
//        animationDrawable = layout.getBackground() != null ? (AnimationDrawable) layout.getBackground() : null;
//        assert animationDrawable != null;
//        animationDrawable.setEnterFadeDuration(2500);
//        animationDrawable.setExitFadeDuration(5000);
//        animationDrawable.start();

        Button updateBtn = findViewById(R.id.updateBtn);
        ImageView profilePic = findViewById(R.id.profilePic);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        showData();
        profilePic.setImageResource(R.drawable.default_profile);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPhoneChanged() || isEmailChanged() || isPasswordChanged()) {
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showData() {
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        editName.setText(name);
        editPhone.setText(phone);
        editEmail.setText(email);
        editPassword.setText(password);
    }

    public boolean isPhoneChanged() {
       if (!phone.equals(editPhone.getText().toString())){
           reference.child(name).child("phone").setValue(editPhone.getText().toString());
           phone = editPhone.getText().toString();
           return true;
       } else {
           return false;
       }
    }

    public boolean isEmailChanged() {
        if (!email.equals(editEmail.getText().toString())){
            reference.child(name).child("email").setValue(editEmail.getText().toString());
            email = editEmail.getText().toString();
            return true;
        } else {
            return false;
        }
    }

    public boolean isPasswordChanged() {
        if (!password.equals(editPassword.getText().toString())) {
            reference.child(name).child("password").setValue(editPassword.getText().toString());
            password = editPassword.getText().toString();
            return true;
        } else {
            return false;
        }
    }
}