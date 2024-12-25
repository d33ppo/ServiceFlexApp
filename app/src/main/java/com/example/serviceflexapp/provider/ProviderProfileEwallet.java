package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderProfileEwallet extends Fragment {

    public ProviderProfileEwallet() {
        super(R.layout.fragment_provider_profile_ewallet);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.IB_Previous2).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileEwallet_to_providerProfileFragment));
    }
}