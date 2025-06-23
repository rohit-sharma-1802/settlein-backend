package com.settlein.backend.repository;

import com.settlein.backend.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByFeedIdOrderByCreatedAtAsc(UUID feedId);
}