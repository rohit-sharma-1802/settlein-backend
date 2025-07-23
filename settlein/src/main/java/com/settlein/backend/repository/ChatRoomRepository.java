package com.settlein.backend.repository;

import com.settlein.backend.entity.ChatRoom;
import com.settlein.backend.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID> {
    Optional<ChatRoom> findByUser1IdAndUser2IdAndTypeAndItemId(UUID user1Id, UUID user2Id, String type, UUID itemId);
}
