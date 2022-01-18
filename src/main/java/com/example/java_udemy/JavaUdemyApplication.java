package com.example.java_udemy;


import com.example.java_udemy.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaUdemyApplication implements CommandLineRunner { //CommandLineRunner serve pra métodos iniciarem no inicio da aplciação

	@Autowired
	private S3Service s3Service;
	
	public static void main(String[] args) {
		SpringApplication.run(JavaUdemyApplication.class, args);
	}

	
	
	@Override
	public void run(String... args) throws Exception {
		s3Service.uploadFile("C:\\Users\\Ian\\Desktop\\imagem.png");

	}
	
}
