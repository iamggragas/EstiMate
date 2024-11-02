package com.example.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.main.Utilities.HelperClass;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText editName, editPhone, editEmail, editPassword;
    private String name, phone, email, password;

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

        // auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Users");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = editName.getText().toString().trim();
                phone = editPhone.getText().toString().trim();
                email = editEmail.getText().toString().trim();
                password = editPassword.getText().toString().trim();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    checkFields(name, phone, email, password);
                } else {
                    HelperClass helperClass = new HelperClass(name, phone, email, password);
                    reference.child(name).setValue(helperClass);

                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
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