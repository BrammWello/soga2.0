package com.devbramm.soga.models;

public class ChatMessage {
    private String text;
    private String senderId;

    public ChatMessage() {
    }

    public ChatMessage(String text, String senderId) {
        this.text = text;
        this.senderId = senderId;
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
}
