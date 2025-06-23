package com.settlein.backend.repository;

import com.settlein.backend.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
    Optional<Company> findByEmailDomain(String emailDomain);
}
