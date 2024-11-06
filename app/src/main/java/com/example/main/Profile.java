package com.example.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private EditText editName, editPhone, editEmail, editPassword;
    private ImageView profilePic;
    private String name, phone, email, password;
    private DatabaseReference reference;
    private Button seePostsBtn, seeListingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        Button updateBtn = findViewById(R.id.updateBtn);
        seeListingsBtn = findViewById(R.id.seeListingsBtn);
        seePostsBtn = findViewById(R.id.seePostsBtn);
        ImageButton addListingsBtn = findViewById(R.id.addListingsBtn);
        ImageView profilePic = findViewById(R.id.profilePic);

        reference = FirebaseDatabase.getInstance().getReference("Users");

        showData();
        profilePic.setImageResource(R.drawable.default_profile);

        // Update Profile
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPhoneChanged() || isEmailChanged() || isPasswordChanged()) {
                    Toast.makeText(getApplicationContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Will go to add listings activity
        addListingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, AddListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });

        // Will go to see my listings
        seeListingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, MyListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
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

        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Listings").child(name);

        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = (int) snapshot.getChildrenCount();

                seePostsBtn.setText("View " + count +" Posts");
                seeListingsBtn.setText("View " + count + " Listings");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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