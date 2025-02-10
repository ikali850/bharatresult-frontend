package com.bharatresult.frontend.bharatresult.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "trending_post")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrendingPost implements Serializable {
    private static final long serialVersionUID = 715652607788328164L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name, url;

}
