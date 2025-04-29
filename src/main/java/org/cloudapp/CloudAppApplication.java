package org.cloudapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class CloudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudAppApplication.class, args);
	}

	@PostConstruct
	public void init() {
		System.out.println("CloudApp is running! Welcome!");
	}
}