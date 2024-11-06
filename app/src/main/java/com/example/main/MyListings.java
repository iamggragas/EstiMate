package com.example.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyListings extends AppCompatActivity {

    private ConstraintLayout layout;
    private AnimationDrawable animationDrawable;
    String name, phone, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);

        layout = findViewById(R.id.main);
        animationDrawable = layout.getBackground() != null ? (AnimationDrawable) layout.getBackground() : null;        assert animationDrawable != null;
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        ImageButton backBtn = findViewById(R.id.backBtn);
        FloatingActionButton fab = findViewById(R.id.fab);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyListings.this, Profile.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyListings.this, AddListings.class);

                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);

                startActivity(intent);
            }
        });
    }
}