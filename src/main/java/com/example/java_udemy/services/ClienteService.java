package com.example.java_udemy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.java_udemy.domain.Cidade;
import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.Endereco;
import com.example.java_udemy.domain.enums.TipoCliente;
import com.example.java_udemy.dto.ClienteDTO;
import com.example.java_udemy.dto.ClienteNewDTO;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.repositories.EnderecoRepository;
import com.example.java_udemy.services.exception.DataIntegrityException;
import com.example.java_udemy.services.exception.ObjectNotFoundException;

@Service

public class ClienteService {

	 @Autowired
	private ClienteRepository clienterepo;
	 
	 @Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;
	 
	 @Autowired
	 private EnderecoRepository enderecoRepository;
	 
	public Cliente find(Integer id) {
		 Optional<Cliente> cliente = clienterepo.findById(id);
		 return cliente.orElseThrow( () -> new ObjectNotFoundException(
				 "Não foi possível encontrar esse cliente. Id: " + id + "Cliente" + Cliente.class.getName()));
	}
	 
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);  //Fazer isso pois se não for nulo, ele irá pensar que é uma atualização e não novo elemento
		obj = clienterepo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		
		return clienterepo.save(newObj);
	}
	
	
	public void delete(Integer id) {
		find(id);
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
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
	}
	
	public Cliente fromDTO(ClienteNewDTO objDTO) {
		Cliente cliente = new Cliente(null, objDTO.getNome(), objDTO.getEmail(),objDTO.getCpf_Ou_Cnpj(),TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode(objDTO.getSenha()));
		Cidade cidade = new Cidade(null, null, objDTO.getCidadeId());
		Endereco endereco = new Endereco(null, objDTO.getNome(), objDTO.getNumero(),objDTO.getComplemento(), objDTO.getBairro(),objDTO.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(objDTO.getNumero());
		if (objDTO.getTelefone()!=null) {
			cliente.getTelefones().add(objDTO.getTelefone());
		}
		
		return cliente;
	}
	
	
	private void updateData (Cliente newObj,Cliente obj ) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
}
