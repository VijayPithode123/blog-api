package com.blogapis.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapis.entities.Category;
import com.blogapis.exceptions.ResourceNotFoundException;
import com.blogapis.payloads.CategoryDto;
import com.blogapis.repository.CategoryRepo;
import com.blogapis.service.CategoryService;

@Service
public class CategoryServiceImple implements CategoryService {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
				Category category = this.modelMapper.map(categoryDto,Category.class);
				Category category2 = this.categoryRepo.save(category);
			
			return this.modelMapper.map(category2,CategoryDto.class);
		
	 
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer id) {
		 
		
		
		Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("id","id",id));
		
		category.setTitle(categoryDto.getTitle());
		category.setDescription(categoryDto.getDescription());
		Category save = this.categoryRepo.save(category);
			return this.modelMapper.map(save,CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer id) {
		
		Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("id","id", id));
		
					this.categoryRepo.delete(category);
		
	}

	@Override
	public CategoryDto getById(Integer id) {
		
		Category category = this.categoryRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Id","id", id));
		
		return this.modelMapper.map(category,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAll() {
		
		List<Category> all = this.categoryRepo.findAll();
		
			List<CategoryDto> list2 = all.stream().map((list)->this.modelMapper.map(list,CategoryDto.class)).collect(Collectors.toList());
		
		return list2;
	}

}
