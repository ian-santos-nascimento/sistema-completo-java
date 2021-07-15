package com.example.java_udemy;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.domain.Cidade;
import com.example.java_udemy.domain.Estado;
import com.example.java_udemy.domain.Produto;
import com.example.java_udemy.repositories.CategoriaRepository;
import com.example.java_udemy.repositories.CidadeRepository;
import com.example.java_udemy.repositories.EstadoRepository;
import com.example.java_udemy.repositories.ProdutoRepository;

@SpringBootApplication
public class JavaUdemyApplication implements CommandLineRunner { //CommandLineRunner serve pra métodos iniciarem no inicio da aplciação

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	CidadeRepository cidadeRepository;
	
	@Autowired
	EstadoRepository estadoRepository;
	public static void main(String[] args) {
		SpringApplication.run(JavaUdemyApplication.class, args);
	}

	
	
	@Override
	public void run(String... args) throws Exception {
		
		//Aqui vamos colocar os objetos que vão aparecer sempre 		
		
		Categoria cat1 = new Categoria(null, "Informática");  //São nulos porque quem vai preencher o campo vai ser o BD
		Categoria cat2 = new  Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		
		//Agora as listas estão criadas, mas vazias. Para associar os Produto(p1,p2,p3) dentro dos devidos objetos(de acordo com o checklist), devo:
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Agora que fiz com que cat1 e cat2 reconhecessem suas devidas instâncias, devo fazer os Produtos reconhecerem suas categorias:
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//O responsável por salvar esses objetos no BD vai ser o CategoriaRepository, por isso nós instânciamos ali em cima
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));     //Só é possivel fazer isso por causa da extensão JpaRepository
		produtoRepository.saveAll(Arrays.asList( p1,p2,p3));
	
		Estado est1 = new Estado("Minas Gerais", null);
		Estado est2 = new Estado("São Paulo", null);
		
		Cidade c1 = new Cidade(est1, "Uberlândia", null);
		Cidade c2 = new Cidade(est2, "São Paulo", null);
		Cidade c3 = new Cidade(est2, "Campinas", null);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		//Primeiro eu salvo os estados pois as cidades dependem dos Estados
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		
	}
	
}
