package com.settlein.backend.dto;

import com.settlein.NotificationType;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class NotificationResponse {
    private UUID id;
    private String message;
    private boolean read;
    private NotificationType type;
    private LocalDateTime createdAt;
}