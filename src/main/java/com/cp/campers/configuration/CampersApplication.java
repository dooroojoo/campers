package com.cp.campers.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@SpringBootApplication
@ComponentScan("com.cp.campers")
public class CampersApplication {

	public static void main(String[] args) {
		SpringApplication.run(CampersApplication.class, args);	
	}
	
	@Bean
	public MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource messageSource
			= new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasename("classpath:message/message");
		
		return messageSource;
	}

}
