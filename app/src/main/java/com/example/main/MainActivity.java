package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editName, editPhone, editEmail, editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        TextView login = findViewById(R.id.logIn);
        TextView terms = findViewById(R.id.terms);

        Button btnSignUp = findViewById(R.id.btnSignUp);

        auth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String password = editPassword.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    checkFields(name, phone, email, password);
                } else {
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "User Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(MainActivity.this, Login.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "User Sign Up Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TermsAndCondition.class);
                startActivity(intent);
            }
        });
    }

    private void checkFields(String name, String phone, String email, String password) {
        if (name.isEmpty()) {
            editName.setError("Please enter a name");
            editName.requestFocus();
        }

        if (phone.isEmpty()) {
            editPhone.setError("Please enter a phone number");
            editPhone.requestFocus();
        }

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