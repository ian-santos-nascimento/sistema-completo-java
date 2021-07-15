package com.example.java_udemy.services.exception;

public class ObjectNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ObjectNotFoundException(String aviso) {
		super(aviso);
	}
	
	public ObjectNotFoundException(String aviso, Throwable cause) {
		super(aviso, cause);
	}
}
