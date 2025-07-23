package com.settlein.backend.controller;

import com.settlein.backend.dto.ChatInitiationRequest;
import com.settlein.backend.entity.ChatRoom;
import com.settlein.backend.entity.Message;
import com.settlein.backend.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/initiate")
    public ResponseEntity<Map<String, Object>> initiateChat(@RequestBody ChatInitiationRequest req) {
        ChatRoom chatRoom = chatService.initiateChat(
                req.getInterestedUserId(),
                req.getOwnerUserId(),
                req.getType(),
                req.getItemId()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("chatRoom", chatRoom);
        response.put("messages", chatService.getMessages(chatRoom.getId()));
        response.put("item", chatService.getItemDetails(req.getType(), req.getItemId()));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/history/{chatRoomId}")
    public ResponseEntity<List<Message>> getChatHistory(@PathVariable("chatRoomId") UUID chatRoomId) {
        List<Message> messages = chatService.getMessages(chatRoomId);
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/item-details")
    public ResponseEntity<Object> getItemDetails(
            @RequestParam("itemId") UUID itemId,
            @RequestParam("type") String type
    ) {
        Object itemDetails = chatService.getItemDetails(type, itemId);
        if (itemDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemDetails);
    }
}