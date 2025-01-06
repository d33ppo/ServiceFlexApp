package com.example.serviceflexapp.consumer;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ImageButton;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ConsumerBookingsFragment2 extends Fragment {

    private CalendarView calendarView;
    private TimePicker timePicker;
    private Button confirmButton;
    private List<String> providerAvailability;
    private DatabaseReference databaseReference;
    private Bundle nextBundle;

    final Calendar[] calendar = new Calendar[1];

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

        // Initialize UI components
        calendarView = view.findViewById(R.id.CV_ChooseDate);
        timePicker = view.findViewById(R.id.TP_PickTime);
        confirmButton = view.findViewById(R.id.BTN_Confirm);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar[0] = Calendar.getInstance();
                calendar[0].set(year, month, dayOfMonth);
                Log.d("Selected date millis",calendar[0].toString());
            }
        });
        // Handle the Confirm button click
        confirmButton.setOnClickListener(v -> {

            // Get selected date and time


            int selectedHour = timePicker.getHour();
            int selectedMinute = timePicker.getMinute();

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.getDefault());
            String selectedDay = dayFormat.format(calendar[0].getTime());
            Log.d("Day", "Day: "+ selectedDay);

            // Check availability
            checkProviderAvailability(calendar[0], selectedDay, selectedHour, selectedMinute);
        });

        // Handle navigation back to the previous fragment
        ImageButton previousButton = view.findViewById(R.id.IB_Previous6);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }

    private void checkProviderAvailability(Calendar calendar, String selectedDay, int selectedHour, int selectedMinute) {
        Log.d("Selected Day and Time","Day: "+selectedDay+" Time: "+selectedHour+selectedMinute);
        // Retrieve the selected category from the arguments
        Bundle args = getArguments();
        if (args == null) {
            Toast.makeText(getContext(), "No provider information provided.", Toast.LENGTH_SHORT).show();
            return;
        }

        String providerId = args.getString("providerId");
        String category = args.getString("category");

        if (providerId == null || category == null) {
            Toast.makeText(getContext(), "Provider ID or category is missing.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Log the providerId and category
        Log.d("LoadProviderAvailability", "Provider ID: " + providerId);
        Log.d("LoadProviderAvailability", "Category: " + category);

        databaseReference = FirebaseDatabase.getInstance().getReference("Provider").child(category).child(providerId).child("availability");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("Listening to database","fetch database");
                if (snapshot.exists()) {
                    // Define the GenericTypeIndicator for List<String>
                    GenericTypeIndicator<List<String>> genericTypeIndicator = new GenericTypeIndicator<List<String>>() {};
                    providerAvailability = snapshot.getValue(genericTypeIndicator);
                    Log.d("LoadProviderAvailability", "Provider Availability: " + providerAvailability);

                    // Check if the selected day is available
                    if (providerAvailability != null && providerAvailability.contains(selectedDay)) {
                        // Prepare booking data in a bundle
                        prepareBookingBundle(calendar, selectedHour, selectedMinute);
                    } else {
                        Toast.makeText(getContext(), "Selected date is not within the provider's availability.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("LoadProviderAvailability", "No data found for the specified provider.");
                    Toast.makeText(getContext(), "No availability data found for the provider.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("LoadProviderAvailability", "Error: " + error.getMessage());
                Toast.makeText(getContext(), "Failed to load provider availability.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareBookingBundle(Calendar calendar, int selectedHour, int selectedMinute) {
        /*// Retrieve the selected date from the CalendarView
        long selectedDateMillis = calendarView.getDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(selectedDateMillis);*/

        // Format the date and time
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(calendar.getTime());

        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute);

        // Retrieve additional booking details from the arguments
        Bundle args = getArguments();
        if (args == null) {
            Toast.makeText(getContext(), "No booking details provided.", Toast.LENGTH_SHORT).show();
            return;
        }

        String providerId = args.getString("providerId");
        String category = args.getString("category");
        String consumerId = args.getString("consumerId");

        if (providerId == null || category == null) {
            Toast.makeText(getContext(), "Missing booking details.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Pass booking details in a bundle
        nextBundle = new Bundle();
        nextBundle.putString("date", formattedDate);
        nextBundle.putString("time", formattedTime);
        nextBundle.putString("providerId", providerId);
        nextBundle.putString("category", category);
        nextBundle.putString("consumerId", consumerId);

        NavController navController = Navigation.findNavController(requireView());
        Log.d("Passing bundle","Bundle: "+ nextBundle.toString());
        navController.navigate(R.id.action_consumerBookingsFragment2_to_consumerBookingsFragment3, nextBundle);
    }
}

