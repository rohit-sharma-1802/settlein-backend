package com.settlein.backend.service;

import com.settlein.backend.dto.CompleteProfileRequest;
import com.settlein.backend.dto.UserUpdateRequest;
import com.settlein.backend.entity.MasterUser;
import com.settlein.backend.repository.MasterUserRepository;
import com.settlein.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.sql.DataSource;

@Service
public class MasterUserService {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private MasterUserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    public MasterUser updateUserDetails(CompleteProfileRequest req) {
        // Assuming JWT token or email is available in request
        MasterUser user = userRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("entity.entity.User not found"));

        user.setPhone(req.getPhone());
        user.setProfilePicUrl(req.getProfilePic());

        return userRepo.save(user);
    }

    // Get currently authenticated user using JWT
    public MasterUser getCurrentUser(String jwt) {
        String email = jwtUtil.extractUsername(jwt);
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("entity.entity.User not found"));
    }

    // Update current user profile
    public MasterUser updateProfile(String jwt, UserUpdateRequest req) {
        MasterUser user = getCurrentUser(jwt);
        user.setName(req.getName());
        user.setPhone(req.getPhone());
        user.setProfilePicUrl(req.getProfilePic());
        return userRepo.save(user);
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
