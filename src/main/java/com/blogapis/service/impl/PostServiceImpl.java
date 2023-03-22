package com.blogapis.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blogapis.entities.Category;
import com.blogapis.entities.Post;
import com.blogapis.entities.User;
import com.blogapis.exceptions.ResourceNotFoundException;
import com.blogapis.payloads.PageResponse;
import com.blogapis.payloads.PostDto;
import com.blogapis.repository.CategoryRepo;
import com.blogapis.repository.PostRepo;
import com.blogapis.repository.UserRepo;
import com.blogapis.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto,Integer categoryId,Integer userId) {

		Post post=this.modelMapper.map(postDto, Post.class);
		
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Id","CategoryId", categoryId));
		
		User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Id","UserId",userId));
		
		post.setImageName("default.png");
		post.setDate(new java.util.Date());
		post.setCategory(category);
		post.setUser(user);
		
		Post newPost=this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class); 
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		 
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Id","Id", postId));
	
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		post.setContent(postDto.getContent());
		
		Post updatePost=this.postRepo.save(post);
		
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		 
		Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Id","id", postId));
		
		postRepo.delete(post);
	}

	@Override
	public PageResponse getAllPosts(Integer pageSize,Integer pageNumber,String sortBy,String dirBy) {
		
	//int pageNumber=2;
	//int pageSize=5;
		
		Sort sort=null;
		if(dirBy.equalsIgnoreCase("asc"))
		{
			sort=Sort.by(sortBy).ascending();
		}
		else
		{
			sort=Sort.by(sortBy).descending();
		}
	
	Pageable p=PageRequest.of(pageSize, pageNumber,	sort);//Sort.by(sortBy).descending());
		
	Page<Post> pagePost = this.postRepo.findAll(p);
	
	List<Post> allPage=pagePost.getContent();
		
		
		
		 
		List<Post> posts=this.postRepo.findAll();
		
		 List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		 PageResponse pageResponse=new PageResponse();
		 pageResponse.setContent(postDto);
		 pageResponse.setPageNumber(pagePost.getNumber());
		 pageResponse.setPageSize(pagePost.getSize());
		 pageResponse.setTotalElement(pagePost.getTotalElements());
		 pageResponse.setTotalPage(pagePost.getTotalPages());
		 pageResponse.setLastPage(pagePost.isLast());
		 
		 return pageResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		
	Post post=	this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Id", "ID", postId));
		
		return this.modelMapper.map(post,PostDto.class);
	}

	@Override
	public List<PostDto> getByCategoryId(Integer categoryId) {
	
	Category cat=this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Id","CategoryId", 0));	
		
			List<Post> posts = this.postRepo.findByCategory(cat);
			
			 List<PostDto> postDto = posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
	
		return postDto;
	}

	@Override
	public List<PostDto> getByUserId(Integer userId) {
		
	User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("Id","userID", userId));
		
	List<Post> posts=this.postRepo.findByUser(user);
	List<PostDto> postDto=posts.stream().map((post)->this.modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
			return postDto;
	}

	@Override
	public List<PostDto> getSearch(String keyword) {
		 
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDto=posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDto;
	}

}
