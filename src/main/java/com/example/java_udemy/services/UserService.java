package com.example.java_udemy.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.java_udemy.security.UserSS;

public class UserService {

	/**
	 * Metodo que adquire o usuario logado na sessão
	 * @return caso exista, retorna o usuario do tipo UserSS, caso contrário, retorna null
	 */
	public static UserSS authenticated() {
		try {			
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}catch(Exception e) {
			return null;
		}
	}
}
