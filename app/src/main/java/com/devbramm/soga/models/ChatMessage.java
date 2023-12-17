package com.devbramm.soga.models;

public class ChatMessage {
    private String text;
    private String senderId;
    private String timestamp;
    private String status;

    public ChatMessage() {
    }

    public ChatMessage(String text, String senderId, String timestamp, String status) {
        this.text = text;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
