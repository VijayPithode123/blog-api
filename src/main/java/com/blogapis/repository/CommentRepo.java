package com.blogapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
