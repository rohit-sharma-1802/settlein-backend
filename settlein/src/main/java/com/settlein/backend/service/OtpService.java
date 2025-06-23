package com.settlein.backend.service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
@Service
public class OtpService {
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    public String generateAndSendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(900000) + 100000);
        otpStore.put(email, otp);
        System.out.println("Sending OTP to " + email + ": " + otp);
        return otp;
    }


    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStore.get(email));
    }
}

