package com.bharatresult.frontend.bharatresult.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Page;

import com.bharatresult.frontend.bharatresult.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // Fetch posts where category LIKE the given string, isPublish is true, and
    // order by createdDate and updatedDate
    @Query("SELECT p FROM Post p WHERE p.category LIKE %:category% AND p.isPublish = true " +
            "ORDER BY p.updatedDate desc")
    Page<Post> findByCategoryLikeAndIsPublishTrue(String category, org.springframework.data.domain.Pageable pageable);

    List<Post> findTop10ByIsPublishTrueOrderByUpdatedDateDesc();

    Optional<Post> findByPostUrl(String url);
}
