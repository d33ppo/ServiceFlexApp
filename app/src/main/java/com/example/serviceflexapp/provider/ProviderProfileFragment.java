package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderProfileFragment extends Fragment {

    public ProviderProfileFragment() {
        super(R.layout.fragment_provider_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.BTN_AccountInfo).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileAccountInfo));
        view.findViewById(R.id.BTN_Ewallet).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileEwallet));
        view.findViewById(R.id.BTN_Transfer).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileTransferToBankAccount));
        view.findViewById(R.id.BTN_UpdateWorkProfile).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileFragment_to_providerProfileUpdateWorkProfile));
    }
}