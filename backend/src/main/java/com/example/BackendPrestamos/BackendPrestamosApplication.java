package com.example.BackendPrestamos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.BackendPrestamos")
public class BackendPrestamosApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPrestamosApplication.class, args);
	}

}
