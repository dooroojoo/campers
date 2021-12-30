package com.cp.campers.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.cp.campers")
public class campersApplication {

	public static void main(String[] args) {
		SpringApplication.run(campersApplication.class, args);
		
	}

}
