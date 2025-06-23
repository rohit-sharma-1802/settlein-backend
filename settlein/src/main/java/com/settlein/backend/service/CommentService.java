package com.settlein.backend.service;


import com.settlein.backend.dto.CommentRequest;
import com.settlein.backend.entity.Comment;
import com.settlein.backend.entity.Feed;
import com.settlein.backend.entity.MasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.settlein.backend.repository.CommentRepository;
import com.settlein.backend.repository.FeedRepository;
import com.settlein.backend.repository.MasterUserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepo;

    @Autowired
    private FeedRepository feedRepo;

    @Autowired
    private MasterUserRepository userRepo;

    public Comment addComment(String email, CommentRequest request) {
        MasterUser user = userRepo.findByEmail(email).orElseThrow();
        Feed feed = feedRepo.findById(request.getFeedId()).orElseThrow();

        Comment comment = new Comment();
        comment.setFeed(feed);
        comment.setUser(user);
        comment.setComments(request.getComments());
        return commentRepo.save(comment);
    }

    public List<Comment> getFeedComments(UUID feedId) {
        return commentRepo.findByFeedIdOrderByCreatedAtAsc(feedId);
    }
}