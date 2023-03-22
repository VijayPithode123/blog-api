package com.blogapis.controllers;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.blogapis.payloads.ApiResponse;
import com.blogapis.payloads.UserDto;
import com.blogapis.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserControllers {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		UserDto user=userService.createUser(userDto);
		return new ResponseEntity<UserDto>(user,HttpStatus.CREATED);
	}
	
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer id)
	{
		UserDto updateUser = this.userService.updateUser(userDto, id);
		return ResponseEntity.ok(updateUser);
	}

	
	//Only Admin user deleted
	@PreAuthorize("hasRole('ADMIN_USER')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@Valid @PathVariable("userId") int id)
	{
		this.userService.deleteUser(id);
		
			return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully",true,new Date()),HttpStatus.OK);
		
	}
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
	{
		List<UserDto> allUsers = this.userService.getAllUsers();
		
		return ResponseEntity.ok(allUsers);
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable("userId") Integer id)
	{
				UserDto userById = this.userService.getUserById(id);
	
					return new ResponseEntity<UserDto>(userById,HttpStatus.OK);
	}
	
	
	
	
	
	
}
