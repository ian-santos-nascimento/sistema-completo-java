package com.example.java_udemy.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class MockMailService extends AbstractEmailService {
	private static final Logger LOG = LoggerFactory.getLogger(MockMailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("EMAIL PADRÂO");
		LOG.info(msg.toString());
		// TODO Auto-generated method stub
		
	}

}
