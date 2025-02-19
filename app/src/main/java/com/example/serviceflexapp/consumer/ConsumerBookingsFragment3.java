package com.example.serviceflexapp.consumer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class ConsumerBookingsFragment3 extends Fragment {

    private RadioButton rbPaypal, rbCreditDebitCard, rbOnlineBanking, rbEWallet, rbCash;
    private Button finishButton;

    // Initialize Firestore
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    //paypal
    private static final int PAYPAL_REQUEST_CODE = 123;
    private static PayPalConfiguration config;
    private Button paypalButton;
    private String paymentAmount = "10.00";

    static {
        config = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                // Get your Client ID from PayPal Developer Dashboard
                .clientId("AV2H5YEcD_MK41a698DIim73Sp-DmUzf7zH3Nf8ZwWKY82z7EbWpINScHQwXNnNTPI5MbUfWFr8DkXTs")
                .merchantName("ServFlex")  // Add your store name
                .acceptCreditCards(true)
                // Removes address requirement for faster checkout
                .rememberUser(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consumer_bookings3, container, false);

        // Find and setup PayPal button
        rbPaypal = view.findViewById(R.id.RB_PayPal); // button ID
        rbPaypal.setOnClickListener(v -> processPayment());
        return view;
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
        rbPaypal = view.findViewById(R.id.RB_PayPal);
        rbCreditDebitCard = view.findViewById(R.id.RB_CreditDebitCard);
        rbOnlineBanking = view.findViewById(R.id.RB_OnlineBanking);
        rbEWallet = view.findViewById(R.id.RB_EWallet);
        rbCash = view.findViewById(R.id.RB_Cash);
        finishButton = view.findViewById(R.id.BTN_Finish);

        // Handle the Finish button click
        finishButton.setOnClickListener(v -> {
            if (rbPaypal.isChecked()) {
                proceedToPayment("E-Wallet", providerId);
            } else if (rbCreditDebitCard.isChecked()) {
                proceedToPayment("Credit/Debit Card", providerId);
            } else if (rbOnlineBanking.isChecked()) {
                proceedToPayment("Online Banking", providerId);
            } else if (rbEWallet.isChecked()) {
                proceedToPayment("E Wallet", providerId);
            } else if (rbCash.isChecked()) {
                proceedToPayment("Cash", providerId);
            } else {
                // If no payment method is selected
                Toast.makeText(getContext(), "Please select a payment method", Toast.LENGTH_SHORT).show();
            }

            uploadAppointment(getArguments());
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

    private void uploadAppointment(Bundle bundle) {
        if (bundle != null) {
            // Retrieve data from the bundle
            String providerId = bundle.getString("providerId");
            String consumerId = bundle.getString("consumerId"); // Assuming you have this in the bundle
            String selectedDate = bundle.getString("date"); //
            String selectedTime = bundle.getString("time");

            Log.d("Bundle received","Bundle: " + bundle);
            // Create a map for the appointment data
            Map<String, Object> appointmentData = new HashMap<>();
            appointmentData.put("bookingDate", selectedDate); // Store the formatted date
            appointmentData.put("bookingTime", selectedTime); // Store the time
            appointmentData.put("consumerId", consumerId); // Store the consumer ID
            appointmentData.put("isCompleted", false); // Store the completion status

            // Upload the appointment data to Firestore
            firestore.collection("providers")
                    .document(providerId)
                    .collection("appointment")
                    .document()
                    .set(appointmentData)
                    .addOnSuccessListener(documentReference -> {
                        // Appointment uploaded successfully
                        Toast.makeText(getContext(), "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                    });

            String category = bundle.getString("category");
            Log.d("Bundle received","Bundle: " + bundle);
            // Create a map for the appointment data
            Map<String, Object> appointmentDataConsumers = new HashMap<>();
            appointmentDataConsumers.put("bookingDate", selectedDate); // Store the formatted date
            appointmentDataConsumers.put("bookingTime", selectedTime); // Store the time
            appointmentDataConsumers.put("providerId", providerId); // Store the consumer ID
            appointmentDataConsumers.put("isCompleted", false); // Store the completion status
            appointmentDataConsumers.put("category", category); // Store the consumer ID


            // Upload the appointment data to Firestore consumers collection
            firestore.collection("consumers")
                    .document(consumerId)
                    .collection("appointment")
                    .document()
                    .set(appointmentDataConsumers)
                    .addOnSuccessListener(documentReference -> {
                        // Appointment uploaded successfully
                        Toast.makeText(getContext(), "Appointment booked successfully!", Toast.LENGTH_SHORT).show();
                    });
        }
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

    //paypallll
    private void processPayment() {
        // Show loading dialog
        ProgressDialog dialog = new ProgressDialog(requireContext());
        dialog.setMessage("Processing Payment...");
        dialog.show();

        // Create payment
        PayPalPayment payment = new PayPalPayment(
                new BigDecimal(paymentAmount),
                "USD",
                "Test Payment", // Description of payment
                PayPalPayment.PAYMENT_INTENT_SALE
        );

        // Start PayPal payment activity
        Intent intent = new Intent(requireActivity(), PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        // Dismiss loading dialog
        dialog.dismiss();

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(
                        PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null) {
                    try {
                        // Getting payment details
                        String paymentDetails = confirmation.toJSONObject().toString(4);
                        JSONObject jsonDetails = new JSONObject(paymentDetails);

                        // Process successful payment
                        handleSuccessfulPayment(jsonDetails);

                    } catch (JSONException e) {
                        Log.e("PayPal", "JSON Exception: ", e);
                        showError("Payment Failed: " + e.getMessage());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                showMessage("Payment Cancelled", "You cancelled the payment");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                showError("Invalid Payment: Please try again");
            }
        }
    }

    private void handleSuccessfulPayment(JSONObject paymentDetails) {
        try {
            JSONObject response = paymentDetails.getJSONObject("response");
            String paymentId = response.getString("id");
            String status = response.getString("state");

            StringBuilder message = new StringBuilder();
            message.append("Payment Status: ").append(status)
                    .append("\nPayment ID: ").append(paymentId)
                    .append("\nAmount: $").append(paymentAmount);

            // Show success dialog
            new AlertDialog.Builder(requireContext())
                    .setTitle("Payment Successful")
                    .setMessage(message.toString())
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Add your post-payment logic here
                        // For example:
                        // - Update your database
                        // - Navigate to confirmation screen
                        // - Update UI elements
                        NavController navController = Navigation.findNavController(requireView());
                        navController.navigate(R.id.action_consumerBookingsFragment3_to_consumerHomeFragment);
                    })
                    .show();

            // Optional: Save payment details
            savePaymentDetails(paymentId, status, paymentAmount);

        } catch (JSONException e) {
            Log.e("PayPal", "Error parsing payment details", e);
            showError("Error processing payment details");
        }
    }

    private void savePaymentDetails(String paymentId, String status, String amount) {
        // Add your code to save payment details
        // For example, to SharedPreferences or your database
        SharedPreferences prefs = requireActivity().getSharedPreferences(
                "PaymentDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("last_payment_id", paymentId);
        editor.putString("last_payment_status", status);
        editor.putString("last_payment_amount", amount);
        editor.putLong("payment_time", System.currentTimeMillis());
        editor.apply();
    }

    private void showError(String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    private void showMessage(String title, String message) {
        new AlertDialog.Builder(requireContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
