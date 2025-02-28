package com.bharatresult.frontend.bharatresult.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bharatresult.frontend.bharatresult.entity.PostMetrics;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface PostMetricsRepo extends JpaRepository<PostMetrics, Long> {

    Optional<PostMetrics> findByPostId(Long postId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post_metrics SET likes = likes + 1 WHERE post_id = :postId", nativeQuery = true)
    void incrementLikes(@Param("postId") Long postId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post_metrics SET view = view + 1 WHERE post_id = :postId", nativeQuery = true)
    void incrementViews(@Param("postId") Long postId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE post_metrics SET share = share + 1 WHERE post_id = :postId", nativeQuery = true)
    void incrementShares(@Param("postId") Long postId);
}
