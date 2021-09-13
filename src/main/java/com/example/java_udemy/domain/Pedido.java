package com.example.java_udemy.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="Pedido")
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	//Como é OneToOne, precisamos usar o cascade = CascadeType.ALL

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") //Necessário para não haver erro de entidade transiente quando for salvar o Pedido e Pagamento
	private Pagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Pedido() {
	}

	public Pedido(Integer id, Date instante , Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}


	public double getValorTotal(){
		double soma = 0.0;
		for (ItemPedido item: itens) {
			soma = soma + item.getSubTotal();
		}
		return soma;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			return other.id == null;
		} else return id.equals(other.id);
	}

//	@Override
//	public String toString() {
//		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
//		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//		StringBuilder builder = new StringBuilder();
//		builder.append("Pedido número: ");
//		builder.append(getId());
//		builder.append(", Instante: ");
//		builder.append(sdf.format(getInstante()));
//		builder.append(", Cliente: ");
//		builder.append(getCliente().getNome());
//		builder.append(", Situação do pagamento: ");
//		builder.append(getPagamento().getEstado().getDescricao());
//		builder.append("\nDetalhes:\n");
//		for (ItemPedido ip : getItens()) {
//			builder.append(ip.toString());
//		}
//		builder.append("Valor total: ");
//		builder.append(nf.format(getValorTotal()));
//		return builder.toString();
//	}

}
