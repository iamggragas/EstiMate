package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyListingsDetail extends AppCompatActivity {

    private TextView houseName, houseAddress, price, area, bedroom, quality, age;
    private String name, phone, email, password;
    ImageButton backBtn;

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
        }

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
}