package com.bharatresult.frontend.bharatresult.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Messages {

    private Long id;
    private String fName;

    private String lName;

    private String email;

    private String message;

    private LocalDateTime createdAt = LocalDateTime.now();
}
