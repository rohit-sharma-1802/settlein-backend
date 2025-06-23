package com.settlein.backend.service;


import com.settlein.backend.dto.ChatMessageRequest;
import com.settlein.backend.dto.ChatStartRequest;
import com.settlein.backend.entity.Chat;
import com.settlein.backend.entity.ChatMessage;
import com.settlein.backend.entity.MasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.settlein.backend.repository.ChatMessageRepository;
import com.settlein.backend.repository.ChatRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ChatService {
    @Autowired
    private ChatRepository chatRepo;
    @Autowired private ChatMessageRepository messageRepo;
    @Autowired private MasterUserService masterUserService;

    public Chat start(String jwt, ChatStartRequest req) {
        MasterUser sender = masterUserService.getCurrentUser(jwt);

        // Mock: assume receiver is owner of post
        UUID receiverId = UUID.randomUUID(); // Replace with real post owner ID

        Chat chat = new Chat();
        chat.setPostId(req.getPostId());
        chat.setPostType(req.getPostType());
        chat.setSenderId(sender.getId());
        chat.setReceiverId(receiverId);
        return chatRepo.save(chat);
    }

    public ChatMessage send(String jwt, ChatMessageRequest req) {
        MasterUser sender = masterUserService.getCurrentUser(jwt);
        ChatMessage msg = new ChatMessage();
        msg.setChatId(req.getChatId());
        msg.setMessage(req.getMessage());
        msg.setSenderId(sender.getId());
        return messageRepo.save(msg);
    }

    public List<Chat> getAllChats(String jwt, String type) {
        MasterUser user = masterUserService.getCurrentUser(jwt);
        if (type != null) {
            return chatRepo.findAllBySenderIdOrReceiverIdAndPostType(user.getId(), user.getId(), type);
        }
        return chatRepo.findAllBySenderIdOrReceiverId(user.getId(), user.getId());
    }

    public List<ChatMessage> getMessages(UUID chatId) {
        return messageRepo.findAllByChatIdOrderBySentOnAsc(chatId);
    }
}