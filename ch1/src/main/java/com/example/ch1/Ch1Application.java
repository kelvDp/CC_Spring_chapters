package com.example.ch1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication  // Tells machine at runtime that this is a spring boot app
public class Ch1Application {

	public static void main(String[] args) {
		SpringApplication.run(Ch1Application.class, args); // main method calls spring app run method
	}

}
