package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.serviceflexapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ConsumerMainActivity extends AppCompatActivity {

    private String consumerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_main);

        // Retrieve the consumerId
        consumerId = getConsumerId();

        // Find the NavHostFragment by its ID
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.consumer_nav_host_fragment);

        // Get the NavController from the NavHostFragment
        NavController navController = navHostFragment.getNavController();

        // Set up BottomNavigationView and its listener
        BottomNavigationView bottomNavigationView = findViewById(R.id.consumer_bottom_navigation);

        // Handle navigation item selection with NavController
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_home) {
                navController.navigate(R.id.consumerHomeFragment); // Navigate to the home fragment
                return true;
            } else if (itemId == R.id.bottom_booking) {
                navController.navigate(R.id.consumerUpcomingBookings); // Navigate to the bookings fragment
                return true;
            } else if (itemId == R.id.bottom_inbox) {
                // Pass the consumerId to the Inbox Fragment
                Bundle bundle = new Bundle();
                bundle.putString("consumerId", consumerId); // Pass the consumerId
                navController.navigate(R.id.consumerInboxFragment, bundle); // Navigate to the inbox fragment
                return true;
            } else if (itemId == R.id.bottom_profile) {
                navController.navigate(R.id.consumerProfileFragment); // Navigate to the profile fragment
                return true;
            }

            return false;
        });
    }

    public static String getConsumerId() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // You can fetch additional consumer data from Firestore or use the Firebase user ID
            return user.getUid();  // This is the Firebase user ID, which can act as the consumer ID
        } else {
            // Handle error: User not logged in
            return null;
        }
    }
}