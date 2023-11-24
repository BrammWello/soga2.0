package com.devbramm.soga.models;

public class ChatItemList {
    private String chatName;
    private String chatText;
    private String chatTime;

    public ChatItemList() {
    }

    public ChatItemList(String chatName, String chatText, String chatTime) {
        this.chatName = chatName;
        this.chatText = chatText;
        this.chatTime = chatTime;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatText() {
        return chatText;
    }

    public void setChatText(String chatText) {
        this.chatText = chatText;
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime;
    }
}