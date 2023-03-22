package com.blogapis.payloads;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blogapis.entities.Post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	
	
	private Integer id;
	
	@NotEmpty
	@Size(min=5 ,max=100 ,message ="Title should be min 5 character and max=100 character")
	private String title;
	
	@NotEmpty
	@Size(max=300 ,min=10,message = "description should be min 10 Character and Max 300 Character")
	private String description;

	
}
