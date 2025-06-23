package com.settlein.backend.repository;

import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserOrderByCreatedAtDesc(
            MasterUser user);
}