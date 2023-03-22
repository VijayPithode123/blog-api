package com.blogapis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapis.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
