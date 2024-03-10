package com.spring.news;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()

public class NewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsApplication.class, args);
	}

}
