package com.blogapis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.blogapis.entities.Category;
import com.blogapis.entities.Post;
import com.blogapis.entities.User;
import com.blogapis.payloads.PostDto;

public interface PostRepo extends JpaRepository<Post, Integer> {

	
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory(Category category);
	
	
	@Query("select p from Post p  where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
}
