package com.settlein.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class MasterUser {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "fcm_token")
    private String fcmToken;

    private String name;
    private String email;
    private String password;

    private boolean isVerified;
    private String phone;
    private String company;
    private String profilePicUrl;
    private String otp;


    public MasterUser(String email) {
        this.email = email;
    }

    public MasterUser(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.isVerified = false;
    }
}
