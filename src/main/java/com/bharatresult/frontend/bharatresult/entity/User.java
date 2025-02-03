package com.bharatresult.frontend.bharatresult.entity;



import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String email;

	private String password;
	
	private String mobile;
	
	private String dob;

	// Soft delete flag
    private boolean deleted = false;
	
	@Column(name = "created_date", updatable = false)
	private LocalDateTime createdDate;

	@Column(name = "updated_date")
	private LocalDateTime updatedDate;

	@PrePersist
	public void prePersist() {
		this.createdDate = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedDate = LocalDateTime.now();
	}
}