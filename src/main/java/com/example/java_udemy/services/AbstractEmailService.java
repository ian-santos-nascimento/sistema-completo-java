package com.example.java_udemy.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.Pedido;

/**
 * Classe responsável para configuração do envio de emails
 * @author Ian
 *
 */
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSImpleMailMessageFromPedido(obj);
		sendEmail(sm);
		
	}
/**
 * Método que configura de quem, para quem, o que, e o corpo do envio da confirmação do pedido
 * @param obj
 * @return
 */
	protected  SimpleMailMessage prepareSImpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(sender);
		sm.setSubject("PEDIDO CONFIRMADO" + "ID: "+obj.getId());
		sm.setSentDate(obj.getInstante());
		sm.setText(obj.toString());
		
		return sm;
	}
	
	/**
	 * Este método vai enviar um email com a nova senha para o cliente recuperar
	 */
	public void sendNewPasswordEmail(Cliente cliente,String  newPassword) {
		SimpleMailMessage sm = prepareNewPassowordEmail(cliente, newPassword);
		sendEmail(sm);
	}

	/**
	 *  Método que configura de quem, para quem, o que, e o corpo do envio da confirmação do pedido
	 * @param cliente Cliente para quem será enviado o email
	 * @param newPassword	Senha criada para o cliente
	 * @return O email que será enviado
	 */
	protected SimpleMailMessage prepareNewPassowordEmail(Cliente cliente, String newPassword) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setText("Nova senha:" + newPassword);
		return sm;
	}
	
}
