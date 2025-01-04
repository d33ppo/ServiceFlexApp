package com.example.serviceflexapp.database;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    private FirebaseFirestore firestore;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
        firestore = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_messages, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.messageText.setText(message.getMessage());

        // Set up delete button click listener
        holder.deleteButton.setOnClickListener(v -> {
            String providerId = FirebaseAuth.getInstance().getUid();
            String messageId = message.getMessageId(); // Assuming Message object has an ID field
            deleteMessageFromFirestore(providerId, messageId, position);
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private void deleteMessageFromFirestore(String providerId, String messageId, int position) {
        if (providerId == null || messageId == null) {
            Log.e("DeleteError", "providerId or messageId is null");
            return; // Exit early if IDs are null
        }

        DocumentReference messageDocRef = firestore.collection("providers")
                .document(providerId)
                .collection("messages")
                .document(messageId);

        messageDocRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("DeleteSuccess", "Message deleted successfully.");
                    // You can also update the adapter or notify data changes here
                })
                .addOnFailureListener(e -> {
                    Log.e("DeleteError", "Failed to delete message", e);
                });
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
        notifyDataSetChanged();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView senderText;
        Button deleteButton;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.TV_ViewContentProvider);
            senderText = itemView.findViewById(R.id.TV_SenderProvider);// Replace with your actual TextView ID
            deleteButton = itemView.findViewById(R.id.BTN_Delete);
        }
    }
}


