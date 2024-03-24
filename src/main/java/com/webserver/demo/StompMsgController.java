package com.webserver.demo;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;
import java.util.ArrayList;

@Controller
public class StompMsgController {

    private ArrayList<String> messages = new ArrayList<String>();
    private int messageLimit = 10;

    @MessageMapping("/message")
    @SendTo("/topic/chatroom")
    public MessageList testMessage(ChatMessage message) throws Exception {

        messages.add(HtmlUtils.htmlEscape(message.getMessage()));

        ArrayList<String> limitedMessages = new ArrayList<String>();
        if (messages.size() > messageLimit) {
            for (int i = messages.size() - messageLimit; i < messages.size(); i++) {
                limitedMessages.add(messages.get(i));
            }
        } else {
            limitedMessages = messages;
        }
        return new MessageList(limitedMessages);
    }
}
