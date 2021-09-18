package com.example.java_udemy;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaUdemyApplication implements CommandLineRunner { //CommandLineRunner serve pra métodos iniciarem no inicio da aplciação



	
	public static void main(String[] args) {
		SpringApplication.run(JavaUdemyApplication.class, args);
	}

	
	
	@Override
	public void run(String... args) throws Exception {


	}
	
}
