package com.example.java_udemy.resources;
//Esta camada é a de Controladores Rest, ela vai conversar com os Objetos de Serviço e obter deles uma categoria


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.services.CategoriaService;

  
@RestController
@RequestMapping(value="/categorias") //Isso é um handler de url, ou seja ela quem lida com o url, ela suporta métodos Post, Get, Put, Delete e Pacth
public class CategoriaResource {
	
	/*Como é uma operação/requisição básica se usa o GET, Se fosse salvar usaria o POST
	como fazer o operador buscar uma categoria? Usando o value ="/{id}" no @RequestMapping
	agora o endpoint do metodo vai ser value ="/categorias/id"
	Para fazer o id do RequestMapping ir para o arg do "find(Integer id), deve usar o @PathVariable
	O tipo ResponseEntity é um tipo especial do SpringBoost que armazena informações de uma resposta HTTP para um serviço REST
	*/
	
	
	@Autowired
	private CategoriaService service;   //Isso serve para acessar o serviço e lá o metodo vai acessar o objeto de acesso aos dados(repo)
	
	
	@RequestMapping( value ="/{id}", method=RequestMethod.GET)  
	
	 //Esse @ResponseBody é para dizer para o metodo retornar um valor que está de acordo com o body da resposta HTTP
	public @ResponseBody ResponseEntity<?> find(@PathVariable Integer id) {   //Para o "id" do value ="/{id}" ir para o Integer id, usamos o @PathVariable 
		
		Categoria obj = service.buscar(id);
		
		return  ResponseEntity.ok().body(obj);  //Se estiver ok ele vai retornar como corpo o objeto obj(Que é a categoria)
		
		
	}
}
