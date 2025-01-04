package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Message;
import com.example.serviceflexapp.database.MessageAdapter;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ProviderInboxFragment extends Fragment {

    private String providerId; // Variable to store the provider ID

    public ProviderInboxFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            providerId = getArguments().getString("providerId"); // Retrieve providerId from the arguments
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_provider_inbox, container, false);

        if (providerId == null) {
            // Handle error case where providerId is not passed
            return view;
        }

        RecyclerView recyclerView = view.findViewById(R.id.RV_Inbox);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Create a list to hold messages and set up the adapter
        List<Message> messages = new ArrayList<>();
        MessageAdapter adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);

        // Initialize Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Query Firestore to get messages for the specific provider
        Query messagesQuery = db.collection("providers")
                .document(providerId) // Access the document for this specific provider
                .collection("messages") // Access the messages sub-collection
                .orderBy("timestamp");

        // Listen for real-time updates to the "messages" collection
        messagesQuery.addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                return;
            }

            if (snapshots != null) {
                messages.clear();
                for (DocumentSnapshot snapshot : snapshots) {
                    Message message = snapshot.toObject(Message.class);
                    if (message != null) {
                        message.setId(snapshot.getId()); // Set the Firestore document ID
                        messages.add(message);
                    } else {
                        Log.e("ProviderInboxFragment", "Message snapshot is null.");
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
