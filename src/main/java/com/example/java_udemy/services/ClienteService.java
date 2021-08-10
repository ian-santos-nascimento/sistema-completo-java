package com.example.java_udemy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.dto.ClienteDTO;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.services.exception.DataIntegrityException;
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
	 
	
	public Cliente insert(Cliente obj) {
		obj.setId(null);  //Fazer isso pois se não for nulo, ele irá pensar que é uma atualização e não novo elemento
		return clienterepo.save(obj);
	}
	
	
	public Cliente update(Cliente obj) {
		Cliente newObj = buscar(obj.getId());
		updateData(newObj, obj);
		return clienterepo.save(newObj);
	}
	
	
	public void delete(Integer id) {
		buscar(id);
		try {
			clienterepo.deleteById(id);			
		}
		catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não foi possível realizar essa operação de exclusão");
		}
	}
	
	
	public List<Cliente> findAll(){
		return clienterepo.findAll();
	}
	
//"Page" é uma classe especial do SpringData
	public Page<Cliente>findPage(Integer page_id, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page_id,linesPerPage,Direction.valueOf(direction),orderBy);
		return clienterepo.findAll(pageRequest);
	}
	
	public Cliente fromDTO(ClienteDTO objDTO) {
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
	}
	
	
	private void updateData (Cliente newObj,Cliente obj ) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
