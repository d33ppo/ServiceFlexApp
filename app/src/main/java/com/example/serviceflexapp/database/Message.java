package com.example.serviceflexapp.database;

public class Message {
    private String providerId;
    private String message;
    private long timestamp;
    private boolean read;
    private String id;
    private String messageId;

    public Message() {
        // Empty constructor needed for Firestore
    }

    public Message(String providerId, String message, long timestamp, boolean read, String messageId) {
        this.providerId = providerId;
        this.message = message;
        this.timestamp = timestamp;
        this.read = read;
        this.messageId = messageId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getId() {
        return messageId; // Add this method
    }

    public void setId(String messageId) {
        this.id = id; // Add this method
    }
}
