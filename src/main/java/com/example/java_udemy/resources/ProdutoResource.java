package com.example.java_udemy.resources;
//Esta camada é a de Controladores Rest, ela vai conversar com os Objetos de Serviço e obter deles uma categoria


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.java_udemy.domain.Produto;
import com.example.java_udemy.dto.ProdutoDTO;
import com.example.java_udemy.resources.utils.URL;
import com.example.java_udemy.services.ProdutoService;

  
@RestController
@RequestMapping(value="/produtos") //Isso é um handler de url, ou seja ela quem lida com o url, ela suporta métodos Post, Get, Put, Delete e Pacth
public class ProdutoResource {
	
	/*Como é uma operação/requisição básica se usa o GET, Se fosse salvar usaria o POST
	como fazer o operador buscar uma categoria? Usando o value ="/{id}" no @RequestMapping
	agora o endpoint do metodo vai ser value ="/categorias/id"
	Para fazer o id do RequestMapping ir para o arg do "find(Integer id), deve usar o @PathVariable
	O tipo ResponseEntity é um tipo especial do SpringBoost que armazena informações de uma resposta HTTP para um serviço REST
	*/
	
	
	@Autowired
	private ProdutoService service;   //Isso serve para acessar o serviço e lá o metodo vai acessar o objeto de acesso aos dados(repo)
	
	
	@RequestMapping( value ="/{id}", method=RequestMethod.GET)  
	
	 //Esse @ResponseBody é para dizer para o metodo retornar um valor que está de acordo com o body da resposta HTTP
	public @ResponseBody ResponseEntity<Produto> find(@PathVariable Integer id) {   //Para o "id" do value ="/{id}" ir para o Integer id, usamos o @PathVariable 
		
		Produto obj = service.find(id);
		
		return  ResponseEntity.ok().body(obj);  //Se estiver ok ele vai retornar como corpo o objeto obj(Que é a categoria)
		
	}

	

	@RequestMapping(value="/page", method=RequestMethod.GET)  
 	public @ResponseBody ResponseEntity<Page<ProdutoDTO>> findPage(
 			@RequestParam(value="nome", defaultValue=" ") String nome, 
			@RequestParam(value="categorias", defaultValue=" ") String categorias, 
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC") String direction) {   
		String nomeDecoded = URL.decodeParam(nome);   //Serve pra transformar uma string com espaços em uma de caracteres normais
		List<Integer> ids = URL.decodeIntList(categorias);    //Vai transformar a String que contém o numero das categorias em Integer 
		Page<Produto> lista = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listaDTO = lista.map( element -> new ProdutoDTO(element));  //Cada obj da lista do tipo Produto vai ser transformada em tipo ProdutoDTO
		
		return  ResponseEntity.ok().body(listaDTO);  
	}
}
	
 