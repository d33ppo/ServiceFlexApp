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
    private String userRole;

    public MessageAdapter(List<Message> messages, String userRole) {
        this.messages = messages;
        this.userRole = userRole;
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

        holder.deleteButton.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getUid();
            String messageId = message.getMessageId();
            deleteMessageFromFirestore(userId, messageId, position);
        });
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    private void deleteMessageFromFirestore(String userId, String messageId, int position) {
        if (userId == null || messageId == null) {
            Log.e("DeleteError", "userId or messageId is null");
            return;
        }

        String collectionPath = userRole.equals("Provider") ? "providers" : "consumers";

        DocumentReference messageDocRef = firestore.collection(collectionPath)
                .document(userId)
                .collection("messages")
                .document(messageId);

        messageDocRef.delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("DeleteSuccess", "Message deleted successfully.");
                    messages.remove(position);
                    notifyItemRemoved(position);
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
        Button deleteButton;

        public MessageViewHolder(View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.TV_ViewContentProvider);
            deleteButton = itemView.findViewById(R.id.BTN_Delete);
        }
    }
}