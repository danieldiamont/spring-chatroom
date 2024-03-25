package com.webserver.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.webserver.demo.ServerStateType;

@Configuration
@EnableScheduling
public class ChatroomStatusUpdateService {

    SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public ChatroomStatusUpdateService(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Scheduled(fixedRate = 2000, initialDelay = 2000)
    public void sendStatusUpdate() {

        ServerState serverStateView = new ServerState(
                ServerStateType.STATUS,
                WebSocketsEventsListener.activeConnections,
                null
        );
        simpMessagingTemplate.convertAndSend("/topic/chatroom", serverStateView);
    }

}
