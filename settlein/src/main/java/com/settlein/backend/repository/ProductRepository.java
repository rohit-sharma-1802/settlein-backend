package com.settlein.backend.repository;

import com.settlein.backend.entity.Product;
import com.settlein.backend.entity.Properties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    Page<Product> findByCompanyOrderByIdDesc(String company, Pageable pageable);
}
