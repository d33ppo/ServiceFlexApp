package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.example.serviceflexapp.R;

public class ProviderProfileTransferToBankAccount extends Fragment {

    public ProviderProfileTransferToBankAccount() {
        super(R.layout.fragment_provider_profile_transfer_to_bank_account);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.btnReturn).setOnClickListener(v ->
                navController.navigate(R.id.action_providerProfileTransferToBankAccount_to_providerProfileFragment));
    }
}