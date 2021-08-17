package com.example.java_udemy.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.example.java_udemy.services.validation.ClienteInsert;


@ClienteInsert(message = "Este CPF não existe")
public class ClienteNewDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	@NotBlank(message = "Preencha este campo!")
	@Length(min = 5, max = 80, message = "Mínimo de 3 letras e máixmo de 40!")
	private String nome;
	
	@NotBlank(message = "Preencha este campo!")
	@Email(message = "Email inválido")
	private String email;
	
	private String cpf_Ou_Cnpj;
	
	private Integer tipoCliente;

	@NotBlank(message = "Preencha este campo!")
	private String logradouro;
	
	@NotBlank(message = "Preencha este campo!")
	private String numero;
	private String complemento;
	private String bairro;
	
	@NotBlank(message = "Preencha este campo!")
	private String cep;
	
	private String telefone;
	private Integer cidadeId;
	
	public ClienteNewDTO() {
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

	
	public String getCpf_Ou_Cnpj() {
		return cpf_Ou_Cnpj;
	}

	
	public void setCpf_Ou_Cnpj(String cpf_Ou_Cnpj) {
		this.cpf_Ou_Cnpj = cpf_Ou_Cnpj;
	}

	
	public Integer getTipoCliente() {
		return tipoCliente;
	}

	
	public void setTipoCliente(Integer tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

	
	public String getLogradouro() {
		return logradouro;
	}

	
	
	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	
	public String getNumero() {
		return numero;
	}

	
	public void setNumero(String numero) {
		this.numero = numero;
	}

	
	public String getComplemento() {
		return complemento;
	}

	
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	
	public String getBairro() {
		return bairro;
	}

	
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	
	public String getCep() {
		return cep;
	}

	
	public void setCep(String cep) {
		this.cep = cep;
	}

	
	public String getTelefone() {
		return telefone;
	}

	
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	public Integer getCidadeId() {
		return cidadeId;
	}

	
	public void setCidadeId(Integer cidadeId) {
		this.cidadeId = cidadeId;
	}
	
	
}
