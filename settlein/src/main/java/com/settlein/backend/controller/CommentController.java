package com.settlein.backend.controller;

import com.settlein.backend.dto.CommentRequest;
import com.settlein.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public ResponseEntity<?> addComment(@RequestBody CommentRequest request, Principal principal) {
        return ResponseEntity.ok(commentService.addComment(principal.getName(), request));
    }

    @GetMapping("/feed/{feedId}")
    public ResponseEntity<?> getComments(@PathVariable UUID feedId) {
        return ResponseEntity.ok(commentService.getFeedComments(feedId));
    }
}