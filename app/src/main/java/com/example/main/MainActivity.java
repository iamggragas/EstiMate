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

import com.example.main.Utilities.PasswordHash;
import com.example.main.Utilities.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editPhone, editEmail, editPassword, editConfirmPassword;
    private String name, phone, email, password, newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editName);
        editPhone = findViewById(R.id.editPhone);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        editConfirmPassword = findViewById(R.id.editConfirmPassword);

        TextView login = findViewById(R.id.logIn);
        TextView terms = findViewById(R.id.terms);

        Button btnSignUp = findViewById(R.id.btnSignUp);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editName.getText().toString().trim();
                phone = editPhone.getText().toString().trim();
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();
                confirmPassword = editConfirmPassword.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    checkFields(name, phone, email, password, confirmPassword);
                } else {
                    if (!password.equals(confirmPassword)) {
                        editConfirmPassword.setError("Passwords do not match");
                        editConfirmPassword.requestFocus();
                    } else {
                        // hashing password
                        newPassword = new PasswordHash().hashPasswordSHA256(password);

                        // Saving to firebase authentication
                        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                        // Saving to realtime database
                        User user = new User(name, phone, email, newPassword);
                        reference.child(name).setValue(user);

                        Intent intent = new Intent(MainActivity.this, Login.class);
                        startActivity(intent);
                    }
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

    private void checkFields(String name, String phone, String email, String password, String confirmPassword) {
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

        if (confirmPassword.isEmpty()) {
            editConfirmPassword.setError("Please confirm your password");
            editConfirmPassword.requestFocus();
        }

    }
}