package com.example.java_udemy.services.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DataIntegrityException(String aviso) {
		super(aviso);
	}
	
	public DataIntegrityException(String aviso, Throwable cause) {
		super(aviso, cause);
	}
}
