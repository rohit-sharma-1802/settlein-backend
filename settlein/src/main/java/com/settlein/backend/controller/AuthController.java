package com.settlein.backend.controller;

import com.settlein.backend.dto.CompleteProfileRequest;
import com.settlein.backend.dto.LoginRequest;
import com.settlein.backend.dto.SignupRequest;
import com.settlein.backend.dto.VerifyOtpRequest;
import com.settlein.backend.service.AuthService;
import com.settlein.backend.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private OtpService otpService;

    @PostMapping("/request-otp")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request){
        return authService.sendOtpRequest(request);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request){
        return authService.verifyOtp(request);
    }

    @PostMapping("/complete-profile")
    public ResponseEntity<?> completeProfile(@RequestBody CompleteProfileRequest request,  @RequestHeader("Authorization") String authHeader){
        return authService.completeProfile(request, authHeader);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
