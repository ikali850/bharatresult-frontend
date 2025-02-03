package com.bharatresult.frontend.bharatresult.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bharatresult.frontend.bharatresult.entity.Post;
import com.bharatresult.frontend.bharatresult.repository.PostRepository;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

     // Method to get posts by category, sorted by latest first, with pagination
    public Page<Post> getPostsByCategory(String categoryName, int rowSize, int pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber, rowSize); // PageRequest takes page number and size
        String categoryPattern = "%" + categoryName + "%";
        return postRepo.findByCategoryLikeAndIsPublishTrue(categoryPattern, pageable);
    }
    // method to get post via its Poturl
    public Post getPost(String url){
        return this.postRepo.findByPostUrl(url).get();
    }

}
