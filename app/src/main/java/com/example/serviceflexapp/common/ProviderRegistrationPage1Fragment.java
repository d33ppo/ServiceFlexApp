package com.example.serviceflexapp.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serviceflexapp.R;

public class ProviderRegistrationPage1Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_provider_registration_page1, container, false);

        view.findViewById(R.id.Button_Next).setOnClickListener(v -> {
            // Navigate to Fragment 2 (if needed)
            ((ProviderRegistrationActivity) requireActivity()).loadFragment(new ProviderRegistrationPage2Fragment());
        });

        return view;
    }
}