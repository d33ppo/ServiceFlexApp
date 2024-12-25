package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderProfileAccountInfo extends Fragment {

    public ProviderProfileAccountInfo() {
        super(R.layout.fragment_provider_profile_account_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.IB_Previous).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileAccountInfo_to_providerProfileFragment));
    }
}