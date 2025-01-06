package com.example.serviceflexapp.database;

public class Message {
    private String messageId;
    private String message;
    private long timestamp;
    private boolean read;
    private String consumerName;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(Message.class)
    }
    public String getConsumerName() {
        return consumerName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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
}