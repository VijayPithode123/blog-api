package com.blogapis.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.validation.UnexpectedTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapis.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExeptionHandler(ResourceNotFoundException ex)
	{
		String message=ex.getMessage();
		
		ApiResponse apiResponse=new ApiResponse(message,false,new Date());
		
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> HandlerMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String,String> resp=new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
			String message=error.getDefaultMessage();
			resp.put(fieldName, message);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<ApiResponse> handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex)
	{
		 
		String method = ex.getMessage();
		
		
		return new ResponseEntity<ApiResponse>(new ApiResponse(method, false,new Date()),HttpStatus.METHOD_NOT_ALLOWED);
	}
}
