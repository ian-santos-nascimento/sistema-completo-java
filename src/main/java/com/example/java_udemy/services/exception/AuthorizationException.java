package com.example.java_udemy.services.exception;

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthorizationException(String aviso) {
		super(aviso);
	}
	
	public AuthorizationException(String aviso, Throwable cause) {
		super(aviso, cause);
	}
}
