package com.example.java_udemy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.security.UserSS;


//É a classe de servicço do usuario do UserDetais.AO puxar um UserDetailsService, ela será reconhecida
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository repo;

	
	//Classe do SpringSecurity responsável por buscar o usuário
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cliente = repo.findByEmail(email);
		if(cliente ==null) {
			throw new UsernameNotFoundException(email); 
		}
		
		return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfil());
	}

}
