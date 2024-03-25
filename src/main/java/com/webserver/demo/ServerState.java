package com.webserver.demo;

import java.util.ArrayList;

// This class is used to show the client a snapshot of the server's state
public class ServerState {

    private ServerStateType type;
    private int activeConnections;
    private ArrayList<String> messages;

    public ServerState() {
    }

    public ServerState(ServerStateType type, int activeConnections, ArrayList<String> messages) {
        this.type = type;
        this.activeConnections = activeConnections;
        this.messages = messages;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public int getActiveConnections() {
        return activeConnections;
    }

    public ServerStateType getType() {
        return type;
    }
    
    public void setType(ServerStateType type) {
        this.type = type;
    }

    public void setMessages(ArrayList<String> messages) {
        this.messages = messages;
    }
    
    public void setActiveConnections(int activeConnections) {
        this.activeConnections = activeConnections;
    }

}

