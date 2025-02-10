package com.bharatresult.frontend.bharatresult.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bharatresult.frontend.bharatresult.entity.Messages;

@Repository
public interface MessageUsRepo extends JpaRepository<Messages,Long> {

}
