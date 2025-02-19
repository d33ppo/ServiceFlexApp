package com.example.serviceflexapp.consumer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConsumerHomeFragment2 extends Fragment{

    private DatabaseReference databaseReference;
    private String selectedCategory;
    private RecyclerView recyclerView;
    private ProviderAdapter adapter;
    private List<Provider> providerList;

    private String consumerId;

    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_home2, container, false);

        // Initialize DatabaseReference
        if (getArguments() != null) {
            selectedCategory = getArguments().getString("category");
            consumerId = getArguments().getString("consumerId");
            databaseReference = FirebaseDatabase.getInstance().getReference("Provider").child(selectedCategory);
        }

        // Set the category title
        TextView tvCategory = view.findViewById(R.id.TV_Category);
        if (selectedCategory != null) {
            tvCategory.setText(selectedCategory);
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.RV_ProviderMiniInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize provider list and adapter
        providerList = new ArrayList<>();
        navController = Navigation.findNavController(getActivity(), R.id.consumer_nav_host_fragment); // Get the NavController here
        adapter = new ProviderAdapter(getActivity(), providerList/*, navController*/, selectedCategory, consumerId);
        recyclerView.setAdapter(adapter);

        // Fetch and display data based on the selected category
        fetchAndDisplayData();

        return view;
    }

    private void fetchAndDisplayData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                providerList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String providerID = snapshot.getKey();
                        String firstName = snapshot.child("firstName").getValue(String.class);
                        String priceRange = snapshot.child("priceRange").getValue(String.class);
                        String imageURL = snapshot.child("imageURL").getValue(String.class);
                        String email = snapshot.child("email").getValue(String.class);
                        Integer age = snapshot.child("age").getValue(Integer.class);

                        Provider provider = new Provider(providerID,firstName, priceRange, imageURL, email, age);
                        providerList.add(provider);
                    }
                    // Notify adapter after data is updated
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("RealtimeDB", "No providers found for category: " + selectedCategory);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("RealtimeDB", "Error: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setNavController(navController);
        ImageButton previousButton = view.findViewById(R.id.IB_Previous7);
        previousButton.setOnClickListener(v -> {
            navController = Navigation.findNavController(view);
            navController.popBackStack();
        })

        ;
    }
    private void navigateToProviderDetails(View view, Provider provider) {
        navController = Navigation.findNavController(view);
        Bundle bundle = new Bundle();

        // Pass the category
        bundle.putString("category", selectedCategory);

        // Pass provider-specific data if needed
        bundle.putString("providerId", provider.getProviderId());
        bundle.putString("name", provider.getFirstName());
        bundle.putInt("age", provider.getAge());
        bundle.putString("email", provider.getEmail());
        bundle.putString("priceRange", provider.getPriceRange());
        bundle.putString("imageUrl", provider.getImageURL());

        navController.navigate(R.id.action_consumerHomeFragment2_to_consumerBookingsFragment, bundle);
    }

}