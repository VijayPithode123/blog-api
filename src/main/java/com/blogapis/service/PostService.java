package com.blogapis.service;

import java.util.List;

import com.blogapis.entities.Post;
import com.blogapis.payloads.PageResponse;
import com.blogapis.payloads.PostDto;

public interface PostService {
	
	
	PostDto createPost(PostDto postDto,Integer categoryId,Integer userId);
	
	
	PostDto updatePost(PostDto postDto,Integer postId);
	
	void deletePost(Integer postId);
	
	
	//Pagination apply
	PageResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String dirBy);
	
	PostDto getPostById(Integer postId);
	
	List<PostDto> getByCategoryId(Integer categoryId);
	
	List<PostDto> getByUserId(Integer userId);
	
	List<PostDto> getSearch(String keyword);
	

}
