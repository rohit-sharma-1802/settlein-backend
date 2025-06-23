package com.settlein.backend.repository;

import com.settlein.backend.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    List<Chat> findAllBySenderIdOrReceiverId(UUID senderId, UUID receiverId);
    List<Chat> findAllBySenderIdOrReceiverIdAndPostType(UUID senderId, UUID receiverId, String type);
}
