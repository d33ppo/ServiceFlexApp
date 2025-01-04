package com.example.serviceflexapp.consumer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.serviceflexapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ConsumerProfileEWallet extends Fragment {

    private Button addFundsButton;

    public ConsumerProfileEWallet() {
        super(R.layout.fragment_consumer_profile_e_wallet);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the Add Funds button
        addFundsButton = view.findViewById(R.id.BTN_add_funds);  // Replace with actual ID in your layout

        // Set click listener for the Add Funds button
        addFundsButton.setOnClickListener(v -> {
            String consumerId = getCurrentConsumerId();  // Get the current logged-in consumer's ID

            // Simulate adding funds process
            addFundsToAccount(consumerId);  // Add funds and send the message
        });

        // Handle the navigation to the previous screen
        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.IB_Previous2).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileEWallet_to_consumerProfileFragment2)
        );
    }

    // Method to get the current logged-in consumer's ID
    private String getCurrentConsumerId() {
        // Replace with the actual logic to get the current logged-in consumer's ID
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    // Method to simulate adding funds to the consumer's account
    private void addFundsToAccount(String consumerId) {
        // After adding funds, send a message to the consumer's inbox
        sendAddFundsMessageToConsumer(consumerId);
    }

    // Save message to consumer's inbox when they add funds
    private void sendAddFundsMessageToConsumer(String consumerId) {
        // Create the message content
        String message = "You have added funds to your account";

        // Create a map to save in Firestore
        Map<String, Object> messageData = new HashMap<>();
        messageData.put("message", message);
        messageData.put("timestamp", System.currentTimeMillis());
        messageData.put("read", false);  // Mark the message as unread initially

        // Get a reference to Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("consumers")
                .document(consumerId)
                .collection("messages")
                .add(messageData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String messageId = task.getResult().getId();  // Get the generated message ID
                        Toast.makeText(getContext(), "Funds added successfully, message sent to inbox", Toast.LENGTH_SHORT).show();
                        Log.d("ConsumerProfileEwallet", "Message sent with ID: " + messageId);
                        // Optionally, you can save the messageId in the Firestore document as part of the data
                        Map<String, Object> updatedMessageData = new HashMap<>(messageData);
                        updatedMessageData.put("messageId", messageId);  // Adding messageId to the map
                        // Update the document to include the messageId field if needed
                        db.collection("consumers")
                                .document(consumerId)
                                .collection("messages")
                                .document(messageId) // Access the newly created document by its ID
                                .set(updatedMessageData, SetOptions.merge()) // Merge the updated data
                                .addOnCompleteListener(updateTask -> {
                                    if (updateTask.isSuccessful()) {
                                        Log.d("ConsumerProfileEwallet", "MessageId added to message.");
                                    } else {
                                        Log.e("ConsumerProfileEwallet", "Failed to update message with messageId");
                                    }
                                });
                    } else {
                        Toast.makeText(getContext(), "Failed to send message to inbox", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}