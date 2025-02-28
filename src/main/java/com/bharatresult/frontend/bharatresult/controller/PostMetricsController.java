package com.bharatresult.frontend.bharatresult.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.bharatresult.frontend.bharatresult.service.PostMetricsService;

@Controller
public class PostMetricsController {

    @Autowired
    private PostMetricsService postMetricsService;

    @GetMapping("/count/like/{postId}")
    public ResponseEntity likePost(@PathVariable Long postId) {
        postMetricsService.likePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/count/view/{postId}")
    public ResponseEntity viewPost(@PathVariable Long postId) {
        postMetricsService.viewPost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/count/share/{postId}")
    public ResponseEntity sharePost(@PathVariable Long postId) {
        postMetricsService.sharePost(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
