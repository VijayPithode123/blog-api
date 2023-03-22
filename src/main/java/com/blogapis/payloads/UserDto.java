package com.blogapis.payloads;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import com.blogapis.entities.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@XmlRootElement  //(apply Pojo class)
//Directly not expose entity to api same as expose directly UserDto
public class UserDto {

	
		private int id;
		
		@NotEmpty
		@Size(max=15,min=4,message = "name should be min 4 character !")
		private String name;
		
		@Email(message="Email is not Valid !!")
		private String email;
		
		@Size(max=10,min=6,message="Password Should be min 6 character and maximum 10 character")
		@JsonIgnoreProperties
		//@Pattern(regexp = "[^((?=(.*[d0-9@&#$?%!|(){}[]]){2,})(?=(.*[a-zA-Z]){2,}).{8,})$]")
		private String password;
		
		@NotEmpty
		private String about;
		
		private Set<RoleDto> roles=new HashSet<>();
	
	 
}
