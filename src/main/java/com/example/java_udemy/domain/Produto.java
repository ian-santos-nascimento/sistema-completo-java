package com.example.java_udemy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

//Lá na Checlist eu veo que é uma associação de 1(ou mais) categoria, para muitos Produtos, então vamos criar uma lista lá.
//Como posso ter mais de uma categoria, então vou criar uma lista aqui também de Categoria
	

@Entity
@Table(name = "Produto")
public class Produto implements Serializable {
		
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="nome")
	private String nome;
	private double preco;
	


	@JsonIgnore                              //Omiti a lista  de categorias para cada produto pra não ocorrer um erro cíclico                                //Fazer essa anotação em apenas um dos objetos que vou relacionar
	@ManyToMany


	@JoinTable(name="PRODUTOCATEGORIA",			       //Para relacionar um muitos pra muitos, é criada uma terceira tabela entre as duas que quero relacioanr, usando JoinTable
		joinColumns = @JoinColumn(name = "produto_id"),       //Chave estrangeira do produto
		inverseJoinColumns = @JoinColumn(name= "categoria_id")) //Chave estrangeira da categoria
	private List<Categoria>categorias= new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();

	public Produto() {
		
	}


	public Produto(Integer id, String nome, double preco) { //Como a categorias ja foi iniciada, ela ja entra no construtor
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	
	@JsonIgnore
	public List<Pedido> getPedidos(){
		List<Pedido> lista = new ArrayList<>();
		for(ItemPedido x: itens ) { //Pra cada item da lista itens, eu vou adiciona-la na Array lista
			lista.add(x.getPedido());
		}
		return lista;
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


	public double getPreco() {
		return preco;
	}


	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}


	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
