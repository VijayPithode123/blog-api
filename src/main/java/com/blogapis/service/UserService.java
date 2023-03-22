package com.blogapis.service;

import java.util.List;

import com.blogapis.payloads.UserDto;

public interface UserService {
	
	
	UserDto registerNewUser(UserDto userDto);
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user,Integer id);
	UserDto getUserById(Integer id);
	List<UserDto> getAllUsers();
	void deleteUser(Integer id);
}
