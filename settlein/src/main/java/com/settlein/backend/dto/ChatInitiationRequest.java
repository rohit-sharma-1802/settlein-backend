package com.settlein.backend.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ChatInitiationRequest {
    private UUID interestedUserId;
    private UUID ownerUserId;
    private UUID itemId;
    private String type;
}