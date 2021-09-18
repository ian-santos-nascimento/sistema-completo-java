package com.example.java_udemy.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.example.java_udemy.services.DBService;
import com.example.java_udemy.services.EmailService;
import com.example.java_udemy.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {
    @Autowired
    private DBService dbService;

    @Bean
    public boolean instantiateDatabase() throws ParseException {
        try {
        dbService.instantiateTestDatabase();
        }catch (Exception e){
            e.getMessage();
        }
        return true;
    }
    
    @Bean
    public EmailService emailService() {
    	return new MockMailService();
    }
}
