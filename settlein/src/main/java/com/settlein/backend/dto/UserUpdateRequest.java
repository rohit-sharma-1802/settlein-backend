package com.settlein.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private UUID id;
    private String name;
    private String phone;
    private String address;
    private String company;
    private String email;
    private String password;
}