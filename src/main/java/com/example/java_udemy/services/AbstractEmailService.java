package com.example.java_udemy.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.example.java_udemy.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;

	@Override

	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSImpleMailMessageFromPedido(obj);
		sendEmail(sm);
		
	}

	protected  SimpleMailMessage prepareSImpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(null);
		sm.setSubject("PEDIDO CONFIRMADO" + "ID: "+obj.getId());
		sm.setSentDate(obj.getInstante());
		sm.setText(obj.toString());
		
		return sm;
	}
	
}
