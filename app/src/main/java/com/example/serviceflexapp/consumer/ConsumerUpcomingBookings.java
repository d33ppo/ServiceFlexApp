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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DatabaseReference realTimeDb = FirebaseDatabase.getInstance().getReference("Provider"); // Path to provider details
        String consumerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Assuming the consumer is logged in

        // Access the collection path: providers -> providerId -> appointment
        db.collection("providers")
                .get()  // Fetch all providers
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot providerDocument : task.getResult()) {
                            String providerId = providerDocument.getId(); // The provider document ID

                            // Retrieve the appointments for the specific provider and filter by consumerId
                            db.collection("providers")
                                    .document(providerId)
                                    .collection("appointment")
                                    .whereEqualTo("consumerId", consumerId) // Fetch only appointments for the current consumer
                                    .whereEqualTo("isCompleted", false) // Fetch only upcoming appointments
                                    .get()
                                    .addOnCompleteListener(appointmentTask -> {
                                        if (appointmentTask.isSuccessful()) {
                                            for (QueryDocumentSnapshot appointmentDocument : appointmentTask.getResult()) {
                                                String bookingDate = appointmentDocument.getString("bookingDate");
                                                String bookingTime = appointmentDocument.getString("bookingTime");

                                                // Fetch provider's name and address from Realtime Database
                                                realTimeDb.child(providerId).addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String providerName = snapshot.child("name").getValue(String.class);
                                                        String providerAddress = snapshot.child("address").getValue(String.class);

                                                        // Add the booking to the list
                                                        upcomingBookings.add(new Booking(bookingDate, bookingTime, providerName, providerAddress));

                                                        // Notify adapter about data changes
                                                        bookingAdapter.notifyDataSetChanged();
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {
                                                        Log.e("RealtimeDbError", "Error fetching provider data", error.toException());
                                                    }
                                                });
                                            }
                                        } else {
                                            Log.e("FirestoreError", "Error fetching appointments", appointmentTask.getException());
                                        }
                                    });
                        }
                    } else {
                        Log.e("FirestoreError", "Error fetching providers", task.getException());
                    }
                });
    }

}