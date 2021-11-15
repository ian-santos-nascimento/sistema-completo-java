package com.example.java_udemy.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.services.exception.ObjectNotFoundException;

@Service
public class AuthService {
	
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	private Random rand = new Random();
	
	/**
	 * Método que recebe o email do usuário, verifica se o email existe(se o usuário já é cadastrado), salva a nova senha do cliente no banco
	 * e retorna a nova senha para o email
	 * @param email
	 */
	public void sendNewPasswordEmail(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);
		if(cliente == null) {
			throw new ObjectNotFoundException("Email não cadastrado");
		}
		String newPassword = newPassword();
		cliente.setSenha(newPassword);
		
		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}
	
	
	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i < 10 ; i ++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

/**
 * Geração de codigo com letras e numeros
 * @return Retorna uma string que pode ser letras e/ou números
 */
	private char randomChar() { 		//Unic-code table
		int opt = rand.nextInt(3);
		if (opt == 0) { // gera um digito
			return (char) (rand.nextInt(10) + 48);
		}
		else if (opt == 1) { // gera letra maiuscula
			return (char) (rand.nextInt(26) + 65);
		}
		else { // gera letra minuscula
			return (char) (rand.nextInt(26) + 97);
		}
	}
}
