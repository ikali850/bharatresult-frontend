package com.bharatresult.frontend.bharatresult.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bharatresult.frontend.bharatresult.entity.PostMetrics;
import com.bharatresult.frontend.bharatresult.repository.PostMetricsRepo;

@Service
public class PostMetricsService {

    @Autowired
    private PostMetricsRepo postMetricsRepo;

    public void likePost(Long postId) {
        this.postMetricsRepo.incrementLikes(postId);
    }

    public void viewPost(Long postId) {
        this.postMetricsRepo.incrementViews(postId);
    }

    public void sharePost(Long postId) {
        this.postMetricsRepo.incrementShares(postId);
    }

    public PostMetrics savePostMetrics(PostMetrics postMetrics) {
        return this.postMetricsRepo.save(postMetrics);
    }

    public PostMetrics getPostMetrics(Long postId) {
        Optional<PostMetrics> postMetrics = this.postMetricsRepo.findByPostId(postId);
        if (postMetrics.isPresent()) {
            return postMetrics.get();
        }
        PostMetrics postMetricsData = new PostMetrics();
        postMetricsData.setPostId(postId);
        return savePostMetrics(postMetricsData);
    }

}
