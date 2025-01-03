package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.serviceflexapp.R;

public class ConsumerProfileAccountInfo extends Fragment {

    public ConsumerProfileAccountInfo() {
        super(R.layout.fragment_consumer_profile_account_info);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        view.findViewById(R.id.IB_Previous).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileAccountInfo_to_consumerProfileFragment2));
    }
}