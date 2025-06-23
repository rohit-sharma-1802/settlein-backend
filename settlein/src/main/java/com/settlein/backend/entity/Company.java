package com.settlein.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String emailDomain;
    private String dbName;

    public Company(String domain, String dbName) {
        this.dbName = dbName;
        this.emailDomain =  domain;
    }
}
