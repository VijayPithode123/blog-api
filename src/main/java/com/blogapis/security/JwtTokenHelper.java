package com.blogapis.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// Step-3
//All token related information stored to performing operations
@Component
public class JwtTokenHelper {
	
	
	//token validity show means how many times tokens expire
	public static final long JWT_TOKEN_VALIDITY= 5 * 60 * 60;
	
	private String secretKey="jwtTokenKey";
	
	
	//retrive username by jwt token
	public String getUsernameFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getSubject);
		
	}
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getExpiration);
		
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver)
	{
					final Claims claim=getAllClaimsFromToken(token);
					
					return claimsResolver.apply(claim);
	}
	
	
	//for reteriving any information token related then we will need secret key
	
	 


	private Claims getAllClaimsFromToken(String token) {
		
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		 
		
	}
	//Check if the token has expired
	public boolean isTokenExpired(String token)
	{
		
		final Date expirationDate = getExpirationDateFromToken(token);
		return expirationDate.before(new Date());
		
	}
	
	//genrate token for user
	public String genrateToken(UserDetails userDetails)
	{
		Map<String,Object> claims=new HashMap<>();
		
		return doGenrateToken(claims, userDetails.getUsername());
	}
	
	//while creating token
	//1.define claims of the token like expiration,issuer,subject and id,
	//2.sign the jwt using hs512 algoritham and secreat key
	//3.According to jws compact serialization
	//compaction to jwt to url safe String
	
	
	private String doGenrateToken(Map<String,Object> claims,String subject) 
	{
		return Jwts.builder().setClaims(claims).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY * 100L))
				.signWith(SignatureAlgorithm.HS512, secretKey).compact();
				
	}
	
	
	//Validation token
	public boolean validateToken(String token,UserDetails userDetails)
	{
		final String username=getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	
}
