package com.settlein.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String title;
    private String description;
    private Double price;
    private String category;
    private String location;
    @ElementCollection
    private List<String> imageUrls;

    @Column(name = "company")
    private String company;

    @Column(name = "user_id")
    private UUID userId;

}