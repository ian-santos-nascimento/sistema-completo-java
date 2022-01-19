package com.example.java_udemy.resources.exception;

import javax.servlet.http.HttpServletRequest;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.example.java_udemy.services.exception.FileException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.java_udemy.services.exception.AuthorizationException;
import com.example.java_udemy.services.exception.DataIntegrityException;
import com.example.java_udemy.services.exception.ObjectNotFoundException;


@ControllerAdvice
public class ResourceExceptionalHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardError error = new StandardError (HttpStatus.NOT_FOUND.value(),"Erro de Validação", System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		StandardError error = new StandardError (HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError error = new ValidationError (HttpStatus.BAD_REQUEST.value(),"Erro de validação", System.currentTimeMillis());
		for( FieldError x : e.getBindingResult().getFieldErrors()) {
			error.addError(x.getField(),x.getDefaultMessage() );
		}
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	

	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e, HttpServletRequest request){
		StandardError error = new StandardError (HttpStatus.FORBIDDEN.value(),e.getMessage(), System.currentTimeMillis());
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
	}

	@ExceptionHandler(FileException.class)
	public ResponseEntity<StandardError> file(FileException e){
		StandardError error = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<StandardError> amazonService(AmazonServiceException e){
		HttpStatus statusCode = HttpStatus.valueOf(e.getErrorCode());
		StandardError error = new StandardError(statusCode.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(statusCode).body(error);

	}

	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<StandardError> amazonClient(AmazonClientException e){
		StandardError error =new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<StandardError> amazonS3(AmazonS3Exception e){
		StandardError error =new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}


}