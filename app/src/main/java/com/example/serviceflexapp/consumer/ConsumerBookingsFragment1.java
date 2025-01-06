package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.serviceflexapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class ConsumerBookingsFragment1 extends Fragment {

    public ConsumerBookingsFragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_consumer_bookings1, container, false);

        // Initialize UI components
        TextView providerSelectedCategoryTextView = rootView.findViewById(R.id.TV_Category);
        TextView providerNameTextView = rootView.findViewById(R.id.TV_Name);
        TextView providerAgeTextView = rootView.findViewById(R.id.TV_Age1);
        TextView providerQualificationsTextView = rootView.findViewById(R.id.TV_Education);
        TextView providerAvailabilityTextView = rootView.findViewById(R.id.TV_Availability);
        TextView providerEmailTextView = rootView.findViewById(R.id.TV_ProviderEmail);
        TextView providerPricingTextView = rootView.findViewById(R.id.TV_Pricing);
        ImageView providerImageView = rootView.findViewById(R.id.imageView);

        // Retrieve data from the fragment's arguments
        Bundle args = getArguments();
        if (args != null) {
            String providerId = args.getString("providerId");
            String providerName = args.getString("name");
            Integer providerAge = args.getInt("age");
            String providerEmail = args.getString("email");
            String providerPriceRange = args.getString("priceRange");
            String providerImageUrl = args.getString("imageUrl");
            String selectedCategory = args.getString("category");

            // Set the data in the UI components
            providerSelectedCategoryTextView.setText(selectedCategory);
            providerNameTextView.setText("Name: " + providerName);
            providerAgeTextView.setText("Age: " + providerAge + " years old");
            providerEmailTextView.setText("Email: " + providerEmail);
            providerPricingTextView.setText("Pricing Range: RM " + providerPriceRange);  // Set the pricing value

            // Load the provider image using Glide
            Glide.with(requireContext())
                    .load(providerImageUrl)
                    .placeholder(R.drawable.profile_circle_icon) // Optional placeholder
                    .into(providerImageView);

            // Fetch additional data (qualifications and availability) from Firebase
            fetchAdditionalData(providerId, providerQualificationsTextView, providerAvailabilityTextView, selectedCategory);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set up the navigation controller
        ImageButton previousButton = view.findViewById(R.id.IB_Previous8);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });

        Button bookNowButton = view.findViewById(R.id.button2);
        bookNowButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);

            // Retrieve the selected category from the arguments
            Bundle args = getArguments();
            if (args != null) {
                String providerId = args.getString("providerId");
                String category = args.getString("category");

                // Pass the provider ID to the next fragment
                Bundle bundle = new Bundle();
                bundle.putString("providerId", providerId);
                bundle.putString("category", category);
                navController.navigate(R.id.action_consumerBookingsFragment_to_consumerBookingsFragment2, bundle);
            }
            else {
                Toast.makeText(getContext(), "Error: Provider ID not found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAdditionalData(String providerId, TextView qualificationsTextView, TextView availabilityTextView, String selectedCategory) {
        if (providerId == null) return;

        DatabaseReference providerRef = FirebaseDatabase.getInstance().getReference("Provider").child(selectedCategory).child(providerId);

        providerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String available = "";
                if (snapshot.exists()) {
                    // Extract additional data
                    String qualifications = snapshot.child("qualifications").getValue(String.class);
                    GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {};
                    List<String> availabilityArray = snapshot.child("availability").getValue(genericTypeIndicator);

                    for (int n = 0; n < availabilityArray.size(); n++) {
                        available += availabilityArray.get(n);
                        if (n < availabilityArray.size() - 1) {
                            available += ", "; // Add a comma only if it's not the last element
                        }
                    }
                    // Update UI components
                    qualificationsTextView.setText("Qualifications: " + (qualifications != null ? qualifications : "No qualifications available"));
                    availabilityTextView.setText("Available on: " + available);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle Firebase errors here if necessary
            }
        });
    }
}