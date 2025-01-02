package com.example.serviceflexapp.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.serviceflexapp.R;

public class ProviderRegistrationPage1Fragment extends Fragment {

    private EditText ETV_FirstName, ETV_LastName,ETV_PhoneNumber,  ETV_Email, ETV_Password, ETV_Address;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_registration_page1, container, false);

        ETV_FirstName = view.findViewById(R.id.ETV_FirstName);
        ETV_LastName = view.findViewById(R.id.ETV_LastName);
        ETV_Email = view.findViewById(R.id.ETV_Email);
        ETV_Password = view.findViewById(R.id.ETV_Password);
        ETV_Address = view.findViewById(R.id.ETV_Address);



        view.findViewById(R.id.Button_Next).setOnClickListener(v -> {
            //Collect data and pass to the next fragment
            Bundle bundle = new Bundle();
            bundle.putString("firstName", ETV_FirstName.getText().toString().trim());
            bundle.putString("lastName", ETV_LastName.getText().toString().trim());
            bundle.putString("phoneNumber", ETV_PhoneNumber.getText().toString().trim());
            bundle.putString("email", ETV_Email.getText().toString().trim());
            bundle.putString("password", ETV_Password.getText().toString().trim());
            bundle.putString("address", ETV_Address.getText().toString().trim());
            // Navigate to Fragment 2 (if needed)
            ((ProviderRegistrationActivity) requireActivity()).loadFragment(new ProviderRegistrationPage2Fragment());
        });

        return view;
    }
}