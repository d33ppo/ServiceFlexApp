package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.example.serviceflexapp.database.BookingAdapterProviderCompleted;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProviderBookingsPage2Fragment extends Fragment {

    //Dennis code's start here
    private RecyclerView recyclerView;
    private BookingAdapterProviderCompleted bookingAdapter;
    private List<Booking> completedBookings;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_provider_bookings_page2, container, false);
    }
    //Dennis code's end here

    /*
    public ProviderBookingsPage2Fragment() {
        super(R.layout.fragment_provider_bookings_page2);
    }
     */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Dennis code's start here
        recyclerView = view.findViewById(R.id.RV_BookingsComplete);
        completedBookings = new ArrayList<>();
        bookingAdapter = new BookingAdapterProviderCompleted(completedBookings);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookingAdapter);

        loadCompletedBookings();

        /*
        // Button to navigate to Upcoming Bookings
        btnUpcoming.setOnClickListener(v -> {
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new ProviderBookingsPage1Fragment())
                            .addToBackStack(null)
                            .commit();

         */
        //Dennis code's end here

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.BTN_Upcoming).setOnClickListener(v ->
                navController.navigate(R.id.action_providerBookingsPage2Fragment_to_providerBookingsPage1Fragment));
    };

    //Dennis code's start here
        private void loadCompletedBookings() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DatabaseReference realTimeDb = FirebaseDatabase.getInstance().getReference("Consumer"); // Path to user details
            String providerId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Get the logged-in provider's UID

            // Access the collection path: providers -> providerId -> appointment
            db.collection("providers").document(providerId)
                    .collection("appointment")
                    .whereEqualTo("isCompleted", true) // Fetch only completed appointments
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            completedBookings.clear(); // Clear existing data

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String consumerId = document.getString("consumerId");
                                String bookingDate = document.getString("bookingDate");
                                String bookingTime = document.getString("bookingTime");

                                // Fetch consumer's first name and address from Realtime Database
                                realTimeDb.child(consumerId).addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String firstName = snapshot.child("firstName").getValue(String.class);
                                        String address = snapshot.child("address").getValue(String.class);

                                        // Add the booking to the list
                                        completedBookings.add(new Booking(bookingDate, bookingTime, firstName, address));

                                        // Notify adapter about data changes
                                        bookingAdapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e("RealtimeDbError", "Error fetching consumer data", error.toException());
                                    }
                                });
                            }
                    } else {
                Log.e("FirestoreError", "Error fetching data", task.getException());
            }
        });

        //Dennis code's end here
}
}