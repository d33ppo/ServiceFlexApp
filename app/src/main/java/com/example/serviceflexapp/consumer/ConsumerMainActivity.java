package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.serviceflexapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ConsumerMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_main);

//        // Find the NavHostFragment by its ID
//        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.consumer_nav_host_fragment);
//
//        // Get the NavController from the NavHostFragment
//        NavController navController = navHostFragment.getNavController();
//
//        // Set up BottomNavigationView and its listener automatically
//        BottomNavigationView bottomNavigationView = findViewById(R.id.consumer_bottom_navigation);
//
//        // This will automatically handle navigation and the BottomNavigationView item selection
//        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}