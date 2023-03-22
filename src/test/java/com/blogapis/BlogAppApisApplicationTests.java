package com.blogapis;

import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blogapis.payloads.ApiResponse;
import com.blogapis.repository.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void getInfo()
	{
		  String name=userRepo.getClass().getName();
		  String packageName=userRepo.getClass().getCanonicalName();
		  
		  System.out.println(name);
		  System.out.println(packageName);
	}
	
	
}
