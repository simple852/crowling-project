package com.teamproject.computerproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication

public class ComputerProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ComputerProjectApplication.class, args);
	}

}
