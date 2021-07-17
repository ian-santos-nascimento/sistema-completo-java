package com.example.java_udemy;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.java_udemy.domain.Categoria;
import com.example.java_udemy.domain.Cidade;
import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.Endereco;
import com.example.java_udemy.domain.Estado;
import com.example.java_udemy.domain.Produto;
import com.example.java_udemy.domain.enums.TipoCliente;
import com.example.java_udemy.repositories.CategoriaRepository;
import com.example.java_udemy.repositories.CidadeRepository;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.repositories.EnderecoRepository;
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
	
	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	EnderecoRepository enderecoRepository;
	
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
		
		Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "12331451287", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323","93838393"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 203", "Jardim", "38220834", cli1, c1) ;
		Endereco e2 = new Endereco(null,"Avenida Matos", "105", "Sala 800", "Centro","39777012", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1)); //Como o Cliente é independente do Endereço, ele é salvo primeiro 
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
	}
	
}
