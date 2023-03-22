package com.blogapis.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;



// step-2 JWT Security
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
//  Un-authrized user wrong username and password write then this reponse send client call exception  		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied !!");
	}

}
