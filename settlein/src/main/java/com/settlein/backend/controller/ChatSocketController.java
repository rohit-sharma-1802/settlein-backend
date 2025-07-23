package com.settlein.backend.controller;

import com.settlein.backend.dto.ChatMessageRequest;
import com.settlein.backend.entity.Message;
import com.settlein.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;


public class ChatSocketController {
    @Autowired
    private ChatService chatService;

    @MessageMapping("/chat.send") // maps to /app/chat.send
    @SendTo("/topic/chat/{chatRoomId}") // clients subscribe to /topic/chat/{chatRoomId}
    public Message send(ChatMessageRequest request) {
        Message savedMessage = chatService.saveMessage(request);
        return savedMessage;
    }
}
