package com.example.serviceflexapp.consumer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConsumerHomeFragment1 extends Fragment {

    private LinearLayout LL_CategoryPlumber, LL_CategoryElectrician, LL_CategoryBarber, LL_CategoryMaid;

    private String consumerId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumer_home1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize category LinearLayouts
        LL_CategoryPlumber = view.findViewById(R.id.LL_CategoryPlumber);
        LL_CategoryElectrician = view.findViewById(R.id.LL_CategoryElectrician);
        LL_CategoryBarber = view.findViewById(R.id.LL_CategoryBarber);
        LL_CategoryMaid = view.findViewById(R.id.LL_CategoryMaid);

        // Set up click listeners for categories
        LL_CategoryPlumber.setOnClickListener(v -> inflateCheck(view, "Plumber"));
        LL_CategoryElectrician.setOnClickListener(v -> inflateCheck(view, "Electrician"));
        LL_CategoryBarber.setOnClickListener(v -> inflateCheck(view, "Barber"));
        LL_CategoryMaid.setOnClickListener(v -> inflateCheck(view, "Maid"));
    }

    private void navigateToCategory(View view, String category) {
        // Use NavController to pass the category to the next fragment
        NavController navController = Navigation.findNavController(view);
        Bundle bundle = new Bundle();
        bundle.putString("consumerId", getConsumerId());
        bundle.putString("category", category);
        navController.navigate(R.id.action_consumerHomeFragment_to_consumerHomeFragment2, bundle);
    }

    private void inflateCheck(View view, String category) {
        // Reference to Firebase database for the selected category
        DatabaseReference categoryRef = FirebaseDatabase.getInstance().getReference("Provider/"  + category);

        categoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.hasChildren()) {
                    // Navigate to the next fragment if values exist
                    navigateToCategory(view, category);
                } else {
                    // Show a toast message if no values exist
                    Toast.makeText(requireContext(), "No services available for " + category, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Show a toast message for any database error
                Toast.makeText(requireContext(), "Error checking services: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
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