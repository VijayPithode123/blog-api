package com.blogapis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogapis.entities.User;
import com.blogapis.exceptions.ResourceNotFoundException;
import com.blogapis.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	
	
	
	  @Autowired private UserRepo userRepo;
	  
	  @Override 
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	  {
	  
		  System.out.println(username);
	  
		  User user = this.userRepo.findByEmail(username).orElseThrow(()->new
		  ResourceNotFoundException("username", "username", 0)); 
		  return user; 
	  }
	 
}
