package com.settlein.backend.controller;

import com.settlein.backend.dto.UpdateFcmTokenRequest;
import com.settlein.backend.dto.UserUpdateRequest;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.service.MasterUserService;
import com.settlein.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class MasterUserController {

    @Autowired
    private MasterUserService masterUserService;

    @Autowired
    private JwtUtil jwtUtil;

    // ✅ Get current logged-in user info
    @GetMapping("/me")
    public ResponseEntity<MasterUser> me(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7); // Remove "Bearer "
        MasterUser user = masterUserService.getCurrentUser(jwt);
        return ResponseEntity.ok(user);
    }

    // ✅ Update user profile
    @PutMapping("/update")
    public ResponseEntity<MasterUser> updateProfile(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody UserUpdateRequest req) {
        String jwt = authHeader.substring(7); // Remove "Bearer "
        MasterUser updatedUser = masterUserService.updateProfile(jwt, req);
        return ResponseEntity.ok(updatedUser);
    }

    // ✅ Update FCM token using principal (email from Spring Security)
    @PutMapping("/fcm-token")
    public ResponseEntity<String> updateFcmToken(
            @RequestBody UpdateFcmTokenRequest request,
            Principal principal) {
        String email = principal.getName(); // Authenticated email
        MasterUser user = masterUserService.getByEmail(email);
        user.setFcmToken(request.getFcmToken());
        masterUserService.save(user);
        return ResponseEntity.ok("FCM Token updated");
    }
}
