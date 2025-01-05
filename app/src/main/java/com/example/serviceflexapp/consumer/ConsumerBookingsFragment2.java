package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConsumerBookingsFragment2 extends Fragment {

    private CalendarView calendarView;

    Provider provider;

    private DatabaseReference databaseReference;
    private TimePicker timePicker;
    private Button confirmButton;
    private FirebaseFirestore firestore;
    private List providerAvailability;

    public ConsumerBookingsFragment2() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consumer_bookings2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance();

        // Initialize UI components
        calendarView = view.findViewById(R.id.CV_ChooseDate);
        timePicker = view.findViewById(R.id.TP_PickTime);
        confirmButton = view.findViewById(R.id.BTN_Confirm);

        // Load provider availability
        loadProviderAvailability();

        // Handle the Confirm button click
        confirmButton.setOnClickListener(v -> {
            // Get selected date and time
            long selectedDateMillis = calendarView.getDate();
            int selectedHour = timePicker.getHour();
            int selectedMinute = timePicker.getMinute();

            // Format the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selectedDateMillis);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            String selectedDay = dayFormat.format(calendar.getTime());

            // Check availability
            if (providerAvailability != null && providerAvailability.contains(selectedDay)) {
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                String formattedDateTime = dateTimeFormat.format(calendar.getTime());

                // Save booking data
                saveBookingData(formattedDateTime);
            } else {
                Toast.makeText(getContext(), "Selected date is not within the provider's availability.", Toast.LENGTH_SHORT).show();
            }
        });
// Handle navigation to the next fragment
        Button nextConfirmButton = view.findViewById(R.id.BTN_Confirm);
        nextConfirmButton.setOnClickListener(v -> {
            Bundle args = getArguments();
            if (args != null) {
                String providerId = args.getString("providerId");
                if (providerId != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("providerId", providerId);
                    NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.action_consumerBookingsFragment2_to_consumerBookingsFragment3, bundle);
                } else {
                    Toast.makeText(getContext(), "Error: Provider ID not found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Error: Arguments are null", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle navigation back to the previous fragment
        ImageButton previousButton = view.findViewById(R.id.IB_Previous6);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }

    private void loadProviderAvailability() {
        // Retrieve the selected category from the arguments
        Bundle args = getArguments();
        String providerId = args.getString("providerId");
        String category = args.getString("category");

        databaseReference = FirebaseDatabase.getInstance().getReference("Provider").child(category).child(providerId).child("availability");

        databaseReference.addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Define the GenericTypeIndicator for List<String> (or any other type you expect)
                    GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {};

                    // Retrieve the list using the GenericTypeIndicator
                    providerAvailability = snapshot.getValue(genericTypeIndicator);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("RealtimeDB", "Error: " + error.getMessage());
            }
        });



        /*firestore.collection("Provider/"+category)
                .document(providerId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<String> availabilityArray = documentSnapshot.get("availability", List.class);
                        if (availabilityArray != null) {
                            providerAvailability = availabilityArray;
                        } else {
                            providerAvailability = null;
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Failed to load provider availability.", Toast.LENGTH_SHORT).show());*/
    }

    private void saveBookingData(String dateTime) {
        // Create a map with the data to save
        Map<String, Object> bookingData = Map.of("bookingDateTime", dateTime);

        // Save data to Firestore
        firestore.collection("Appointments Database")
                .add(bookingData)
                .addOnSuccessListener(documentReference -> {
                    // Data saved successfully
                    Toast.makeText(getContext(), "Booking Confirmed", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Error saving data
                    Toast.makeText(getContext(), "Failed to save booking", Toast.LENGTH_SHORT).show();
                });
    }


}

