package com.settlein.backend.entity;

import com.settlein.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Notification {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private MasterUser user;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private boolean read = false;

    private LocalDateTime createdAt = LocalDateTime.now();
}