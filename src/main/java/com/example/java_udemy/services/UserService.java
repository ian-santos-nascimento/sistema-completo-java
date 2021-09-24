package com.example.java_udemy.services;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.java_udemy.security.JWTAuthenticationFilter;
import com.example.java_udemy.security.UserSS;

public class UserService {

		
	private JWTAuthenticationFilter authenticationFilter;
	/**
	 * Metodo que adquire o usuario logado na sessão
	 * @return caso exista, retorna o usuario do tipo UserSS, caso contrário, retorna null
	 */
	public static UserSS authenticated() {
		try {			
			UserSS userSS;
			SecurityContext context = SecurityContextHolder.createEmptyContext();
			return (UserSS) context.getAuthentication().getAuthorities();
		}catch(Exception e) {
			return null;
		}
	}
}
