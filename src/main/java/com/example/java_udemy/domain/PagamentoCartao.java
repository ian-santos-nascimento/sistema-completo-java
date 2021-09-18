package com.example.java_udemy.domain;

import javax.persistence.Entity;

import com.example.java_udemy.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

@Entity
@JsonTypeName("pagamentoCartao")
public class PagamentoCartao extends Pagamento {
	private static final long serialVersionUID = 1L;

	
	private Integer numeroParcelas;
	
	public PagamentoCartao() {
		
	}

	public PagamentoCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroParcelas) {
		super(id, estado, pedido);
		this.numeroParcelas = numeroParcelas;
		// TODO Auto-generated constructor stub
	}

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PagamentoCartao [numeroParcelas=");
		builder.append(numeroParcelas);
		builder.append("]");
		return builder.toString();
	}
	
	

}
