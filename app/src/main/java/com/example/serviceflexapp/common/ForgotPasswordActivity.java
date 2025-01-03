package com.example.serviceflexapp.common;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailEditText, recoveryCodeEditText, newPasswordEditText, confirmPasswordEditText;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize EditText fields
        emailEditText = findViewById(R.id.ETV_RecoveryEmail);


        // Handle Confirm Button Click
        Button confirmButton = findViewById(R.id.BTN_Confirm);
        confirmButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String recoveryCode = recoveryCodeEditText.getText().toString().trim();
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (email.isEmpty() || recoveryCode.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Reset the password using the recovery code
            mAuth.confirmPasswordReset(recoveryCode, newPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } else {
                        Log.e("ForgotPasswordActivity", "Password reset failed: " + task.getException().getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, "Password reset failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        });

        // Handle Resend Code Click
        findViewById(R.id.CTV_ResendCode).setOnClickListener(v -> {
            if (emailEditText.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.sendPasswordResetEmail(emailEditText.getText().toString().trim())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Recovery code sent to your email", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("ForgotPasswordActivity", "Failed to send recovery code: " + task.getException().getMessage());
                        Toast.makeText(ForgotPasswordActivity.this, "Failed to send recovery code: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        });
    }
}