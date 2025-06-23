package com.settlein.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompleteProfileRequest {
    private UUID id;
    private String name;
    private String phone;
    private String address;
    private String profilePic;
    private String email;
    private String password;
    private List<String> properties_image_urls;
}
