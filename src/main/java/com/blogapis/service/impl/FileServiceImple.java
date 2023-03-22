package com.blogapis.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogapis.service.FileService;import com.fasterxml.jackson.core.sym.Name;

@Service
public class FileServiceImple implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		//file name
		String fileName=file.getOriginalFilename();
		 //abc.png
	
		//Random name gentrate file
		String randomId=UUID.randomUUID().toString();
		
		String fileName1=randomId.concat(fileName.substring(fileName.lastIndexOf(".")));
		
		//fullPath  FileSeprator=/ Or File.pathSepratorChar  and File.pathSeprator Or File.pathSepratorChar =:
		//-  /images/"b352f936-5698-4fe4-a7f8-876359ce3da7.jpg"
		String fullPath=path+File.separator+fileName1;
		
		//Create folder if not created folder
		
		File f=new File(fullPath);
		
		if(!f.exists())
		{
			f.mkdir();
		}
		
		try {
			Files.copy(file.getInputStream(),Paths.get(fullPath));
			
		}
		catch (Exception e) {
			 e.printStackTrace();
		}
		 
			
		 
		
		
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		
		String fullPath=path+File.separator+fileName;
		
	 
			 InputStream inputStream=new FileInputStream(fullPath);
	
			 
		return inputStream;
	}

}
