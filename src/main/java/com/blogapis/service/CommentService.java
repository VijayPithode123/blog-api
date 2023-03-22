package com.blogapis.service;

import com.blogapis.entities.Comment;
import com.blogapis.payloads.CommentDto;

public interface CommentService {
	
	
	CommentDto createComment(CommentDto commentDto,Integer postId);
	void deleteComment(Integer commentId);

}
