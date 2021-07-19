package com.example.java_udemy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.services.exception.ObjectNotFoundException;

@Service

public class ClienteService {

	 @Autowired
	 private ClienteRepository clienterepo;
	 
	 public Cliente buscar(Integer id) {
		 Optional<Cliente> cliente = clienterepo.findById(id);
		 return cliente.orElseThrow( () -> new ObjectNotFoundException(
				 "Não foi possível encontrar esse cliente. Id: " + id + "Categoia" + Cliente.class.getName()));
		 
	 }
	 
}
