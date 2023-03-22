package com.blogapis.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	
	public static final String Authorization_Header="Authorization";
	
	public ApiKey apiKey()
	{
		return new ApiKey("JWT", Authorization_Header, "header");
	}
	public  List<springfox.documentation.spi.service.contexts.SecurityContext> securityContexts()
	{
		return Arrays.asList(springfox.documentation.spi.service.contexts.SecurityContext.builder().securityReferences(sr()).build());
	
	}
	
	private List<SecurityReference> sr()
	{
		
		AuthorizationScope scope=new AuthorizationScope("global","Access Everything");
		
		return Arrays.asList(new SecurityReference("JWT", new AuthorizationScope []{scope}));
		
	}
	
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securitySchemes(Arrays.asList(apiKey()))//jwt configure two method
				.securityContexts(securityContexts())//jwt method enable
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();	
						
				
				
	}

	private ApiInfo getInfo() {
		
		return new ApiInfo("This is Blog Application","Project Rest Api Blog Application","1.0","google.com","Vijay Pithode", "License API","www.api.com");
	}

}
