package com.settlein.backend.controller;

import com.settlein.backend.dto.ChatMessageRequest;
import com.settlein.backend.dto.ChatStartRequest;
import com.settlein.backend.entity.Chat;
import com.settlein.backend.entity.ChatMessage;
import com.settlein.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @PostMapping("/start")
    public Chat start(@RequestHeader("Authorization") String auth,
                      @RequestBody ChatStartRequest req) {
        return chatService.start(auth.substring(7), req);
    }

    @PostMapping("/send")
    public ChatMessage send(@RequestHeader("Authorization") String auth,
                            @RequestBody ChatMessageRequest req) {
        return chatService.send(auth.substring(7), req);
    }

    @GetMapping
    public List<Chat> getChats(@RequestHeader("Authorization") String auth,
                               @RequestParam(required = false) String type) {
        return chatService.getAllChats(auth.substring(7), type);
    }

    @GetMapping("/{chatId}/messages")
    public List<ChatMessage> getMessages(@PathVariable UUID chatId) {
        return chatService.getMessages(chatId);
    }

    @MessageMapping("/chat/send")
    @SendTo("/topic/chat/{chatId}")
    public ChatMessage sendMessage(@DestinationVariable String chatId, ChatMessage message) {
        return message; // Save to DB if needed
    }
}