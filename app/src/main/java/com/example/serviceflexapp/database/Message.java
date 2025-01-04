package com.example.serviceflexapp.database;

public class Message {
    private String providerId;
    private String message;
    private String messageId;
    private long timestamp;
    private boolean read;


    public Message() {

    }

    public Message(String providerId, String message, String messageId, long timestamp, boolean read) {
        this.providerId = providerId;
        this.message = message;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.read = read;
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

    public String getMessageId() {
        return messageId; // Add this method
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId; // Add this method
    }
}
