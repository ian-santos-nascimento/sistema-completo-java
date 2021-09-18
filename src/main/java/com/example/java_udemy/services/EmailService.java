package com.example.java_udemy.services;

import org.springframework.mail.SimpleMailMessage;

import com.example.java_udemy.domain.Pedido;


public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
