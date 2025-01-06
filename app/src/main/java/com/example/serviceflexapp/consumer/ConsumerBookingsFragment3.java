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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ConsumerBookingsFragment3 extends Fragment {

    private RadioButton rbEWallet, rbCreditDebitCard, rbOnlineBanking, rbGPay, rbCash;
    private Button finishButton;

    public ConsumerBookingsFragment3() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_consumer_bookings3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Retrieve providerId from arguments
        String providerId = getArguments() != null ? getArguments().getString("providerId") : null;
        if (providerId == null) {
            Toast.makeText(getContext(), "Provider ID is missing.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser consumerId = FirebaseAuth.getInstance().getCurrentUser();

        // Initialize UI components
        rbEWallet = view.findViewById(R.id.RB_EWallet);
        rbCreditDebitCard = view.findViewById(R.id.RB_CreditDebitCard);
        rbOnlineBanking = view.findViewById(R.id.RB_OnlineBanking);
        rbGPay = view.findViewById(R.id.RB_GPay);
        rbCash = view.findViewById(R.id.RB_Cash);
        finishButton = view.findViewById(R.id.BTN_Finish);

        // Handle the Finish button click
        finishButton.setOnClickListener(v -> {
            if (rbEWallet.isChecked()) {
                proceedToPayment("E-Wallet", providerId);
            } else if (rbCreditDebitCard.isChecked()) {
                proceedToPayment("Credit/Debit Card", providerId);
            } else if (rbOnlineBanking.isChecked()) {
                proceedToPayment("Online Banking", providerId);
            } else if (rbGPay.isChecked()) {
                proceedToPayment("Google Pay", providerId);
            } else if (rbCash.isChecked()) {
                proceedToPayment("Cash", providerId);
            } else {
                // If no payment method is selected
                Toast.makeText(getContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle navigation to the previous fragment
        ImageButton previousButton = view.findViewById(R.id.IB_Previous5);
        previousButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.popBackStack();
        });
    }

    private void proceedToPayment(String paymentMethod, String providerId) {
        // Handle the payment process based on the selected payment method
        Toast.makeText(getContext(), "Proceeding with " + paymentMethod, Toast.LENGTH_SHORT).show();

        // Save the message to the provider's inbox
        saveMessageToProviderInbox(paymentMethod, providerId);

        // Navigate to the next fragment or start payment process here
        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.action_consumerBookingsFragment3_to_consumerHomeFragment);
    }

    private void saveMessageToProviderInbox(String paymentMethod, String providerId) {
        // Get the current logged-in consumer's ID
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String consumerId = currentUser != null ? currentUser.getUid() : null;

        if (consumerId == null) {
            // Handle the case when there is no logged-in user
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference consumerRef = FirebaseDatabase.getInstance()
                .getReference("Consumer")
                .child(consumerId);

        consumerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Fetch firstName and lastName
                    String firstName = snapshot.child("firstName").getValue(String.class);
                    String lastName = snapshot.child("lastName").getValue(String.class);

                    if (firstName == null || lastName == null) {
                        Toast.makeText(getContext(), "Consumer name is incomplete", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Combine firstName and lastName
                    String consumerName = firstName + " " + lastName;

                    // Message to be saved
                    String message = consumerName + " booked a service with payment method: " + paymentMethod;

                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    DocumentReference newMessageRef = db.collection("providers")
                            .document(providerId)
                            .collection("messages")
                            .document(); // Generates a unique document ID

                    String messageId = newMessageRef.getId();

                    // Data to be saved
                    Map<String, Object> messageData = new HashMap<>();
                    messageData.put("consumerName", consumerName);
                    messageData.put("consumerId", consumerId);
                    messageData.put("providerId", providerId);
                    messageData.put("messageId", messageId);
                    messageData.put("message", message);
                    messageData.put("timestamp", System.currentTimeMillis());

                    // Save message to Firestore
                    newMessageRef.set(messageData)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getContext(), "Message sent to provider's inbox", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Failed to send message to provider's inbox", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(getContext(), "Consumer data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}