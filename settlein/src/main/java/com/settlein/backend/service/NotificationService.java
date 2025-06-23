package com.settlein.backend.service;

import com.settlein.NotificationType;
import com.settlein.backend.dto.NotificationResponse;
import com.settlein.backend.entity.Notification;
import com.settlein.backend.entity.MasterUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.settlein.backend.repository.NotificationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    private FcmService fcmService;

    public List<Notification> getUserNotifications(MasterUser user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }

    public void markAsRead(UUID id, MasterUser user) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("entity.entity.Notification not found"));
        if (!n.getUser().getId().equals(user.getId()))
            throw new RuntimeException("Access denied");

        n.setRead(true);
        notificationRepository.save(n);
    }

    public void markAllAsRead(MasterUser user) {
        List<Notification> list = notificationRepository.findByUserOrderByCreatedAtDesc(user);
        for (Notification n : list) {
            n.setRead(true);
        }
        notificationRepository.saveAll(list);
    }

    public List<NotificationResponse> getUserNotificationResponses(MasterUser user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(n -> new NotificationResponse(
                        n.getId(),
                        n.getMessage(),
                        n.isRead(),
                        n.getType(),
                        n.getCreatedAt()
                ))
                .toList();
    }

    public void sendNotification(MasterUser user, String message, NotificationType type) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setType(type);
        notificationRepository.save(notification);

        // Push notification
        if (user.getFcmToken() != null) {

            fcmService.sendPushNotification(
                    user.getFcmToken(),
                    "SettleIn",
                    message
            );
        }
    }
}