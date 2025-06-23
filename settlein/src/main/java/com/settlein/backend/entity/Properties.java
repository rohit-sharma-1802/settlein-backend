package com.settlein.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Properties {
    @Id
    @GeneratedValue
    private UUID id;
    private String title;
    private String description;
    private String location;
    private Double price;

    @ElementCollection
    private List<String> imageUrls;

    @Column(name = "company")
    private String company;

    @Column(name = "user_id")
    private UUID userId;

}
