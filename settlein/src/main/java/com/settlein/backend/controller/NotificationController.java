package com.settlein.backend.controller;

import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.service.NotificationService;
import com.settlein.backend.service.MasterUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final MasterUserService masterUserService;

    @GetMapping
    public ResponseEntity<?> getMyNotifications(Principal principal) {
        MasterUser user = masterUserService.getByEmail(principal.getName());
        return ResponseEntity.ok(notificationService.getUserNotifications(user));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<?> markAsRead(@PathVariable UUID id, Principal principal) {
        MasterUser user = masterUserService.getByEmail(principal.getName());
        notificationService.markAsRead(id, user);
        return ResponseEntity.ok("Marked as read");
    }

    @PutMapping("/read-all")
    public ResponseEntity<?> markAllAsRead(Principal principal) {
        MasterUser user = masterUserService.getByEmail(principal.getName());
        notificationService.markAllAsRead(user);
        return ResponseEntity.ok("All notifications marked as read");
    }

}
