package com.settlein.backend.repository;

import com.settlein.backend.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID>, JpaSpecificationExecutor<Products> {
    Page<Products> findByCompanyOrderByIdDesc(String company, Pageable pageable);

    Optional<Products> findById(UUID id);
}
