package com.example.serviceflexapp.database;

public class TransactionHistory {
    private String amount;
    private long timestamp;

    public TransactionHistory() {
        // Default constructor required for calls to DataSnapshot.getValue(Transaction.class)
    }

    public TransactionHistory(String amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
