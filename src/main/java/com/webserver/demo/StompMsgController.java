package com.webserver.demo;

import org.springframework.stereotype.Controller;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.util.HtmlUtils;

@Controller
public class StompMsgController {

    @MessageMapping("/message")
    @SendTo("/topic/chatroom")
    public MessageReceiver testMessage(ChatMessage message) throws Exception {
        return new MessageReceiver(HtmlUtils.htmlEscape(message.getMessage()));
    }
}
