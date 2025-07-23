package com.settlein.backend.service;


import com.settlein.backend.dto.ChatMessageRequest;
import com.settlein.backend.entity.ChatRoom;
import com.settlein.backend.entity.Message;
import com.settlein.backend.repository.ChatRoomRepository;
import com.settlein.backend.repository.MessageRepository;
import com.settlein.backend.repository.ProductRepository;
import com.settlein.backend.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatRoomRepository chatRoomRepo;
    @Autowired
    private MessageRepository messageRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private PropertyRepository propertyRepo;

    public ChatRoom initiateChat(UUID interestedUserId, UUID ownerUserId, String type, UUID itemId) {
        Optional<ChatRoom> existingChat = chatRoomRepo
                .findByUser1IdAndUser2IdAndTypeAndItemId(interestedUserId, ownerUserId, type, itemId);

        if (existingChat.isPresent()) return existingChat.get();

        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setUser1Id(interestedUserId);
        chatRoom.setUser2Id(ownerUserId);
        chatRoom.setType(type);
        chatRoom.setItemId(itemId);
        chatRoom.setCreatedAt(LocalDateTime.now());

        chatRoom = chatRoomRepo.save(chatRoom);

        Message msg = new Message();
        msg.setChatRoomId(chatRoom.getId());
        msg.setSenderId(interestedUserId);
        msg.setContent("Hi, I'm interested in this " + type + ".");
        msg.setTimestamp(LocalDateTime.now());
        messageRepo.save(msg);
        return chatRoom;
    }

    public Object getItemDetails(String type, UUID itemId) {
        if ("PRODUCT".equalsIgnoreCase(type)) {
            return productRepo.findById(itemId).orElse(null);
        } else if ("PROPERTY".equalsIgnoreCase(type)) {
            return propertyRepo.findById(itemId).orElse(null);
        }
        return null;
    }

    public List<Message> getMessages(UUID chatRoomId) {
        return messageRepo.findAll()
                .stream()
                .filter(msg -> msg.getChatRoomId().equals(chatRoomId))
                .collect(Collectors.toList());
    }

    public Message saveMessage(ChatMessageRequest request) {
        Message message = new Message();
        message.setChatRoomId(request.getChatRoomId());
        message.setSenderId(request.getSenderId());
        message.setContent(request.getContent());
        message.setTimestamp(LocalDateTime.now());
        return messageRepo.save(message);
    }
}
