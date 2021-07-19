package com.example.java_udemy.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	@Autowired
	private ClienteService clienteservice;
	
	@RequestMapping(value= "/{id}",method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<?> retorno (@PathVariable Integer id){
		Cliente obj = clienteservice.buscar(id);
		return ResponseEntity.ok().body(obj);
		
	}
}
