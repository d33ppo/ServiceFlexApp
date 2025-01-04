package com.example.serviceflexapp.provider;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;
import com.example.serviceflexapp.database.Message;
import com.example.serviceflexapp.database.MessageAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProviderInboxFragment extends Fragment {
    private RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private MessageAdapter messageAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_provider_inbox, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.RV_Inbox);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firestore = FirebaseFirestore.getInstance();
        messageAdapter = new MessageAdapter(new ArrayList<>());
        recyclerView.setAdapter(messageAdapter);
        fetchMessages();
    }

    private void fetchMessages(){
        String providerId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        firestore.collection("providers")
                .document(providerId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        QuerySnapshot documentSnapshots = task.getResult();
                        if(documentSnapshots != null && !documentSnapshots.isEmpty()){
                            messageAdapter.setMessages(documentSnapshots.toObjects(Message.class));
                        }else{
                            Toast.makeText(getContext(), "No messages found.", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getContext(), "Failed to fetch messages.", Toast.LENGTH_SHORT).show();
                        Log.e("FirestoreError", "Error fetching messages: ", task.getException());
                    }
                });
    }
}
