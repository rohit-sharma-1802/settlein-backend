package com.settlein.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.settlein.backend.dto.CompleteProfileRequest;
import com.settlein.backend.dto.LoginRequest;
import com.settlein.backend.dto.SignupRequest;
import com.settlein.backend.dto.VerifyOtpRequest;
import com.settlein.backend.service.AuthService;
import com.settlein.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private ObjectMapper objectMapper;


    @PostMapping("/request-otp")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        return authService.sendOtpRequest(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        return authService.verifyOtp(request);
    }

    @PostMapping(value = "/complete-profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> completeProfile(
            @RequestPart("data") String request,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestHeader("Authorization") String authHeader) throws JsonProcessingException {
        try {
            CompleteProfileRequest profileRequest = objectMapper.readValue(request, CompleteProfileRequest.class);
            return authService.completeProfileWithImage(profileRequest, image, authHeader);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @PostMapping("/resend-otp")
//    public ResponseEntity<?> resendOtp(@RequestBody SignupRequest request @) {
//        return authService.resendOtp(request, authHeader);
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
