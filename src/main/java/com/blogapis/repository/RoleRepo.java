package com.blogapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
