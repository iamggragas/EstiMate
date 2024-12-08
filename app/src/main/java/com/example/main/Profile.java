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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private FirebaseAuth auth;
    private FirebaseUser user;
    private Button seePostsBtn, seeListingsBtn, reqPasswordResetBtn, updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);

        updateBtn = findViewById(R.id.updateBtn);
        seeListingsBtn = findViewById(R.id.seeListingsBtn);
        seePostsBtn = findViewById(R.id.seePostsBtn);
        reqPasswordResetBtn = findViewById(R.id.reqPasswordResetBtn);

        ImageButton addListingsBtn = findViewById(R.id.addListingsBtn);
        ImageView profilePic = findViewById(R.id.profilePic);
        ImageView backBtn = findViewById(R.id.backBtn);

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");

        showData();
        profilePic.setImageResource(R.drawable.default_profile);

        // Update Profile
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPhoneChanged() || isEmailChanged()) {
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

                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, AllUsersListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);

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

                startActivity(intent);
            }
        });

        // reset password
        reqPasswordResetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    public void showData() {
        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");

        editName.setText(name);
        editPhone.setText(phone);
        editEmail.setText(email);

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

//        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
//
//        user.reauthenticate(credential).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                user.updateEmail(email).addOnCompleteListener(emailUpdateTask -> {});
//            }
//        });
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

    public void resetPassword() {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Profile.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}