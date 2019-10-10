package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com")
public class MusicManagementApplication {
	public static void main(String[] args) {
		SpringApplication.run(MusicManagementApplication.class, args);
//
	}
}