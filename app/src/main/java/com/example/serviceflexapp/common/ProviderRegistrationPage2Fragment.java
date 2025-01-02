package com.example.serviceflexapp.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Provider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProviderRegistrationPage2Fragment extends Fragment {

    private EditText ETV_Age, ETV_MinPrice, ETV_MaxPrice;
    private Spinner SP_JobChoice;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_registration_page2, container, false);

        ETV_Age = view.findViewById(R.id.ETV_Age);
        ETV_MinPrice = view.findViewById(R.id.ETV_MinPrice);
        ETV_MaxPrice = view.findViewById(R.id.ETV_MaxPrice);
        SP_JobChoice = view.findViewById(R.id.SP_JobChoice);
        Button BTN_Create = view.findViewById(R.id.Btn_Create);

        // Initialize Firebase Database
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Set up the job choice spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.job_choices, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SP_JobChoice.setAdapter(adapter);

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
        String jobChoice = SP_JobChoice.getSelectedItem().toString();
        String priceRange = ETV_MinPrice.getText().toString().trim() + " - " + ETV_MaxPrice.getText().toString().trim();

        String providerId = mDatabase.push().getKey();
        Provider provider = new Provider(providerId, firstName, lastName, phoneNumber, email, password, address, age, jobChoice, priceRange);

        mDatabase.child("providers").child(providerId).setValue(provider)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ((ProviderRegistrationActivity) requireActivity()).navigateToHomeProvider();
                    } else {
                        Toast.makeText(getContext(), "Failed to register provider. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}