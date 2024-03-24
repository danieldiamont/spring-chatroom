package com.webserver.demo;

public class MessageReceiver {

    private String content;

    public MessageReceiver() {
    }

    public MessageReceiver(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
