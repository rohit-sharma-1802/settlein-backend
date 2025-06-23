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
public class ChatStartRequest {
    private UUID postId;
    private String postType; // PROPERTY or PRODUCT
}
