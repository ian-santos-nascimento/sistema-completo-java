package com.example.java_udemy.resources;


import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.dto.CategoriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.java_udemy.domain.Pedido;
import com.example.java_udemy.services.PedidoService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoservice;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = pedidoservice.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert (@Valid @RequestBody Pedido obj){  //RequestBody vai fazer com que o Json seja convertido num Objeto
		if(obj.getInstante() == null){
			obj.setInstante(new Date());
		}
		 pedidoservice.insert(obj);
		java.net.URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") //O fromCurrentRequest se refere ao request  da classe ((value="/pedidos/")
				.buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
	