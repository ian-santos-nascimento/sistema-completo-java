package com.example.java_udemy.resources;
//Esta camada é a de Controladores Rest, ela vai conversar com os Objetos de Serviço e obter deles uma categoria


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.dto.CategoriaDTO;
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
	public @ResponseBody ResponseEntity<Categoria> find(@PathVariable Integer id) {   //Para o "id" do value ="/{id}" ir para o Integer id, usamos o @PathVariable 
		
		Categoria obj = service.buscar(id);
		
		return  ResponseEntity.ok().body(obj);  //Se estiver ok ele vai retornar como corpo o objeto obj(Que é a categoria)
		
	}

	
	@RequestMapping(method = RequestMethod.POST)
	// ResponseEntity<Void> quer dizer que o body da resposta HTTP vai tá vazia
	public ResponseEntity<Void> insert (@RequestBody Categoria obj){  //RequestBody vai fazer com que o Json seja convertido num Objeto
		obj = service.insert(obj);
		java.net.URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") //O fromCurrentRequest se refere ao request  da classe ((value="/categorias")
				.buildAndExpand(obj.getId()).toUri();   
		return ResponseEntity.created(uri).build();
	}
	
	//Como eu quero que o id permaneça o mesmo, eu devo usar o value="/{id}"
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update (@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value= "/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	//Para retornar todas as categorias
	
	@RequestMapping(method=RequestMethod.GET)  
	public @ResponseBody ResponseEntity<List<CategoriaDTO>> findAll() {   
		
		List<Categoria> lista = service.findAll();
		List<CategoriaDTO> listaDTO = lista.stream().map( element ->
		new CategoriaDTO(element)).collect(Collectors.toList());  //Cada obj da lista do tipo Categoria vai ser transformada em tipo CategoriaDTO
		
		return  ResponseEntity.ok().body(listaDTO);  
	}
	@RequestMapping(value="/page", method=RequestMethod.GET)  
	public @ResponseBody ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(name = "page_id", defaultValue = "0")Integer page_id,
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(name = "orderBy", defaultValue = "nome")String orderBy, 
			@RequestParam(name = "page_direction", defaultValue = "asc")String direction) {   
		
		Page<Categoria> lista = service.findPage(page_id, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listaDTO = lista.map( element -> new CategoriaDTO(element));  //Cada obj da lista do tipo Categoria vai ser transformada em tipo CategoriaDTO
		
		return  ResponseEntity.ok().body(listaDTO);  
	}
}
	
 