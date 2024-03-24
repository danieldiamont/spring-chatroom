package com.webserver.demo;

import java.util.ArrayList;

public class MessageList {
    private ArrayList<String> messages;

    public MessageList() {
    }

    public MessageList(ArrayList<String> messages) {
        this.messages = messages;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }
}
