package com.syuk27.springboot.pt_restful_hibernate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class PtRestfulHibernateApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtRestfulHibernateApplication.class, args);
	}

	// Cross Origin Requests
	// Allow all requests only from http://localhost:5173/

	/** SpringSecurityConfiguration */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry
					.addMapping("/**")
					.allowedMethods("*")
					.allowedOrigins("http://localhost:5173");
			}
		};
	}
	
}
