package com.settlein.backend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
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
public class PropertyRequest {
    private UUID id;
    private String title;
    private String description;
    private String location;
    private Double price;
    private List<String> imageUrls;
    private String company;
    private UUID userId;
}