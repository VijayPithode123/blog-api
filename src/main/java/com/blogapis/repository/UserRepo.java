package com.blogapis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.blogapis.entities.User;

public interface UserRepo extends JpaRepository<User,Integer>{

	
				//@Query(value = "select u from Users u where email=?1",nativeQuery = true)
				Optional<User> findByEmail(String username);
	
}
