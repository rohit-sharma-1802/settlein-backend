package com.settlein.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    @Autowired
    private EmailService emailService;

    private final Map<String, String> otpStore = new ConcurrentHashMap<>();

    public String generateAndSendOtp(String email) {
        String otp = generateOtp();
        emailService.sendOtpEmail(email, otp);
        otpStore.put(email, otp);
        return otp;
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }


    public boolean verifyOtp(String email, String otp) {
        System.out.println("Email: " + email + " OTP: " + otp);
        System.out.println("OTP Store: " + otpStore.get(email));
        return otp.equals(otpStore.get(email));
    }
}

