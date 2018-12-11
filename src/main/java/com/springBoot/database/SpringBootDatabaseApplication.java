package com.springBoot.database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBootDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDatabaseApplication.class, args);
	}
}
