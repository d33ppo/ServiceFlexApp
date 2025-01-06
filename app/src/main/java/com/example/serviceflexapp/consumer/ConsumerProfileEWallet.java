package com.example.serviceflexapp.consumer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.TransactionAdapter;
import com.example.serviceflexapp.database.TransactionHistory;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsumerProfileEWallet extends Fragment {

    private Button addFundsButton;
    private EditText insertAmountEditText;
    private RecyclerView transactionRecyclerView;
    private TransactionAdapter transactionAdapter;
    private List<TransactionHistory> transactionList = new ArrayList<>();
    private TextView walletBalanceTextView;

    public ConsumerProfileEWallet() {
        super(R.layout.fragment_consumer_profile_e_wallet);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionRecyclerView = view.findViewById(R.id.RV_transaction_history);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        transactionAdapter = new TransactionAdapter(transactionList);
        transactionRecyclerView.setAdapter(transactionAdapter);

        walletBalanceTextView = view.findViewById(R.id.TV_wallet_balance);

        fetchTransactionHistory();
        fetchTotalBalance();

        addFundsButton = view.findViewById(R.id.BTN_add_funds);
        insertAmountEditText = view.findViewById(R.id.ET_InsertAmount);

        addFundsButton.setOnClickListener(v -> {
            String consumerId = getCurrentConsumerId();
            String amount = insertAmountEditText.getText().toString().trim();

            if (amount.isEmpty()) {
                Toast.makeText(getContext(), "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            addFundsToAccount(consumerId, amount);
        });

        NavController navController = Navigation.findNavController(view);
        view.findViewById(R.id.IB_Previous2).setOnClickListener(v ->
                navController.navigate(R.id.action_consumerProfileEWallet_to_consumerProfileFragment2)
        );
    }

    private String getCurrentConsumerId() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    private void addFundsToAccount(String consumerId, String amount) {
        sendAddFundsMessageToConsumer(consumerId, amount);
        sendMessageToTransactionHistory(consumerId, amount);
    }

    private void sendMessageToTransactionHistory(String consumerId, String amount) {
        Map<String, Object> transactionData = new HashMap<>();
        transactionData.put("amount", amount);
        transactionData.put("timestamp", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("consumers")
                .document(consumerId)
                .collection("transactionHistory")
                .add(new TransactionHistory(amount, System.currentTimeMillis()))
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Transaction history updated", Toast.LENGTH_SHORT).show();
                        fetchTransactionHistory(); // Fetch the updated transaction history
                        fetchTotalBalance();
                    } else {
                        Toast.makeText(getContext(), "Failed to update transaction history", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendAddFundsMessageToConsumer(String consumerId, String amount) {
        String message = "You have added funds to your account: " + amount;

        Map<String, Object> messageData = new HashMap<>();
        messageData.put("message", message);
        messageData.put("timestamp", System.currentTimeMillis());
        messageData.put("read", false);
        messageData.put("amount", amount);

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
                        Map<String, Object> updatedMessageData = new HashMap<>(messageData);
                        updatedMessageData.put("messageId", messageId);
                        db.collection("consumers")
                                .document(consumerId)
                                .collection("messages")
                                .document(messageId)
                                .set(updatedMessageData, SetOptions.merge())
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

    private void fetchTransactionHistory() {
        String consumerId = getCurrentConsumerId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("consumers")
                .document(consumerId)
                .collection("transactionHistory")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        transactionList.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TransactionHistory transaction = document.toObject(TransactionHistory.class);
                            transactionList.add(transaction);
                        }
                        transactionAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch transaction history", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void fetchTotalBalance() {
        String consumerId = getCurrentConsumerId();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("consumers")
                .document(consumerId)
                .collection("transactionHistory")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        double totalBalance = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            TransactionHistory transaction = document.toObject(TransactionHistory.class);
                            totalBalance += Double.parseDouble(transaction.getAmount());
                        }
                        walletBalanceTextView.setText("RM " + totalBalance);
                    } else {
                        Toast.makeText(getContext(), "Failed to fetch total balance", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}