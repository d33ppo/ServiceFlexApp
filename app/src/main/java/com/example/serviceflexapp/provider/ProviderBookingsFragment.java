package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderBookingsFragment extends Fragment {

    public ProviderBookingsFragment() {
        super(R.layout.fragment_provider_bookings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.BTN_Upcoming).setOnClickListener(v ->
                navController.navigate(R.id.action_providerBookingsFragment_to_providerBookingsPage1Fragment));
        view.findViewById(R.id.BTN_Completed).setOnClickListener(v ->
                navController.navigate(R.id.action_providerBookingsFragment_to_providerBookingsPage2Fragment));
    }
}