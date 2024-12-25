package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderBookingsPage1Fragment extends Fragment {

    public ProviderBookingsPage1Fragment() {
        super(R.layout.fragment_provider_bookings_page1);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.BTN_Completed).setOnClickListener(v ->
                navController.navigate(R.id.action_providerBookingsPage1Fragment_to_providerBookingsPage2Fragment));
    }
}