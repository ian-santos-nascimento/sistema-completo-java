package com.example.java_udemy.domain;

//Esta é a classe da camada de Dominio
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;


@Entity(name="Categoria")                                 //Pra indicar que vai ser a entidade do JPA
public class Categoria implements Serializable { 
   //Serializable serve para converter os objetos da classe em sequência de bytes, ou seja,permite os objetos serem gravados em arquivos e trafegar em rede

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Estou definindo a estratégia de geração automatica do Id's das categorias
	private Integer id;
	@Column(name="nome")
	private String nome; 
	
	//@JsonManagedReference                //Fazer isso no objeto aonde eu quero receber os objetos associados. att: Tem bug, então retirei
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos = new ArrayList<>();
	
	public Categoria() {
		
	}

	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
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
	public List<Produto> getProdutos() {
		return produtos;
	}
	
	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
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
		Categoria other = (Categoria) obj;
		return Objects.equals(id, other.id);
	}

	
}
