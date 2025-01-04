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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messages;
    private OnDeleteClickListener onDeleteClickListener;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
        this.onDeleteClickListener = onDeleteClickListener;
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
        holder.senderText.setText("Your Account");
        holder.messageText.setText(message.getMessage());

        // Set click listener for the delete button
        holder.deleteButton.setOnClickListener(v -> {
            // Get Firestore instance
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            String providerId = message.getProviderId(); // Get providerId
            String messageId = message.getId();

            // Ensure message ID and provider ID are valid
            if (message.getId() != null && message.getProviderId() != null) {
                // Delete the message from Firestore
                db.collection("providers")
                        .document(providerId)  // Access the provider document
                        .collection("messages")  // Messages sub-collection
                        .document(messageId)  // Delete the message by ID
                        .delete()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Remove the message from the list and notify the adapter
                                messages.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, messages.size());
                            } else {
                                Log.e("MessageAdapter", "Error deleting message", task.getException());
                            }
                        });
            } else {
                Log.e("MessageAdapter", "Message ID or Provider ID is null, cannot delete.");
            }
        });
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(String messageId);
    }

    @Override
    public int getItemCount() {
        return messages.size();
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


