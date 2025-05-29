package com.example.fraudeZero;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
public class FraudeZeroApplication {

	public static void main(String[] args) {
		SpringApplication.run(FraudeZeroApplication.class, args);
	}

}
