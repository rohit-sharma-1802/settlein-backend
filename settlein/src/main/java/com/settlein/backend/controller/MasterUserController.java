package com.settlein.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.settlein.backend.dto.CompleteProfileRequest;
import com.settlein.backend.dto.UpdateFcmTokenRequest;
import com.settlein.backend.dto.UserUpdateRequest;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.service.MasterUserService;
import com.settlein.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class MasterUserController {

    @Autowired
    private MasterUserService masterUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ObjectMapper objectMapper;

    // ✅ Get current logged-in user info
    @GetMapping("/me")
    public ResponseEntity<MasterUser> me(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7); // Remove "Bearer "
        MasterUser user = masterUserService.getCurrentUser(jwt);
        return ResponseEntity.ok(user);
    }

    // ✅ Update user profile
    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(
            @RequestPart(name = "data", required = false) String request,
            @RequestPart(name = "image", required = false) MultipartFile profileImage,
            @RequestHeader("Authorization") String authHeader) {
        try {
            UserUpdateRequest userUpdateRequest = objectMapper.readValue(request, UserUpdateRequest.class);
            return masterUserService.updateProfile(authHeader, userUpdateRequest, profileImage);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
