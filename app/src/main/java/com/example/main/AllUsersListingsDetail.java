package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllUsersListingsDetail extends AppCompatActivity {

    private TextView houseName, houseAddress, price, area, bedroom, quality, age, ownerName, phoneNumber, emailAddress;
    private String name, phone, email, password;
    private String nameToPass, phoneToPass, emailToPass, passwordToPass;
    private ImageButton backBtn;

    private FloatingActionButton fab;

    String key = "";

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_listings_details);

        backBtn = findViewById(R.id.backBtn);

        houseName = findViewById(R.id.houseName);
        houseAddress = findViewById(R.id.houseAddress);
        price = findViewById(R.id.price);
        area = findViewById(R.id.area);
        bedroom = findViewById(R.id.bedroom);
        quality = findViewById(R.id.quality);
        age = findViewById(R.id.age);

        ownerName = findViewById(R.id.owner);
        phoneNumber = findViewById(R.id.phone);
        emailAddress = findViewById(R.id.email);

        fab = findViewById(R.id.fab_add);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                    if (userSnapshot.child("email").getValue(String.class).equals(user.getEmail())) {
                        nameToPass = userSnapshot.child("name").getValue(String.class);
                        phoneToPass = userSnapshot.child("phone").getValue(String.class);
                        emailToPass = userSnapshot.child("email").getValue(String.class);
                        passwordToPass = userSnapshot.child("password").getValue(String.class);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            houseName.setText(extras.getString("houseName"));
            houseAddress.setText(extras.getString("houseAddress"));
            price.setText("Php " + extras.getString("price") + "0");
            area.setText(extras.getString("area") + " sq, m.");
            bedroom.setText(extras.getString("bedroom") + " bedroom");
            quality.setText(extras.getString("quality") + "/10 rating");
            age.setText(extras.getString("age") + " years");

            name = extras.getString("name");
            phone = extras.getString("phone");
            email = extras.getString("email");
            password = extras.getString("password");

            ownerName.setText(name);
            phoneNumber.setText(phone);
            emailAddress.setText(email);

            key = extras.getString("key");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllUsersListingsDetail.this, AllUsersListings.class);

                intent.putExtra("name", nameToPass);
                intent.putExtra("phone", phoneToPass);
                intent.putExtra("email", emailToPass);
                intent.putExtra("password", passwordToPass);

                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllUsersListingsDetail.this, AddListings.class);

                intent.putExtra("name", nameToPass);
                intent.putExtra("phone", phoneToPass);
                intent.putExtra("email", emailToPass);
                intent.putExtra("password", passwordToPass);

                startActivity(intent);
            }
        });
    }
}