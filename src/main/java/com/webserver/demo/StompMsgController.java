package com.webserver.demo;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;
import java.util.ArrayList;

@Controller
public class StompMsgController {

    private int messageLimit = 10;
    private MessageList messageList = new MessageList();

    @MessageMapping("/message")
    @SendTo("/topic/chatroom")
    public ServerState updateChatroom(ChatMessage message) throws Exception {

        ArrayList<String> limitedMessages = prepareMessages(message);

        ServerState serverStateView = new ServerState(
                ServerStateType.MESSAGES,
                WebSocketsEventsListener.activeConnections,
                limitedMessages
        );
        return serverStateView;
    }

    private ArrayList<String> prepareMessages(ChatMessage message) {
        messageList.addMessage(HtmlUtils.htmlEscape(message.getMessage()));
        ArrayList<String> messages = messageList.getMessages();

        ArrayList<String> limitedMessages = new ArrayList<String>();
        if (messages.size() > messageLimit) {
            for (int i = messages.size() - messageLimit; i < messages.size(); i++) {
                limitedMessages.add(messages.get(i));
            }
        } else {
            limitedMessages = messages;
        }
        return limitedMessages;
    }

    // TODO - Add way to add messages to a database
    // TODO - Add way to retrieve messages from a database
    //
    // TODO - Use an in-memory database to store messages instead of ArrayList
}
