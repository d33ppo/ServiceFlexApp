package com.example.serviceflexapp.consumer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ConsumerCompletedBookings extends Fragment {

    private RecyclerView recyclerView;
    private BookingAdapter bookingAdapter;
    private List<Booking> completedBookings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consumer_completed_bookings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.RV_BookingsCompletedConsumer);

        // Initialize booking list and adapter
        completedBookings = new ArrayList<>();
        bookingAdapter = new BookingAdapter(completedBookings);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookingAdapter);

        loadCompletedBookings();

        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.BTN_Upcoming).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerCompletedBookings_to_consumerUpcomingBookings)
        );
    }

    private void loadCompletedBookings() {
        Log.d("DebugFlow", "Loading completed bookings...");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseReference realTimeDb = FirebaseDatabase.getInstance().getReference("Provider");
        String consumerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Assuming the consumer is logged in

        Log.d("DebugFlow", "Consumer ID: " + consumerId);

        // Fetch completed bookings from Firestore
        db.collection("consumers")
                .document(consumerId)
                .collection("completedAppointments")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("FirestoreDebug", "Fetched completed appointments for consumer.");
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Get the providerId and other details from the document
                            String providerId = document.getString("providerId");
                            String bookingDate = document.getString("bookingDate");
                            String bookingTime = document.getString("bookingTime");
                            String category = document.getString("category");

                            Log.d("FirestoreDebug", "Processing providerId: " + providerId);

                            realTimeDb.child(category).child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String providerName = snapshot.child("firstName").getValue(String.class);
                                    String providerAddress = snapshot.child("address").getValue(String.class);

                                    Log.d("RealtimeDbDebug", "Provider Name: " + providerName + ", Address: " + providerAddress);

                                    // Add the booking to the list
                                    completedBookings.add(new Booking(bookingDate, bookingTime, providerName, providerAddress));

                                    // Notify the adapter to update the RecyclerView
                                    bookingAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.e("RealtimeDbError", "Error fetching provider data", error.toException());
                                }
                            });
                        }
                    } else {
                        Log.e("FirestoreError", "Error fetching completed appointments", task.getException());
                    }
                });
    }
}
