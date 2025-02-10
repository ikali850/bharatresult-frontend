package com.bharatresult.frontend.bharatresult.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostDto<T> implements Serializable {
    private static final long serialVersionUID = 71565260778832816L;
    private List<T> items;
    private Page<T> page;
}
