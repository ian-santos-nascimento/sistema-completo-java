package com.example.java_udemy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Estado implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	
	@OneToMany(mappedBy = "estado")
	private List<Cidade> cidades = new ArrayList<>();   //Dentro de um Estado, existem várias cidades
	
	private String nome;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	
	public Estado() {
		
	}
	
	
	public Estado(String nome, Integer id) {
		super();
		this.nome = nome;
		this.id = id;
	}

	
	public List<Cidade> getCidades() {
		return cidades;
	}


	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estado other = (Estado) obj;
		return Objects.equals(id, other.id);
	}
	
	
}
