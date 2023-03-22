package com.blogapis.service;

import java.util.List;

import com.blogapis.payloads.CategoryDto;

public interface CategoryService {


	
		CategoryDto createCategory(CategoryDto categoryDto);
		CategoryDto updateCategory(CategoryDto categoryDto,Integer id);
		void deleteCategory(Integer id);
		CategoryDto getById(Integer id);
		List<CategoryDto> getAll();
		
		
	
	
	
}
