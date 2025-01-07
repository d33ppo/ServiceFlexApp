package com.example.serviceflexapp.consumer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Booking;
import com.example.serviceflexapp.database.BookingAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ConsumerUpcomingBookings extends Fragment {
    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private List<Booking> upcomingBookings;
    private Button btnCompleted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consumer_upcoming_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RV_BookingsUpcomingConsumer);
        btnCompleted = view.findViewById(R.id.BTN_Completed);

        // Initialize booking list and adapter
        upcomingBookings = new ArrayList<>();
        bookingAdapter = new BookingAdapter(upcomingBookings);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookingAdapter);

        loadUpcomingBookings();

        NavController navController = Navigation.findNavController(view);

        // Button to navigate to Completed Bookings
        view.findViewById(R.id.BTN_Completed).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerUpcomingBookings_to_consumerCompletedBookings));
    }


    private void loadUpcomingBookings() {
        Log.d("DebugFlow", "Loading upcoming bookings...");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseReference realTimeDb = FirebaseDatabase.getInstance().getReference("Provider");
        String consumerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Assuming the consumer is logged in

        Log.d("DebugFlow", "Consumer ID: " + consumerId);

        // Fetch provider IDs from the 'consumers' collection
        db.collection("consumers")
                .document(consumerId)
                .collection("appointment")
                .whereEqualTo("isCompleted", false)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FirestoreDebug", "Fetched providers for consumer.");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get the providerId from the document ID
                            String providerId = document.getString("providerId");
                            String bookingDate = document.getString("bookingDate");
                            String bookingTime = document.getString("bookingTime");
                            String category = document.getString("category");
                            Log.d("FirestoreDebug", "Inside line 86 for loop.");
                            Log.d("FirestoreDebug", "providerId: " + providerId);
                            Log.d("FirestoreDebug", "category: " + category);

                            realTimeDb.child(category).child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String providerName = snapshot.child("firstName").getValue(String.class);
                                    String providerAddress = snapshot.child("address").getValue(String.class);
                                    String providerId = snapshot.child("providerId").getValue(String.class);


                                    Log.d("RealtimeDbDebug", "Provider Name: " + providerName + ", Address: " + providerAddress);

                                    // Add the booking to the list
                                    upcomingBookings.add(new Booking(providerId, bookingDate, bookingTime, providerName, providerAddress, category));

                                        bookingAdapter.notifyDataSetChanged();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("RealtimeDbError", "Error fetching provider data", error.toException());
                                }
                            });
                            Log.d("FirestoreDebug", "Fetched providerId: " + providerId);
                        }
                    } else {
                        Log.e("FirestoreError", "Error fetching providers", task.getException());
                    }
                });
    }

    private void fetchProviderDetails(String providerId, String bookingDate, String bookingTime, DatabaseReference realTimeDb) {
        realTimeDb.child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String providerName = snapshot.child("name").getValue(String.class);
                String providerAddress = snapshot.child("address").getValue(String.class);

                Log.d("RealtimeDbDebug", "Provider Name: " + providerName + ", Address: " + providerAddress);

                // Add the booking to the list
                upcomingBookings.add(new Booking(providerId, bookingDate, bookingTime, providerName, providerAddress));

                // Notify the adapter to update the RecyclerView
                recyclerView.post(() -> {
                    Log.d("RecyclerViewDebug", "Notifying adapter of data change.");
                    bookingAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RealtimeDbError", "Error fetching provider data", error.toException());
            }
        });
    }
}