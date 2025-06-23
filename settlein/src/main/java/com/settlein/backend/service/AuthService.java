package com.settlein.backend.service;

import com.settlein.backend.dto.CompleteProfileRequest;
import com.settlein.backend.dto.LoginRequest;
import com.settlein.backend.dto.SignupRequest;
import com.settlein.backend.dto.VerifyOtpRequest;
import com.settlein.backend.entity.Company;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.repository.MasterUserRepository;
import com.settlein.backend.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private MasterUserRepository userRepo;

    @Autowired
    private OtpService otpService;

    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<?> sendOtpRequest(@RequestBody SignupRequest req) {
        Optional<MasterUser> userOpt = userRepo.findByEmail(req.getEmail());
        MasterUser user = userOpt.orElseGet(() -> new MasterUser(req.getEmail()));

        String otp = otpService.generateAndSendOtp(req.getEmail());
        user.setOtp(otp);
        user.setVerified(false);

        String domain = extractDomain(req.getEmail());
        String companyName = domain.split("\\.")[0];
        user.setCompany(companyName);

        userRepo.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP sent to email.");
        response.put("id", user.getId());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest req) {
        Optional<MasterUser> userOpt = userRepo.findById(req.getId());

        if (userOpt.isEmpty() || !otpService.verifyOtp(req.getEmail(), req.getOtp())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP");
        }

        MasterUser user = userOpt.get();
        user.setVerified(true);
        userRepo.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getCompany());
        Map<String, Object> response = new HashMap<>();
        response.put("isVerified", user.isVerified());
        response.put("message", "Otp has been verified, add user details");
        response.put("userId", user.getId());
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> completeProfile(@RequestBody CompleteProfileRequest req, String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String email = jwtUtil.extractUsername(token);

        Optional<MasterUser> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        MasterUser user = userOpt.get();
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setPassword(BCrypt.hashpw(req.getPassword(), BCrypt.gensalt())); // Save password now

        userRepo.save(user);
        Map<String, Object> response = new HashMap<>();
        response.put("id",user.getId());
        response.put("message", "Profile has been completed successfully");
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Optional<MasterUser> userOpt = userRepo.findByEmail(req.getEmail());

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        MasterUser user = userOpt.get();

        if (!BCrypt.checkpw(req.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        String domain = extractDomain(req.getEmail());
        String company = domain.split("\\.")[0];

        String token = jwtUtil.generateToken(user.getEmail(), company);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    public static String extractDomain(String email) {
        return email.substring(email.indexOf("@") + 1);
    }
}
