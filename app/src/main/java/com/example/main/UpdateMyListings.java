package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.main.Utilities.House;
import com.example.main.Utilities.HousePrediction;
import com.example.main.Utilities.Listings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateMyListings extends AppCompatActivity {

    private double vHouseSize;
    private int vBedroom;
    private int vQuality;
    private double vAge;
    private double price;
    private String houseSize, houseName, houseAddress, bedroom, quality, age;
    private String name, phone, email, password;

    String ownerName;

    private ImageView uploadImg;
    private EditText edtHouseName, edtHouseAddress, edtArea, edtNumBedroom, edtQuality, edtAge;
    private TextView edtPrice;
    private Button predictPriceBtn, predictedPrice, saveListingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_my_listings);

        edtHouseName = findViewById(R.id.edtHouseName);
        edtHouseAddress = findViewById(R.id.edtHouseAddress);
        edtArea = findViewById(R.id.edtArea);
        edtNumBedroom = findViewById(R.id.edtNumBedroom);
        edtQuality = findViewById(R.id.edtQuality);
        edtAge = findViewById(R.id.edtAge);
        edtPrice = findViewById(R.id.edtPrice);

        predictPriceBtn = findViewById(R.id.predictPriceBtn);
        predictedPrice = findViewById(R.id.predictedPrice);
        saveListingBtn = findViewById(R.id.saveListingBtn);

        ImageButton backBtn = findViewById(R.id.backBtn);

        uploadImg = findViewById(R.id.uploadImg);

        ownerName = getIntent().getStringExtra("name");

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");
        houseName = intent.getStringExtra("houseName");

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Listings").child(name).child(houseName);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    houseAddress = dataSnapshot.child("houseAddress").getValue(String.class);
                    age = dataSnapshot.child("age").getValue(Integer.class).toString();
                    bedroom = dataSnapshot.child("bedrooms").getValue(Integer.class).toString();
                    price = Double.parseDouble(dataSnapshot.child("price").getValue(Integer.class).toString());
                    quality = dataSnapshot.child("quality").getValue(Integer.class).toString();
                    houseSize = dataSnapshot.child("size").getValue(Integer.class).toString();

                    edtHouseName.setText(houseName);
                    edtHouseAddress.setText(houseAddress);
                    edtArea.setText(houseSize);
                    edtNumBedroom.setText(bedroom);
                    edtQuality.setText(quality);
                    edtAge.setText(age);
                    edtPrice.setText("Old Price: Php " + price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        saveListingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("houseAddress").setValue(houseAddress);
                reference.child("size").setValue(vHouseSize);
                reference.child("bedrooms").setValue(vBedroom);
                reference.child("quality").setValue(vQuality);
                reference.child("age").setValue(vAge);
                reference.child("price").setValue(price);

                Toast.makeText(UpdateMyListings.this, "Listings updated", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(UpdateMyListings.this, MyListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateMyListings.this, Profile.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });
    }

    public void predictPrice(View view) {
        houseSize = edtArea.getText().toString();
        houseName = edtHouseName.getText().toString();
        houseAddress = edtHouseAddress.getText().toString();
        bedroom = edtNumBedroom.getText().toString();
        quality = edtQuality.getText().toString();
        age = edtAge.getText().toString();

        if (houseName.isEmpty() || houseSize.isEmpty() || houseAddress.isEmpty() || bedroom.isEmpty() || quality.isEmpty() || age.isEmpty()) {
            checkFields(houseName, houseSize, houseAddress, bedroom, quality, age);
        } else {
            try {
                vHouseSize = Double.parseDouble(houseSize);
                vBedroom = Integer.parseInt(bedroom);
                vQuality = Integer.parseInt(quality);
                vAge = Double.parseDouble(age);

                HousePrediction housePrediction = new HousePrediction();

                housePrediction.addHouse(new House(150, 3, 7, 10, 300000));
                housePrediction.addHouse(new House(200, 4, 8, 5, 450000));
                housePrediction.addHouse(new House(250, 2, 9, 2, 600000));
                housePrediction.addHouse(new House(200, 3, 7, 4, 350000));
                housePrediction.addHouse(new House(175, 2, 6, 8, 150000));
                housePrediction.addHouse(new House(150, 3, 5, 10, 500000));
                housePrediction.addHouse(new House(205, 2, 9, 1, 250000));
                housePrediction.addHouse(new House(215, 2, 7, 3, 550000));
                housePrediction.addHouse(new House(185, 3, 9, 6, 400000));
                housePrediction.addHouse(new House(225, 1, 6, 4, 750000));

                price = housePrediction.predictPrice(vHouseSize, vBedroom, vQuality, vAge);
                price = Math.round(price * 100.0) / 100.0;

                predictedPrice.setVisibility(View.VISIBLE);
                predictedPrice.setText("Php " + String.format("%.2f", price));
                saveListingBtn.setVisibility(View.VISIBLE);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Please enter a valid number", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkFields(String houseName, String houseSize, String houseAddress, String bedroom, String quality, String age) {
        if (houseName.isEmpty()) {
            edtHouseName.setError("Please enter a house name");
            edtHouseName.requestFocus();
        }

        if (houseSize.isEmpty()) {
            edtArea.setError("Please enter a house address");
            edtArea.requestFocus();
        }

        if (houseAddress.isEmpty()) {
            edtHouseAddress.setError("Please enter a house address");
            edtHouseAddress.requestFocus();
        }

        if (bedroom.isEmpty()) {
            edtNumBedroom.setError("Please enter a number of bedrooms");
            edtNumBedroom.requestFocus();
        }

        if (quality.isEmpty()) {
            edtQuality.setError("Please enter a house quality");
            edtQuality.requestFocus();
        }

        if (age.isEmpty()) {
            edtAge.setError("Please enter a house age");
            edtAge.requestFocus();
        }
    }
}