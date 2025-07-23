package com.settlein.backend.service;

import com.settlein.backend.dto.CompleteProfileRequest;
import com.settlein.backend.dto.UserUpdateRequest;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.repository.MasterUserRepository;
import com.settlein.backend.util.ImageUploadUtil;
import com.settlein.backend.util.JwtUtil;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.io.IOException;

@Service
public class MasterUserService {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MasterUserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ImageUploadUtil imageUploadUtil;

    // Get currently authenticated user using JWT
    public MasterUser getCurrentUser(String jwt) {
        String email = jwtUtil.extractUsername(jwt);
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("entity.entity.User not found"));
    }

    // Update current user profile
    public ResponseEntity<?> updateProfile(String authHeader, UserUpdateRequest request, MultipartFile profileImage) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.extractUsername(jwt);

        MasterUser user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());
        user.setCompany(jwtUtil.extractCompany(jwt));
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));

        if (profileImage != null) {
            try {
                String imageUrl = imageUploadUtil.uploadImage(profileImage);
                System.out.println(imageUrl);
                user.setProfilePicUrl(imageUrl);
            } catch (IOException e) {
                return ResponseEntity.status(500).body("Image upload failed: " + e.getMessage());
            }
        }

        userRepo.save(user);
        return ResponseEntity.ok("Profile updated successfully");
    }

    // Get user by email
    public MasterUser getByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("entity.entity.User not found with email: " + email));
    }

    // Save a user entity
    public void save(MasterUser user) {
        userRepo.save(user);
    }
}
