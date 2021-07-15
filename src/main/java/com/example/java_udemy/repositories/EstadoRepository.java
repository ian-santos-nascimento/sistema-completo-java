package com.example.java_udemy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.java_udemy.domain.Estado;

//Está camada é a de Acesso ao Banco de Dados
//Para acessar os dados da Tabela Categorias do Bd, no SpringBoot vou fazer a anotação "@Repository", mudar a classe para interface e extender-la com JpaRepository

@Repository                                          //Tipo do objeto-Tipo do atributo identificador do objeto
public interface EstadoRepository extends JpaRepository <Estado, Integer>  {
	 
}
