package com.blogapis.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blogapis.entities.Category;
import com.blogapis.entities.Comment;
import com.blogapis.entities.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class PostDto {

	private Integer id;
	@NotEmpty
	@Size(min=6,max=100,message = "Title should be 6 character to 50 Chracter")
	private String title;
	
	@NotEmpty
	@Size(min=10,max=200,message="Content Not Empty minimum 10 words and max=200")
	private String content;
	private String imageName;
	private Date date;

	private CategoryDto category;

	private UserDto user;
	
	
	private Set<CommentDto> comment=new HashSet();
	
	
	
}
