package com.blogapis.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapis.payloads.ApiResponse;
import com.blogapis.payloads.CategoryDto;
import com.blogapis.service.CategoryService;

@RestController
@RequestMapping("/api/categorys")
public class CategoryControllers {
	
	@Autowired
	private CategoryService categoryService;
	
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		
		return new ResponseEntity<CategoryDto>(createCategory,HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,@PathVariable Integer id)
	{
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, id);
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
		
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer id)
	{
				this.categoryService.deleteCategory(id);
				
				return new ResponseEntity<ApiResponse>(new ApiResponse("Deleted successfully", true, new Date()),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer id)
	{
			CategoryDto categoryDto = this.categoryService.getById(id);
			
		return new ResponseEntity<CategoryDto>(categoryDto,HttpStatus.OK);	
		
	}
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory()
	{
				List<CategoryDto> list = this.categoryService.getAll();
				
				
				return new ResponseEntity<List<CategoryDto>>(list,HttpStatus.OK);
		
	}
	
	
	
	
	
}
