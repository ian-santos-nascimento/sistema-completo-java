package com.example.java_udemy.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.Pedido;

//Está camada é a de Acesso ao Banco de Dados
//Para acessar os dados da Tabela Categorias do Bd, no SpringBoot vou fazer a anotação "@Repository", mudar a classe para interface e extender-la com JpaRepository

@Repository                                          //Tipo do objeto-Tipo do atributo identificador do objeto
public interface PedidoRepository extends JpaRepository <Pedido, Integer>  {

	
	@Transactional(readOnly = true)
	Page<Pedido>findByCliente(Cliente cliente, Pageable pagebleRequest);
	
}
