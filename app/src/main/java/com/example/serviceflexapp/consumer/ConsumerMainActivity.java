package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import com.example.serviceflexapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConsumerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_main);

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
                navController.navigate(R.id.consumerInboxFragment); // Navigate to the inbox fragment
                return true;
            } else if (itemId == R.id.bottom_profile) {
                navController.navigate(R.id.consumerProfileFragment); // Navigate to the profile fragment
                return true;
            }

            return false;
        });
    }
}