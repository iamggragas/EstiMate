package com.example.main;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.main.Utilities.Listings;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyListings extends AppCompatActivity {

    private ConstraintLayout layout;
    private AnimationDrawable animationDrawable;
    String name, phone, email, password;
    RecyclerView recyclerView;
    List<Listings> myListings;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    SearchView searchView;
    MyListingsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_listings);

        Intent intent = getIntent();

        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("password");

        layout = findViewById(R.id.main);
        animationDrawable = layout.getBackground() != null ? (AnimationDrawable) layout.getBackground() : null;
        assert animationDrawable != null;
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        ImageButton backBtn = findViewById(R.id.backBtn);
        FloatingActionButton fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        myListings = new ArrayList<>();
        adapter = new MyListingsAdapter(this, myListings, name, phone, email, password);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Listings").child(name);
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                myListings.clear();
                for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                    Listings listings = itemSnapshot.getValue(Listings.class);
                    myListings.add(listings);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

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

    public void searchList(String text) {
        ArrayList<Listings> searchList = new ArrayList<>();

        for (Listings listing: myListings) {
            if (listing.getHouseName().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(listing);
            }
        }

        adapter.searchListings(searchList);
    }
}