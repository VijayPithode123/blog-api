package com.blogapis.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapis.entities.Comment;
import com.blogapis.entities.Post;
import com.blogapis.exceptions.ResourceNotFoundException;
import com.blogapis.payloads.CommentDto;
import com.blogapis.repository.CommentRepo;
import com.blogapis.repository.PostRepo;
import com.blogapis.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

			Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("CommentId","CommentId", postId));
		 
			Comment comment=this.modelMapper.map(commentDto,Comment.class);
			
			comment.setPost(post);
			Comment comment1 = this.commentRepo.save(comment);
			
			
			System.out.println(comment1.getContent());
		
				
		
		return this.modelMapper.map(comment1, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("CommentId","CommentID", commentId));
		this.commentRepo.delete(comment);
		
		
		
	}

}
