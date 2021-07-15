package com.example.java_udemy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

//Lá na Checlist eu veo que é uma associação de 1(ou mais) categoria, para muitos Produtos, então vamos criar uma lista lá.
//Como posso ter mais de uma categoria, então vou criar uma lista aqui também de Categoria
	
 @Entity
public class Produto implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	private double preço;

	
	@JsonBackReference                                //Omiti a lista  de categorias para cada produto pra não ocorrer um erro cíclico
	@ManyToMany    		                                  //Fazer essa anotação em apenas um dos objetos que vou relacionar
	@JoinTable(name="PRODUTO-CATEGORIA",			       //Para relacionar um muitos pra muitos, é criada uma terceira tabela entre as duas que quero relacioanr, usando JoinTable
		joinColumns = @JoinColumn(name = "produto_id"),       //Chave estrangeira do produto
		inverseJoinColumns = @JoinColumn(name= "categoria_id")) //Chave estrangeira da categoria    
	
	private List<Categoria>categorias= new ArrayList<>();
	
	
	public Produto() {
		
	}


	public Produto(Integer id, String nome, double preço) { //Como a categorias ja foi iniciada, ela ja entra no construtor
		super();
		this.id = id;
		this.nome = nome;
		this.preço = preço;
	}


	public List<Categoria> getCategorias() {
		return categorias;
	}


	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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


	public double getPreço() {
		return preço;
	}


	public void setPreço(double preço) {
		this.preço = preço;
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
		Produto other = (Produto) obj;
		return Objects.equals(id, other.id);
	}


	
	
}
