package com.settlein.backend.service;

import com.settlein.FeedStatus;
import com.settlein.backend.dto.FeedRequest;
import com.settlein.backend.entity.Feed;
import com.settlein.backend.entity.MasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.settlein.backend.repository.FeedRepository;
import com.settlein.backend.repository.MasterUserRepository;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FeedService {

    @Autowired
    private FeedRepository feedRepo;

    @Autowired
    private MasterUserRepository userRepo;

    public Feed createFeed(FeedRequest request) {
        MasterUser user = userRepo.findById(request.getUserId()).orElseThrow();
        Feed feed = new Feed();
        feed.setUser(user);
        feed.setDescription(request.getDescription());
        return feedRepo.save(feed);
    }

    public List<Feed> getAllFeeds() {
        return feedRepo.findAllByOrderByCreatedAtDesc();
    }

    public Feed getById(UUID id) {
        return feedRepo.findById(id).orElseThrow();
    }

    public Feed markAsResolved(UUID userId, UUID feedId) throws AccessDeniedException {
        Feed feed = feedRepo.findById(feedId).orElseThrow(() -> new RuntimeException("entity.entity.Feed not found"));

        if (!feed.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("Only the feed creator can mark it as resolved.");
        }

        feed.setStatus(FeedStatus.RESOLVED);
        feed.setUpdatedAt(LocalDateTime.now());
        return feedRepo.save(feed);
    }
}