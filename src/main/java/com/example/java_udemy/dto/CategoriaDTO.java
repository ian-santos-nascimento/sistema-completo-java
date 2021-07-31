package com.example.java_udemy.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.example.java_udemy.domain.Categoria;

//Aqui vai servir pra definir os dados que eu quero trafegar ao Fazer operações básicas de Categoria 

public class CategoriaDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	Integer id;
	@NotEmpty(message = "Esta informação é obrigatória.")
	@Length(min = 3, max = 40, message = "Mínimo de 3 letras e máixmo de 40!")
	String nome;
	
	public CategoriaDTO() {
	}
	//Ou seja, vai pegar o objeto do tipo Categoria e pegar apenas o seu id e nome
	public CategoriaDTO(Categoria obj) {   
		id = obj.getId();     //Tudo que eu quiser retornar quando buscar em /categorias, vai estar nesses atributos
		nome = obj.getNome();  
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
	
}

