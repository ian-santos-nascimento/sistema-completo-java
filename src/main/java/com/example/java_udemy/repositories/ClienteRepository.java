package com.example.java_udemy.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.java_udemy.domain.Cliente;

//Está camada é a de Acesso ao Banco de Dados
//Para acessar os dados da Tabela Categorias do Bd, no SpringBoot vou fazer a anotação "@Repository", mudar a classe para interface e extender-la com JpaRepository

@Repository                                          //Tipo do objeto-Tipo do atributo identificador do objeto
public interface ClienteRepository extends JpaRepository <Cliente, Integer>  {
	 
	@Transactional(readOnly = true)
	Cliente findByEmail(String email); 
}
