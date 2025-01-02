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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;
import com.example.serviceflexapp.provider.ProviderMainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProviderRegistrationPage2Fragment extends Fragment {

    private EditText ETV_Age, ETV_MinPrice, ETV_MaxPrice;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_registration_page2, container, false);

        ETV_Age = view.findViewById(R.id.ETV_Age);
        ETV_MinPrice = view.findViewById(R.id.ETV_MinPrice);
        ETV_MaxPrice = view.findViewById(R.id.ETV_MaxPrice);
        Button BTN_Create = view.findViewById(R.id.Btn_Create);

        // Initialize Firebase Database and Auth
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        BTN_Create.setOnClickListener(v -> registerProvider());

        return view;
    }

    private void registerProvider() {
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
        int age = Integer.parseInt(ETV_Age.getText().toString().trim());
        String priceRange = ETV_MinPrice.getText().toString().trim() + " - " + ETV_MaxPrice.getText().toString().trim();

        Log.d("ProviderRegistration", "First Name: " + firstName);
        Log.d("ProviderRegistration", "Last Name: " + lastName);
        Log.d("ProviderRegistration", "Phone Number: " + phoneNumber);
        Log.d("ProviderRegistration", "Email: " + email);
        Log.d("ProviderRegistration", "Password: " + password);
        Log.d("ProviderRegistration", "Address: " + address);
        Log.d("ProviderRegistration", "Age: " + age);
        Log.d("ProviderRegistration", "Price Range: " + priceRange);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String providerId = user.getUid();
                            Provider provider = new Provider(providerId, firstName, lastName, phoneNumber, email, address, age, priceRange);

                            // Set the role as "Provider" in the database
                            mDatabase.child("users").child(providerId).child("role").setValue("Provider");

                            // Save the provider details
                            mDatabase.child("Provider").child(providerId).setValue(provider)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Log.d("ProviderRegistration", "Provider registered successfully.");
                                            navigateToProviderMainActivity();
                                        } else {
                                            Log.e("ProviderRegistration", "Failed to register provider.", task1.getException());
                                            Toast.makeText(getContext(), "Failed to register provider. Please try again.", Toast.LENGTH_SHORT).show();
                                        }
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