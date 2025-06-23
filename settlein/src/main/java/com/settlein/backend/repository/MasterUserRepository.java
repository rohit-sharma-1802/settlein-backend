package com.settlein.backend.repository;

import com.settlein.backend.entity.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface MasterUserRepository extends JpaRepository<MasterUser, UUID> {
    Optional<MasterUser> findByEmail(String email);
}
