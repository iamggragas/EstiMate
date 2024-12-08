package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.main.Utilities.PasswordHash;
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
    private EditText editName, editPassword, editEmail;
    private String name, email, password, newPassword, emailFromDB, nameFromDB, phoneFromDB;
    private DatabaseReference reference;
    Query checkUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        TextView signUp = findViewById(R.id.signUp);
        TextView terms = findViewById(R.id.terms);
        TextView requestPasswordReset = findViewById(R.id.requestPasswordReset);

        Button btnLogIn = findViewById(R.id.btnLogIn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editName.getText().toString().trim();
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();

                // hashing password
                newPassword = new PasswordHash().hashPasswordSHA256(password);

                reference = FirebaseDatabase.getInstance().getReference("Users");
                checkUser = reference.orderByChild("name").equalTo(name);

                auth = FirebaseAuth.getInstance();

                if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
                    checkFields(name, password, email);
                } else {
                    checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // checking if username exists
                            if (snapshot.exists()) {
                                editName.setError(null);
                                emailFromDB = snapshot.child(name).child("email").getValue(String.class);

                                // checking if email exists
                                if (emailFromDB.equals(email)) {

                                    // checking if authenticate
                                    auth.signInWithEmailAndPassword(email, password)
                                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                                @Override
                                                public void onSuccess(AuthResult authResult) {
                                                    nameFromDB = snapshot.child(name).child("name").getValue(String.class);
                                                    phoneFromDB = snapshot.child(name).child("phone").getValue(String.class);
                                                    emailFromDB = snapshot.child(name).child("email").getValue(String.class);

                                                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                                                    Intent intent = new Intent(Login.this, AllUsersListings.class);

                                                    intent.putExtra("name", nameFromDB);
                                                    intent.putExtra("phone", phoneFromDB);
                                                    intent.putExtra("email", emailFromDB);

                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                } else {
                                    editEmail.setError("Invalid Credentials");
                                    editEmail.requestFocus();
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

        requestPasswordReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(editEmail.getText().toString())){
                    resetPassword();
                } else {
                    editEmail.setError("Please enter an email");
                    editEmail.requestFocus();
                }
            }
        });
    }

    private void checkFields(String name, String password, String email) {
        if (name.isEmpty()) {
            editName.setError("Please enter your user name");
            editName.requestFocus();
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

    private void resetPassword() {
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "Enter a valid email", Toast.LENGTH_SHORT).show();
            }
        });
    }
}