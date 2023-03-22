package com.blogapis.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.CollectionJoin;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogapis.config.AppConstants;
import com.blogapis.entities.Role;
import com.blogapis.entities.User;
import com.blogapis.exceptions.ResourceNotFoundException;
import com.blogapis.payloads.UserDto;
import com.blogapis.repository.RoleRepo;
import com.blogapis.repository.UserRepo;
import com.blogapis.service.UserService;

@Service
public class UserServiceImple implements UserService {
	
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo; 

	@Override
	public UserDto createUser(UserDto userDto) {
		 
			User user=this.dtoUsertoUser(userDto);
			
			User saveUser=this.userRepo.save(user);
		
		return this.UsertoUserDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer id) {
	
			User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
			user.setName(userDto.getName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setAbout(userDto.getAbout());
			
			User updateUser = this.userRepo.save(user);
		
				UserDto usertoUserDto = this.UsertoUserDto(updateUser);
				
				return usertoUserDto;
	
		
	
	}

	@Override
	public UserDto getUserById(Integer id) {
		
		User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));
		
		return this.UsertoUserDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		 
		List<User> users = this.userRepo.findAll();
	
	List<UserDto> userDto = users.stream().map(user->this.UsertoUserDto(user)).collect(Collectors.toList());
		return userDto;
	}

	@Override
	public void deleteUser(Integer id) {
		 
		 User user=this.userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("User","Id",id));

		 		this.userRepo.delete(user);
		 	

	}
	
	
	
	
	//ModelMapper
	
	public User dtoUsertoUser(UserDto dtoUser)
	{
		User user=this.modelMapper.map(dtoUser,User.class);
		
	/*	user.setId(dtoUser.getId());
		user.setName(dtoUser.getName());
		user.setEmail(dtoUser.getEmail());
		user.setPassword(dtoUser.getPassword());
		user.setAbout(dtoUser.getAbout());*/
	
		return user;
	}
	public UserDto UsertoUserDto(User user)
	{
		UserDto userDto=this.modelMapper.map(user,UserDto.class);
		/*userDto.setId(user.getId());
		userDto.setName(user.getName());
		userDto.setEmail(user.getEmail());//Manually Map but 100 class is there so it is not possible
		userDto.setPassword(user.getPassword());
		userDto.setAbout(user.getAbout());*/
	
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		
		User user = this.modelMapper.map(userDto, User.class);
		
		
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
			return this.modelMapper.map(newUser,UserDto.class);
	}	
	

}
