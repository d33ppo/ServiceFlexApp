package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.serviceflexapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProviderMainActivity extends AppCompatActivity {

    private String providerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main);

        // Retrieve the providerId
        providerId = getProviderId();

        // Find the NavHostFragment by its ID
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.provider_nav_host_fragment);

        // Get the NavController from the NavHostFragment
        NavController navController = navHostFragment.getNavController();

        // Set up BottomNavigationView and its listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.provider_bottom_navigation);

        // Handle navigation item selection with NavController
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_home) {
                navController.navigate(R.id.providerHomeFragment); // Navigate to the home fragment
                return true;
            } else if (itemId == R.id.bottom_booking) {
                navController.navigate(R.id.providerBookingsFragment); // Navigate to the bookings fragment
                return true;
            } else if (itemId == R.id.bottom_inbox) {
                // Pass the providerId to the Inbox Fragment
                Bundle bundle = new Bundle();
                bundle.putString("providerId", providerId); // Pass the providerId
                navController.navigate(R.id.providerInboxFragment, bundle); // Navigate to the inbox fragment
                return true;
            } else if (itemId == R.id.bottom_profile) {
                navController.navigate(R.id.providerProfileFragment); // Navigate to the profile fragment
                return true;
            }

            return false;
        });
    }

    public static String getProviderId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // You can fetch additional provider data from Firestore or use the Firebase user ID
            return user.getUid();  // This is the Firebase user ID, which can act as the provider ID
        } else {
            // Handle error: User not logged in
            return null;
        }
    }
}
