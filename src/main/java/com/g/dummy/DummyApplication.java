package com.g.dummy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DummyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DummyApplication.class, args);
	}

	/**@Bean public WebMvcConfigurer corsConfigurer() {
	return new WebMvcConfigurer() {
	@Override public void addCorsMappings(CorsRegistry registry) {
	registry.addMapping("/api/**").allowedOrigins("*"); // TODO allow only possible origins
	}
	};
	}*/

}
