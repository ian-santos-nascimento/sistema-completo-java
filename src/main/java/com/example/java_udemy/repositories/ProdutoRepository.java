package com.example.java_udemy.repositories;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.domain.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	@Transactional(readOnly = true)
	//Query vai sobrepor a JPA keywords https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page <Produto> findDistinctByNomeContainingAndCategoriasIn(@Param("nome")String nome, @Param ("categorias")List<Categoria> categorias, Pageable pageRequest);
}
