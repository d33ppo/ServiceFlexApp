package com.example.serviceflexapp.common;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;
import com.example.serviceflexapp.provider.ProviderMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProviderRegistrationPage2Fragment extends Fragment {

    private EditText ETV_Age, ETV_MinPrice, ETV_MaxPrice, ETV_Qualifications;
    private CheckBox CB_Monday, CB_Tuesday, CB_Wednesday, CB_Thursday, CB_Friday, CB_Saturday, CB_Sunday;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_registration_page2, container, false);

        Spinner jobChoiceSpinner = view.findViewById(R.id.SP_JobChoice);

// Create a list of jobs (you can replace this with your own data)
        List<String> jobList = Arrays.asList("Barber", "Electrician", "Plumber", "Maid");

// Create an ArrayAdapter to bind the list to the Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, jobList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Set the adapter to the Spinner
        jobChoiceSpinner.setAdapter(adapter);

// Set an OnItemSelectedListener to handle item selection
        jobChoiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Get the selected item
                String selectedJob = parentView.getItemAtPosition(position).toString();

                // Display the selected job using a Toast
                Toast.makeText(requireContext(), "Selected: " + selectedJob, Toast.LENGTH_SHORT).show();

                // Additional actions can be added here based on the selected job
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Handle the case where no item is selected (optional)
                Toast.makeText(requireContext(), "No selection made", Toast.LENGTH_SHORT).show();
            }
        });

        ETV_Age = view.findViewById(R.id.ETV_Age);
        ETV_MinPrice = view.findViewById(R.id.ETV_MinPrice);
        ETV_MaxPrice = view.findViewById(R.id.ETV_MaxPrice);
        ETV_Qualifications = view.findViewById(R.id.ETV_Qualifications); // Added for qualifications

        // Initialize checkboxes for availability
        CB_Monday = view.findViewById(R.id.CB_Monday);
        CB_Tuesday = view.findViewById(R.id.CB_Tuesday);
        CB_Wednesday = view.findViewById(R.id.CB_Wednesday);
        CB_Thursday = view.findViewById(R.id.CB_Thursday);
        CB_Friday = view.findViewById(R.id.CB_Friday);
        CB_Saturday = view.findViewById(R.id.CB_Saturday);
        CB_Sunday = view.findViewById(R.id.CB_Sunday);

        Button BTN_Create = view.findViewById(R.id.Btn_Create);

        // Initialize Firebase Database and Auth
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        BTN_Create.setOnClickListener(v -> {
            // Collect selected days into a String[]
            List<String> selectedDays = new ArrayList<>();
            if (CB_Monday.isChecked()) selectedDays.add("Monday");
            if (CB_Tuesday.isChecked()) selectedDays.add("Tuesday");
            if (CB_Wednesday.isChecked()) selectedDays.add("Wednesday");
            if (CB_Thursday.isChecked()) selectedDays.add("Thursday");
            if (CB_Friday.isChecked()) selectedDays.add("Friday");
            if (CB_Saturday.isChecked()) selectedDays.add("Saturday");
            if (CB_Sunday.isChecked()) selectedDays.add("Sunday");

            String[] availability = selectedDays.toArray(new String[0]); // Convert List to String[]

            // Pass the availability array to registerProvider
            registerProvider(availability);
        });

        return view;
    }

    private void registerProvider(String[] availabilityArray) {
        Bundle bundle = getArguments();
        if (bundle == null) {
            Toast.makeText(getContext(), "Failed to retrieve data from previous page.", Toast.LENGTH_SHORT).show();
            return;
        }

        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");
        String phoneNumber = bundle.getString("phoneNumber");
        String email = bundle.getString("email");
        String password = bundle.getString("password");
        String address = bundle.getString("address");
        int age;
        try {
            age = Integer.parseInt(ETV_Age.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Invalid age input.", Toast.LENGTH_SHORT).show();
            return;
        }
        String priceRange = ETV_MinPrice.getText().toString().trim() + " - " + ETV_MaxPrice.getText().toString().trim();
        String qualifications = ETV_Qualifications.getText().toString().trim();

        Log.d("ProviderRegistration", "First Name: " + firstName);
        Log.d("ProviderRegistration", "Last Name: " + lastName);
        Log.d("ProviderRegistration", "Phone Number: " + phoneNumber);
        Log.d("ProviderRegistration", "Email: " + email);
        Log.d("ProviderRegistration", "Password: " + password);
        Log.d("ProviderRegistration", "Address: " + address);
        Log.d("ProviderRegistration", "Age: " + age);
        Log.d("ProviderRegistration", "Price Range: " + priceRange);
        Log.d("ProviderRegistration", "Qualifications: " + qualifications);
        Log.d("ProviderRegistration", "Availability: " + Arrays.toString(availabilityArray));

        // Convert the array to a List
        List<String> availability = new ArrayList<>(Arrays.asList(availabilityArray));

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String providerId = user.getUid();
                            FirebaseMessaging.getInstance().getToken()
                                    .addOnCompleteListener(task1 -> {
                                        if (!task1.isSuccessful()) {
                                            Log.w("FCM", "Fetching FCM registration token failed", task1.getException());
                                            Toast.makeText(getContext(), "Failed to fetch FCM token. Please try again.", Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        // Get the FCM token
                                        String fcmToken = task1.getResult();
                                        Log.d("FCM Token", fcmToken);

                                        // Create a Provider object
                                        Provider provider = new Provider(providerId, firstName, lastName, phoneNumber, email, address, age, priceRange, qualifications, availability, fcmToken);

                                        // Set the role as "Provider" in the database
                                        mDatabase.child("users").child(providerId).child("role").setValue("Provider");

                                        // Save the provider details
                                        mDatabase.child("Provider").child(providerId).setValue(provider)
                                                .addOnCompleteListener(task2 -> {
                                                    if (task2.isSuccessful()) {
                                                        Log.d("ProviderRegistration", "Provider registered successfully.");
                                                        navigateToProviderMainActivity();
                                                    } else {
                                                        Log.e("ProviderRegistration", "Failed to register provider.", task2.getException());
                                                        Toast.makeText(getContext(), "Failed to register provider. Please try again.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    });
                        }
                    } else {
                        Log.e("ProviderRegistration", "Failed to create user.", task.getException());
                        Toast.makeText(getContext(), "Failed to create user. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void navigateToProviderMainActivity() {
        Intent intent = new Intent(getActivity(), ProviderMainActivity.class);
        startActivity(intent);
    }
}