package com.example.java_udemy.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	private static final long serialVersionUID = 1L;
	private List<FieldMessage> errors = new ArrayList<>(); 
	
	public ValidationError(Integer notFound, String mensagemError, long timeStamp) {
		super(notFound, mensagemError, timeStamp);
	
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String message) {
		errors.add(new FieldMessage());
	}
		

	
	
}
