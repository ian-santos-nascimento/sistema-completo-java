package com.example.java_udemy.services;
//Esta é a camada que vai ser a Camada de Serviço, que vai servir a Camada Rest

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.repositories.CategoriaRepository;
import com.example.java_udemy.services.exception.ObjectNotFoundException;
//import com.example.java_udemy.services.exception.ObjectNotFoundException;

 

@Service
public class CategoriaService {
	//Como implementar um serviço que busca uma categoria? Ele vai ter que chamar uma operação do Objeto CategoriaRepository
	//Como instanciar o repositório? Usar o @Autowired, o SpringBoot vai instânciar no primeiro elemento abaixo dele automaticamente
	
	@Autowired
	private CategoriaRepository repo ;   
	
	public Categoria buscar(Integer id) {  //Vai buscar a Categoria por código(Id)
		Optional <Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Não encontrado. Id: "+ id + "tipo: "+ Categoria.class.getName()));
				
	}
	//
}
