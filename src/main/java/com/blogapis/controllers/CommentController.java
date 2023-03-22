package com.blogapis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapis.payloads.CommentDto;
import com.blogapis.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,@PathVariable("postId") Integer postId)
	{
		
		System.out.println(commentDto.getContent());
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		
		
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.OK);
		
	}
	@DeleteMapping("/post/{postId}/comment")
	public ResponseEntity<?> deleteEntity(@PathVariable Integer postId)
	{
		this.commentService.deleteComment(postId);
		
		
		return (ResponseEntity<?>) ResponseEntity.ok("Comment deleted successfully");
	}
	
}
