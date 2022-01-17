package com.example.java_udemy.services;
//Esta é a camada que vai ser a Camada de Serviço, que vai servir a Camada Rest

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.dto.CategoriaDTO;
import com.example.java_udemy.repositories.CategoriaRepository;
import com.example.java_udemy.services.exception.DataIntegrityException;
import com.example.java_udemy.services.exception.ObjectNotFoundException;
//import com.example.java_udemy.services.exception.ObjectNotFoundException;

 
 
@Service
public class CategoriaService {
	//Como implementar um serviço que busca uma categoria? Ele vai ter que chamar uma operação do Objeto CategoriaRepository
	//Como instanciar o repositório? Usar o @Autowired, o SpringBoot vai instânciar no primeiro elemento abaixo dele automaticamente
	
	@Autowired
	private CategoriaRepository repo ;   
	
	public Categoria find(Integer id) {  //Vai buscar a Categoria por código(Id)
		Optional <Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Não encontrado. Id: "+ id + "tipo: "+ Categoria.class.getName()));
				
	}
	
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);  //Fazer isso pois se não for nulo, ele irá pensar que é uma atualização e não novo elemento
		return repo.save(obj);
	}
	
	
	public Categoria update(Categoria obj) {
		Categoria newobj = find(obj.getId());
		updateData(newobj, obj);
		return repo.save(newobj);
	}
	
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);			
		}
		catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não foi possível realizar essa operação de exclusão");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	
	//"Page" é uma classe especial do SpringData
	public Page<Categoria>findPage(Integer page_id, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page_id,linesPerPage,Direction.valueOf(direction),orderBy);
		return repo.findAll(pageRequest);
	}
	
	
	public Categoria fromDTO(CategoriaDTO objDTO) {
		return new Categoria(objDTO.getId(), objDTO.getNome()); 
	}
	
	
	private void updateData (Categoria newObj,Categoria obj ) {
		newObj.setNome(obj.getNome());
	}
}
