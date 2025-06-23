package com.settlein.backend.controller;

import com.settlein.backend.dto.FeedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.settlein.backend.service.FeedService;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/feeds")
public class FeedController {

    @Autowired
    private FeedService feedService;

    @PostMapping
    public ResponseEntity<?> createFeed(@RequestBody FeedRequest request, Principal principal) {
        return ResponseEntity.ok(feedService.createFeed(principal.getName(), request));
    }

    @GetMapping
    public ResponseEntity<?> getAllFeeds() {
        return ResponseEntity.ok(feedService.getAllFeeds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getFeed(@PathVariable UUID id) {
        return ResponseEntity.ok(feedService.getById(id));
    }

    @PutMapping("/{id}/resolve")
    public ResponseEntity<?> markResolved(@PathVariable UUID id, Principal principal) throws AccessDeniedException {
        return ResponseEntity.ok(feedService.markAsResolved(principal.getName(), id));
    }
}