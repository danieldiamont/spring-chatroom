package com.webserver.demo;

import java.util.ArrayList;

public class MessageList {
    private ArrayList<String> messages = new ArrayList<String>();

    public MessageList() {
    }

    public MessageList(ArrayList<String> messages) {
        this.messages = messages;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
