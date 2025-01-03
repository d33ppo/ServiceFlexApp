package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ConsumerBookingsFragment2 extends Fragment {

    private CalendarView calendarView;
    private TimePicker timePicker;
    private Button confirmButton;
    private FirebaseFirestore firestore;

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

        // Handle the Confirm button click
        confirmButton.setOnClickListener(v -> {
            // Get selected date and time
            long selectedDateMillis = calendarView.getDate();
            int selectedHour = timePicker.getHour();
            int selectedMinute = timePicker.getMinute();

            // Format the date and time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(selectedDateMillis);
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
            calendar.set(Calendar.MINUTE, selectedMinute);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String formattedDateTime = dateFormat.format(calendar.getTime());

            // Save the selected date and time to Firestore
            saveBookingData(formattedDateTime);
        });

        // Handle navigation to the next fragment
        Button nextConfirmButton = view.findViewById(R.id.BTN_Confirm);
        nextConfirmButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_consumerBookingsFragment2_to_consumerBookingsFragment3);
        });

        // Handle navigation back to the previous fragment
        ImageButton previousButton = view.findViewById(R.id.IB_Previous6);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }

    private void saveBookingData(String dateTime) {
        // Create a map with the data to save
        Map<String, Object> bookingData = new HashMap<>();
        bookingData.put("bookingDateTime", dateTime);

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