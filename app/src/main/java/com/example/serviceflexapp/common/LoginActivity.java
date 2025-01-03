package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;
import com.example.serviceflexapp.provider.ProviderMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize UI components
        emailEditText = findViewById(R.id.ETV_Email);
        passwordEditText = findViewById(R.id.ETV_Password);

        // Initialize FirebaseAuth and DatabaseReference
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");

        // Handle login button click
        findViewById(R.id.Button_Login).setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            assert user != null;

                            // Fetch user role from Realtime Database
                            database.child(user.getUid()).get()
                                    .addOnSuccessListener(dataSnapshot -> {
                                        if (dataSnapshot.exists()) {
                                            String role = dataSnapshot.child("role").getValue(String.class);
                                            if ("Consumer".equals(role)) {
                                                startActivity(new Intent(this, ConsumerMainActivity.class));
                                            } else if ("Provider".equals(role)) {
                                                startActivity(new Intent(this, ProviderMainActivity.class));
                                            }
                                            finish(); // Close LoginActivity
                                        } else {
                                            Toast.makeText(this, "User role not found", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(e -> {
                                        Toast.makeText(this, "Failed to fetch role: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(this, "Login failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        // Handle sign up button click
        findViewById(R.id.CTV_SignUp).setOnClickListener(v -> {
            // Navigate to the Sign Up Activity
            Intent intent = new Intent(LoginActivity.this, RegisterAsActivity.class); // Replace with your SignUpActivity
            startActivity(intent);
        });

        // Handle forgot password click
        findViewById(R.id.CTV_ForgotPassword).setOnClickListener(v -> {
            // Navigate to the Forgot Password Activity
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
}