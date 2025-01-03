package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;

import com.example.serviceflexapp.R;


public class ConsumerProfileFragment extends Fragment {

    public ConsumerProfileFragment() {
        super(R.layout.fragment_consumer_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.BTN_AccountInfo).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileFragment2_to_consumerProfileAccountInfo));
        view.findViewById(R.id.BTN_Ewallet).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileFragment2_to_consumerProfileEWallet));
        view.findViewById(R.id.BTN_UpdateWorkProfile).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileFragment2_to_consumerProfilePaymentHistory));
    }
}