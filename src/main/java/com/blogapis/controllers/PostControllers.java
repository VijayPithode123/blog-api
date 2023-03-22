package com.blogapis.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogapis.config.AppConstants;
import com.blogapis.entities.Post;
import com.blogapis.payloads.PageResponse;
import com.blogapis.payloads.PostDto;
import com.blogapis.service.FileService;
import com.blogapis.service.PostService;
import com.fasterxml.jackson.databind.ser.std.FileSerializer;

@RestController
@RequestMapping("/api/")
public class PostControllers {

	@Value("${project.image}")
	private String path;

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;

	//Creating Post
		@PostMapping("/user/{userID}/category/{categoryId}/posts")
		public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto,
				@PathVariable Integer userID,
				@PathVariable Integer categoryId)
		{
			
				PostDto postdto=this.postService.createPost(postDto, categoryId, userID);
			
			
				return new ResponseEntity<PostDto>(postdto,HttpStatus.CREATED);
		}
		
		//CategoryId wise Posts
		
		
		@GetMapping("/category/{categoryID}/posts")
		public ResponseEntity<List<PostDto>> getCategoryIdByPosts(@PathVariable Integer categoryID)
		{
			List<PostDto> posts = this.postService.getByCategoryId(categoryID);
			
			return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
			
		}
		
		//UserId Wise Post
		@GetMapping("/user/{userId}/posts")
		public ResponseEntity<List<PostDto>> getUserIdByPosts(@PathVariable Integer userId)
		{	
				List<PostDto> posts=this.postService.getByUserId(userId);
				
				return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
			
		}
		//getAllPosts
		@GetMapping("/posts")
		public ResponseEntity<PageResponse> getAllPost(
				@RequestParam(value="pageSize",defaultValue =AppConstants.PAGE_SIZE,required = false) Integer pageSize,
				@RequestParam(value="pageNumber",defaultValue =AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
				@RequestParam(value="sortBy",defaultValue =AppConstants.SORT_BY,required = false) String sortBy,
				@RequestParam(value="dirBy",defaultValue =AppConstants.DIR_BY,required = false) String dirBy
				)
		{
			
			PageResponse posts=this.postService.getAllPosts(pageSize,pageNumber,sortBy,dirBy);
			
			return new ResponseEntity<PageResponse>(posts,HttpStatus.OK);
			
		}
		//getSinglePost
		@GetMapping("/post/{postId}")
		public ResponseEntity<PostDto> getPostByID(@PathVariable Integer postId)
		{
			PostDto postDto=this.postService.getPostById(postId);
			
			return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
		}
		
		//DeletePosts
		
		@DeleteMapping("/{postId}")
		public ResponseEntity<?> deletePost(@PathVariable Integer postId)
		{
			this.postService.deletePost(postId);
			
			return ResponseEntity.ok("Deleted Successfully");
			
		}
		
		@PutMapping("/{postId}")
		public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId)
		{
				
			PostDto updatePost = postService.updatePost(postDto, postId);
		
			return new ResponseEntity<PostDto>(updatePost,HttpStatus.CREATED);
			
			
			
		}
		
		//Search
		@GetMapping("/posts/search/{keyword}")
		public ResponseEntity<List<PostDto>> getSearch(@PathVariable("keyword") String keyword)
		{
			List<PostDto> result=this.postService.getSearch(keyword);
			
			 
			
			return  new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);
			
		}
		
		
		//PostImageUpload
		@PostMapping("/post/image/upload/{postId}")
		public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile file,@PathVariable("postId") Integer postId) throws IOException 
		{
			
			
			 PostDto postDto=this.postService.getPostById(postId);
			 String FileName = this.fileService.uploadImage(path, file);
			 //PostDto updatePost = this.postService.updatePost(postDto, postId);
			 postDto.setImageName(FileName);
			 PostDto postDto2 = this.postService.updatePost(postDto, postId);
			 
			 return new ResponseEntity<PostDto>(postDto2,HttpStatus.OK);
			 
			 
				
		}
		
		//Image get
		
		@GetMapping(value="/post/image/{imageName}", produces= {MediaType.IMAGE_JPEG_VALUE})
		public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException
		{
			InputStream resource = this.fileService.getResource(path, imageName);
			
			
			System.out.println(resource);
			
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		
				StreamUtils.copy(resource,response.getOutputStream());
			
		}
		
		
		
		
	
		
		
}
