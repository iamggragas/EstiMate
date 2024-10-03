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

public class Login extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        TextView signUp = findViewById(R.id.signUp);
        TextView terms = findViewById(R.id.terms);

        Button btnLogIn = findViewById(R.id.btnLogIn);

        auth = FirebaseAuth.getInstance();

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    checkFields(email, password);
                } else {
                    if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(getApplicationContext(), "User Log In Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(Login.this, TermsAndCondition.class));
                                    }
                                }).addOnFailureListener(new OnFailureListener()  {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "User Log In Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        editEmail.setError("Please enter a valid email");
                        editEmail.requestFocus();
                    }
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

    private void checkFields(String email, String password) {
        if (email.isEmpty()) {
            editEmail.setError("Please enter an email");
            editEmail.requestFocus();
        }

        if (password.isEmpty()) {
            editPassword.setError("Please enter a password");
            editPassword.requestFocus();
        }
    }
}