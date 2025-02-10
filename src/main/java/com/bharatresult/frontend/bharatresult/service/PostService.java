package com.bharatresult.frontend.bharatresult.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bharatresult.frontend.bharatresult.controller.HomeController;
import com.bharatresult.frontend.bharatresult.entity.Post;
import com.bharatresult.frontend.bharatresult.entity.PostDto;
import com.bharatresult.frontend.bharatresult.entity.TrendingPost;
import com.bharatresult.frontend.bharatresult.repository.PostRepository;
import com.bharatresult.frontend.bharatresult.repository.TrendingPostRepo;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private TrendingPostRepo trendingPostRepo;

    public static final Logger logger = LoggerFactory.getLogger(PostService.class);

    // Method to get posts by category, sorted by latest first, with pagination
    public Page<Post> getPostsByCategory(String categoryName, int rowSize, int pageNumber) {
        logger.info("getPostByCategory method called in PostService with category name :" + categoryName);
        Pageable pageable = PageRequest.of(pageNumber, rowSize); // PageRequest takes page number and size
        String categoryPattern = "%" + categoryName + "%";
        return postRepo.findByCategoryLikeAndIsPublishTrue(categoryPattern, pageable);
    }

    @Cacheable(value = "postdata", key = "#url")
    public Post getPost(String url) {
        logger.info("getPost method called in PostService with url" + url);
        return this.postRepo.findByPostUrl(url).get();
    }

    @Cacheable(value = "trendingPostData",key = "#root.methodName")
    public List<TrendingPost> getTrendingPosts() {

        return this.trendingPostRepo.findAll();
    }

    @Cacheable(value = "resultData", key = "#pageNumber")
    public PostDto<Post> getResults(int rowSize, int pageNumber) {
        Page<Post> resulsList = getPostsByCategory("result", rowSize, pageNumber);
        PostDto<Post> postDto = new PostDto<>();
        postDto.setItems(resulsList.getContent());
        postDto.setPage(resulsList);
        return postDto;

    }

    @Cacheable(value = "syllabusData", key = "#pageNumber")
    public PostDto<Post> getSyllbus(int rowSize, int pageNumber) {
        Page<Post> resulsList = getPostsByCategory("syllabus", rowSize, pageNumber);
        PostDto<Post> postDto = new PostDto<>();
        postDto.setItems(resulsList.getContent());
        postDto.setPage(resulsList);
        return postDto;

    }

    @Cacheable(value = "jobData", key = "#pageNumber")
    public PostDto<Post> getJobs(int rowSize, int pageNumber) {
        Page<Post> resulsList = getPostsByCategory("job", rowSize, pageNumber);
        PostDto<Post> postDto = new PostDto<>();
        postDto.setItems(resulsList.getContent());
        postDto.setPage(resulsList);
        return postDto;

    }

    @Cacheable(value = "admitCardData", key = "#pageNumber")
    public PostDto<Post> getExams(int rowSize, int pageNumber) {
        Page<Post> resulsList = getPostsByCategory("admit card", rowSize, pageNumber);
        PostDto<Post> postDto = new PostDto<>();
        postDto.setItems(resulsList.getContent());
        postDto.setPage(resulsList);
        return postDto;
    }

    @Cacheable(value = "admissionData", key = "#pageNumber")
    public PostDto<Post> getAdmissions(int rowSize, int pageNumber) {
        Page<Post> resulsList = getPostsByCategory("admission", rowSize, pageNumber);
        PostDto<Post> postDto = new PostDto<>();
        postDto.setItems(resulsList.getContent());
        postDto.setPage(resulsList);
        return postDto;
    }

    @Cacheable(value = "otherFormData", key = "#pageNumber")
    public PostDto<Post> getOtherForms(int rowSize, int pageNumber) {
        Page<Post> resulsList = getPostsByCategory("other form", rowSize, pageNumber);
        PostDto<Post> postDto = new PostDto<>();
        postDto.setItems(resulsList.getContent());
        postDto.setPage(resulsList);
        return postDto;
    }

}
