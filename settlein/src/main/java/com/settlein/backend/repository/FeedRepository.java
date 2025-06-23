package com.settlein.backend.repository;

import com.settlein.backend.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FeedRepository extends JpaRepository<Feed, UUID> {
    List<Feed> findAllByOrderByCreatedAtDesc();
}
