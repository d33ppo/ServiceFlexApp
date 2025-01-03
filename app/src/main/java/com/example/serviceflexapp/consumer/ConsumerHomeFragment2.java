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

public class ConsumerHomeFragment2 extends Fragment {

    private DatabaseReference databaseReference;
    private String selectedCategory;
    private RecyclerView recyclerView;
    private ServiceProviderAdapter adapter;
    private List<Provider> serviceProviderList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_home2, container, false);

        // Initialize DatabaseReference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Retrieve the selected category from the arguments
        if (getArguments() != null) {
            selectedCategory = getArguments().getString("category");
        }

        // Set the category title
        TextView tvCategory = view.findViewById(R.id.TV_Category);
        if (selectedCategory != null) {
            tvCategory.setText(selectedCategory);
        }

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize data list and adapter
        serviceProviderList = new ArrayList<>();
        adapter = new ServiceProviderAdapter(requireActivity().getSupportFragmentManager(), serviceProviderList);
        recyclerView.setAdapter(adapter);

        // Fetch and display data based on the selected category
        fetchAndDisplayData();

        return view;
    }

    private void fetchAndDisplayData() {
        databaseReference.child("Provider/category/" + selectedCategory)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        serviceProviderList.clear();
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                String firstName = snapshot.child("firstName").getValue(String.class);
                                String priceRange = snapshot.child("priceRange").getValue(String.class);
                                String imageURL = snapshot.child("imageURL").getValue(String.class);
                                String rating = snapshot.child("rating").getValue(String.class);

                                Provider serviceProvider = new Provider(firstName, priceRange, imageURL, rating);
                                serviceProviderList.add(serviceProvider);
                            }
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

        ImageButton previousButton = view.findViewById(R.id.IB_Previous7);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }
}