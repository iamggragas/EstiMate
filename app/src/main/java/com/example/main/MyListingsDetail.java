package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyListingsDetail extends AppCompatActivity {

    private TextView houseName, houseAddress, price, area, bedroom, quality, age, ownerName, phoneNumber, emailAddress;
    private String name, phone, email, password;
    ImageButton backBtn;

    private FloatingActionButton fabMain, fabEdit, fabDelete;
    private RelativeLayout fabOptions;
    private boolean isFabOpen = false;

    String key = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lsitings_detail);

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

        fabMain = findViewById(R.id.fab_main);
        fabEdit = findViewById(R.id.fab_edit);
        fabDelete = findViewById(R.id.fab_delete);
        fabOptions = findViewById(R.id.fab_options);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            houseName.setText(extras.getString("houseName"));
            houseAddress.setText(extras.getString("houseAddress"));
            price.setText("Php " + extras.getString("price"));
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

        fabMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFabMenu();
            }
        });

        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyListingsDetail.this, UpdateMyListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                intent.putExtra("houseName", houseName.getText().toString());

                startActivity(intent);

                toggleFabMenu();
            }
        });

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Listings").child(name);
                reference.child(key).removeValue();

                Toast.makeText(MyListingsDetail.this, "Listing Deleted", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MyListingsDetail.this, MyListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);

                toggleFabMenu();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyListingsDetail.this, MyListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });
    }

    private void toggleFabMenu() {
        if (isFabOpen) {
            fabOptions.setVisibility(View.GONE); // Hide options
            isFabOpen = false;
        } else {
            fabOptions.setVisibility(View.VISIBLE); // Show options
            isFabOpen = true;
        }
    }
}