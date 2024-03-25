package com.webserver.demo;

import org.springframework.stereotype.Component;
import org.springframework.context.event.EventListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.webserver.demo.ServerStateType;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;

@Component
public class WebSocketsEventsListener {

    public static int activeConnections = 0;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public WebSocketsEventsListener(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }
    
    @EventListener
    public void handleSessionConnected(SessionConnectEvent event) {
        activeConnections++;
        ServerState serverStateView = prepareWebSocketEventStatusUpdate("A user has connected.");
        simpMessagingTemplate.convertAndSend("/topic/chatroom", serverStateView);
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
        activeConnections--;
        ServerState serverStateView = prepareWebSocketEventStatusUpdate("A user has disconnected.");
        simpMessagingTemplate.convertAndSend("/topic/chatroom", serverStateView);
    }

    private ServerState prepareWebSocketEventStatusUpdate(String message) {
        ArrayList<String> messages = new ArrayList<String>();
        messages.add(message);
        ServerState serverStateView = new ServerState(
                ServerStateType.STATUS,
                WebSocketsEventsListener.activeConnections,
                messages
        );
        return serverStateView;
    }
}
