package com.example.serviceflexapp.consumer;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.serviceflexapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsumerBookingsFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsumerBookingsFragment3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consumer_bookings3, container, false);
    private RadioButton rbEWallet, rbCreditDebitCard, rbOnlineBanking, rbGPay, rbCash;
    private Button finishButton;

    public ConsumerBookingsFragment3() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize UI components
        rbEWallet = view.findViewById(R.id.RB_EWallet);
        rbCreditDebitCard = view.findViewById(R.id.RB_CreditDebitCard);
        rbOnlineBanking = view.findViewById(R.id.RB_EOnlineBanking);
        rbGPay = view.findViewById(R.id.RB_GPay);
        rbCash = view.findViewById(R.id.RB_Cash);
        finishButton = view.findViewById(R.id.BTN_Finish);

        // Handle the Finish button click
        finishButton.setOnClickListener(v -> {
            if (rbEWallet.isChecked()) {
                proceedToPayment("E-Wallet");
            } else if (rbCreditDebitCard.isChecked()) {
                proceedToPayment("Credit/Debit Card");
            } else if (rbOnlineBanking.isChecked()) {
                proceedToPayment("Online Banking");
            } else if (rbGPay.isChecked()) {
                proceedToPayment("Google Pay");
            } else if (rbCash.isChecked()) {
                proceedToPayment("Cash");
            } else {
                // If no payment method is selected
                Toast.makeText(getContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void proceedToPayment(String paymentMethod) {
        // You can handle the payment UI transition based on the payment method
        // Here, we just show a Toast message for simplicity

        Toast.makeText(getContext(), "Proceeding with " + paymentMethod, Toast.LENGTH_SHORT).show();

        // Here, you would replace the Toast with your actual payment flow, such as:
        // - Navigate to a payment gateway
        // - Open a corresponding activity/fragment for the selected payment type
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button finishButton = view.findViewById(R.id.BTN_Finish);
        finishButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.action_consumerBookingsFragment3_to_consumerBookingsFragment1);
        });

        ImageButton previousButton = view.findViewById(R.id.IB_Previous5);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }
}