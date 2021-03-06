package com.example.java_udemy.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.dto.ClienteDTO;
import com.example.java_udemy.dto.ClienteNewDTO;
import com.example.java_udemy.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(value = "/email", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@RequestParam(value = "value")String email){
		Cliente cliente = service.findByEmail(email);
		return ResponseEntity.ok().body(cliente);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDTO) { // RequestBody vai fazer com que o
																					// Json seja convertido num Objeto
		Cliente obj = service.fromDTO(objDTO);
		service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}

	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}


	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	

	// Para retornar todas os Clientes paginadas
	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<ClienteDTO>> findAll() {

		List<Cliente> lista = service.findAll();
		List<ClienteDTO> listaDTO = lista.stream().map(element -> new ClienteDTO(element)).collect(Collectors.toList()); 

		return ResponseEntity.ok().body(listaDTO);
	}

	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(name = "page_id", defaultValue = "0") Integer page_id,
			@RequestParam(name = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(name = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(name = "page_direction", defaultValue = "asc") String direction) {

		Page<Cliente> lista = service.findPage(page_id, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listaDTO = lista.map(element -> new ClienteDTO(element)); // Cada obj da lista do tipo Cliente
																					// vai ser transformada em tipo
																					// ClienteDTO

		return ResponseEntity.ok().body(listaDTO);
	}

	@RequestMapping(value = "/picture", method = RequestMethod.POST)
	public ResponseEntity<Void> insertPicture(@RequestParam(name = "file") MultipartFile multipartFile){
		URI uri = service.uploadProfilePicture(multipartFile);
		return ResponseEntity.created(uri).build();
	}

}
