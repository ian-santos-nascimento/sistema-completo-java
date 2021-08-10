package com.example.java_udemy.dto;

import java.io.Serializable;

public class ClienteNewDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String nome;
	private String email;
	private String cpf_Ou_Cnpj;
	private Integer tipoCliente;
	
	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;
	
	private String telefone;
	private Integer cidadeId;
	
	public ClienteNewDTO() {
	}

	public ClienteNewDTO(String nome, String email, String cpf_Ou_Cnpj, Integer tipoCliente, String logradouro,
			String numero, String complemento, String bairro, String cep, String telefone, Integer cidadeId) {
		super();
		this.nome = nome;
		this.email = email;
		this.cpf_Ou_Cnpj = cpf_Ou_Cnpj;
		this.tipoCliente = tipoCliente;
		this.logradouro = logradouro;
		this.numero = numero;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cep = cep;
		this.telefone = telefone;
		this.cidadeId = cidadeId;
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
