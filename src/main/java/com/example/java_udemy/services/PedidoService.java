package com.example.java_udemy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Pedido;
import com.example.java_udemy.repositories.PedidoRepository;
import com.example.java_udemy.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository itemrepo;
	
	public Pedido buscar(Integer id) {
		Optional<Pedido> obj = itemrepo.findById(id);
		return obj.orElseThrow(() ->
		 new ObjectNotFoundException("Não encontrado, Id:" + id + "Tipo: " + Pedido.class.getName()));
	}
}
