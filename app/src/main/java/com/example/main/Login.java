package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editName, editPassword;
    private String name, password, passwordFromDB, nameFromDB, phoneFromDB, emailFromDB;
    private DatabaseReference reference;
    Query checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editName = findViewById(R.id.editName);
        editPassword = findViewById(R.id.editPassword);

        TextView signUp = findViewById(R.id.signUp);
        TextView terms = findViewById(R.id.terms);

        Button btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editName.getText().toString().trim();
                password = editPassword.getText().toString().trim();

                reference = FirebaseDatabase.getInstance().getReference("Users");
                checkUser = reference.orderByChild("name").equalTo(name);

                if (name.isEmpty() || password.isEmpty()) {
                    checkFields(name, password);
                } else {
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                editName.setError(null);
                                passwordFromDB = snapshot.child(name).child("password").getValue(String.class);

                                if (passwordFromDB.equals(password)) {
                                    editPassword.setError(null);

                                    // Pass data through intent
                                    nameFromDB = snapshot.child(name).child("name").getValue(String.class);
                                    phoneFromDB = snapshot.child(name).child("phone").getValue(String.class);
                                    emailFromDB = snapshot.child(name).child("email").getValue(String.class);

                                    Intent intent = new Intent(Login.this, Profile.class);

                                    intent.putExtra("name", nameFromDB);
                                    intent.putExtra("phone", phoneFromDB);
                                    intent.putExtra("email", emailFromDB);
                                    intent.putExtra("password", passwordFromDB);

                                    startActivity(intent);
                                } else {
                                    editPassword.setError("Invalid Credentials");
                                    editPassword.requestFocus();
                                }
                            } else {
                                editName.setError("User does not exist");
                                editName.requestFocus();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, MainActivity.class);
                startActivity(intent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, TermsAndCondition.class);
                startActivity(intent);
            }
        });
    }

    private void checkFields(String name, String password) {
        if (name.isEmpty()) {
            editName.setError("Please enter an email");
            editName.requestFocus();
        }

        if (password.isEmpty()) {
            editPassword.setError("Please enter a password");
            editPassword.requestFocus();
        }
    }
}