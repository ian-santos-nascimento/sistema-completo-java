package com.example.java_udemy.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.java_udemy.domain.Cliente;

public class ClienteDTO implements Serializable{
	
	private Integer id;
	
	@NotBlank(message = "Preencha este campo!")
	@Length(min = 5, max = 80, message = "Mínimo de 3 letras e máixmo de 40!")
	private String nome;
	
	@NotBlank(message = "Preencha este campo!")
	@Email(message = "Email inválido")
	private String email;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO(Cliente cliente) {
		super();
		this.id = cliente.getId();
		this.nome = cliente.getNome();
		this.email = cliente.getEmail();
	}


	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
