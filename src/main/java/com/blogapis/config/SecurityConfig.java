package com.blogapis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.blogapis.security.CustomUserDetailService;
import com.blogapis.security.JwtAuthenticationEntryPoint;
import com.blogapis.security.JwtAuthenticationFilter;



//Basic authentication
//per request send username and password

@SuppressWarnings("deprecation")
@Configuration
@EnableWebMvc
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //all method security apply
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	private static final String[] PUBLIC_URLS= 
		{
				"/api/v1/auth/login/**",
				"/v3/api-docs",
				"/v2/api-docs",
				"/swagger-resources/**",
				"/swagger-ui/**",
				"/webjars/**"
				
		};
	
	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()  //by cross site request forgery true hacker can take to send same link form like netbanking 
		.authorizeRequests()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.DELETE).permitAll()
		//.antMatchers(HttpMethod.GET).permitAll()
		.antMatchers(HttpMethod.POST).permitAll()
		.anyRequest()
		.authenticated()
		.and().exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)//exception genrated this class exception throws
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);//  form is found dialogue genrated javascript 
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		//one problem is here per api request tset  send then per request give the username and password this problem overcome jwt Aunthentication
		
		
		
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//Database based Authentication
		auth.userDetailsService(this.customUserDetailService).passwordEncoder(passwordEncoder());
		
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		 
		return super.authenticationManagerBean();
	}
	
	
	
	

	
}
