package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderProfileUpdateWorkProfile extends Fragment {

    public ProviderProfileUpdateWorkProfile() {
        super(R.layout.fragment_provider_profile_update_work_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.IB_Previous3).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileUpdateWorkProfile_to_providerProfileFragment));
    }
}