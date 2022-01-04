package com.cp.campers.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	private final String uploadFilesPath;
	
	public WebConfig(@Value("${custom.path.upload-images}") String uploadFilesPath) {
		
		this.uploadFilesPath = uploadFilesPath;
		
	}
	

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		List<String> imageFolders = Arrays.asList("profileImg","boardImg");
		
		for(String imageFolder : imageFolders) {
			registry.addResourceHandler("/resources/images/uploadFiles/" + imageFolder + "/**")
				.addResourceLocations("file:///" + uploadFilesPath + imageFolder + "/");
			
		}
	}	
	
	

}
