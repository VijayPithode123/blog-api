package com.blogapis.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	 String resourseName;
	 String fieldName;
	 int fieldValue;
	public ResourceNotFoundException(String resourseName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s: %d",resourseName,fieldName,fieldValue));
		this.resourseName = resourseName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	
}
