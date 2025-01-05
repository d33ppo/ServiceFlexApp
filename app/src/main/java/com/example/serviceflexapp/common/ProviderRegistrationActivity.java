package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.provider.ProviderHomeFragment;
import com.example.serviceflexapp.provider.ProviderMainActivity;

public class ProviderRegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_registration);

        if (savedInstanceState == null) {
            // Add the ProviderRegistrationPage1Fragment to this activity
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ProviderRegistrationPage1Fragment())
                    .commit();
        }
    }

    // Add the method to load a fragment
    public void loadFragment(Fragment fragment) {
        // Begin a transaction and replace the current fragment with the new one
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment) // Ensure fragment_container is your fragment container's ID
                .addToBackStack(null) // Optional: Adds the transaction to the back stack
                .commit();
    }

    // This method is used to navigate to the home screen after registration
    public void navigateToHomeProvider() {
        // Replace with your logic to navigate to the provider's home screen
        // For example, if you want to navigate to the `ProviderMainActivity`:
        Intent intent = new Intent(ProviderRegistrationActivity.this, ProviderMainActivity.class);
        startActivity(intent);
        finish(); // Optionally finish this activity to remove it from the stack
    }
}