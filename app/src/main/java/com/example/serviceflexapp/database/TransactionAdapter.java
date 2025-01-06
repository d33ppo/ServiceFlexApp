package com.example.serviceflexapp.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.serviceflexapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<TransactionHistory> transactionHistoryList;

    public TransactionAdapter(List<TransactionHistory> transactionHistoryList) {
        this.transactionHistoryList = transactionHistoryList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_history, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionHistory transactionHistory = transactionHistoryList.get(position);
        holder.amountTextView.setText("RM " + transactionHistory.getAmount());
        holder.timestampTextView.setText(formatTimestamp(transactionHistory.getTimestamp()));
    }

    @Override
    public int getItemCount() {
        return transactionHistoryList.size();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView amountTextView;
        TextView timestampTextView;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTextView = itemView.findViewById(R.id.TV_ReloadHistory);
            timestampTextView = itemView.findViewById(R.id.TV_DateTime);
        }
    }

    private String formatTimestamp(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(new Date(timestamp));
    }
}
