package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;
import com.example.serviceflexapp.provider.ProviderMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize EditText fields
        emailEditText = findViewById(R.id.ETV_Email);  // Ensure you have an EditText with this ID in your layout
        passwordEditText = findViewById(R.id.ETV_Password);  // Ensure you have an EditText with this ID in your layout

        // Handle Login Button Click
        findViewById(R.id.Button_Login).setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            // Validate input
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate credentials (replace with your actual validation logic)
            String userRole = validateUserCredentials(email, password);

            if (userRole == null) {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                return;
            }

            //Sign in with Firebase Auth
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                navigateBasedOnRole(user.getUid());
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        findViewById(R.id.CTV_SignUp).setOnClickListener(v -> {
                    Intent intent = new Intent(LoginActivity.this, RegisterAsActivity.class);
                    startActivity(intent);
                });

        findViewById(R.id.CTV_ForgotPassword).setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
        });
    }
    private void navigateBasedOnRole(String userId) {
        // Fetch user role from Firebase Database
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(userId);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                String role = task.getResult().child("role").getValue(String.class);
                if ("Consumer".equals(role)) {
                    Intent intent = new Intent(LoginActivity.this, ConsumerMainActivity.class);
                    startActivity(intent);
                } else if ("Provider".equals(role)) {
                    Intent intent = new Intent(LoginActivity.this, ProviderMainActivity.class);
                    startActivity(intent);
                }
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Failed to fetch user role.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * Mock method to validate user credentials.
     * Replace with actual login validation logic (e.g., check against a database or authentication service).
     */
    private String validateUserCredentials(String email, String password) {
        // Example: hardcoded credentials for testing purposes
        // Replace with your actual validation logic (e.g., check database, API request)
        if (email.equals("consumer") && password.equals("consumer")) {
            return "Consumer";
        } else if (email.equals("provider") && password.equals("provider")) {
            return "Provider";
        } else {
            return null;  // Invalid credentials
        }
    }
}