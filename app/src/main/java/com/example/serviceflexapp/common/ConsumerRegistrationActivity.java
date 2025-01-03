package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.consumer.ConsumerMainActivity;
import com.example.serviceflexapp.database.Consumer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConsumerRegistrationActivity extends AppCompatActivity {

    private EditText firstNameEditText, lastNameEditText, phoneNumberEditText, emailEditText, passwordEditText, addressEditText;
    private Button nextButton;

    private FirebaseAuth mAuth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_registration);

        // Initialize Firebase Auth and Realtime Database
        mAuth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        // Initialize UI components
        firstNameEditText = findViewById(R.id.ETV_FirstName);
        lastNameEditText = findViewById(R.id.ETV_LastName);
        phoneNumberEditText = findViewById(R.id.ETV_PhoneNumber);
        emailEditText = findViewById(R.id.ETV_Email);
        passwordEditText = findViewById(R.id.ETV_Password);
        addressEditText = findViewById(R.id.ETV_Address);
        nextButton = findViewById(R.id.Button_Next);

        // Set button click listener
        nextButton.setOnClickListener(v -> registerConsumer());
    }

    private void registerConsumer() {
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Validate inputs
        if (TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumber)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register with Firebase Authentication
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String consumerId = user.getUid();
                            Consumer consumer = new Consumer(firstName, lastName, phoneNumber, email, password, address);

                            // Set the role as "Consumer" in the database
                            reference.child("users").child(consumerId).child("role").setValue("Consumer");

                            // Save consumer data to the Realtime Database
                            reference.child("Consumer").child(consumerId).setValue(consumer)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                                            navigateToHome();
                                        } else {
                                            Toast.makeText(this, "Failed to save data. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        Toast.makeText(this, "Registration failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void navigateToHome() {
        Intent intent = new Intent(ConsumerRegistrationActivity.this, ConsumerMainActivity.class);
        intent.putExtra("userRole", "Consumer");
        startActivity(intent);
        finish();
    }
}
