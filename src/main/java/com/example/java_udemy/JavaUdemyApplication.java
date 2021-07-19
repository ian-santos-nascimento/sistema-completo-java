package com.example.java_udemy;

import java.text.SimpleDateFormat;
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
import com.example.java_udemy.domain.ItemPedido;
import com.example.java_udemy.domain.Pagamento;
import com.example.java_udemy.domain.PagamentoCartao;
import com.example.java_udemy.domain.PagamentoComBoleto;
import com.example.java_udemy.domain.Pedido;
import com.example.java_udemy.domain.Produto;
import com.example.java_udemy.domain.enums.EstadoPagamento;
import com.example.java_udemy.domain.enums.TipoCliente;
import com.example.java_udemy.repositories.CategoriaRepository;
import com.example.java_udemy.repositories.CidadeRepository;
import com.example.java_udemy.repositories.ClienteRepository;
import com.example.java_udemy.repositories.EnderecoRepository;
import com.example.java_udemy.repositories.EstadoRepository;
import com.example.java_udemy.repositories.ItemRepository;
import com.example.java_udemy.repositories.PagamentoRepository;
import com.example.java_udemy.repositories.PedidoRepository;
import com.example.java_udemy.repositories.ProdutoRepository;

@SpringBootApplication
public class JavaUdemyApplication implements CommandLineRunner { //CommandLineRunner serve pra métodos iniciarem no inicio da aplciação

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemRepository itemPedidoRepository;
	
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
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("29/03/2003 10:50"), cli1, e1);
		Pedido ped2 = new Pedido(null,sdf.parse("15/12/2010 11:45"), cli1, e2);
		
		Pagamento pagtp1 = new PagamentoCartao(null,EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagtp1);
		Pagamento pagtp2 = new PagamentoComBoleto(null,EstadoPagamento.PENDENTE, ped2,sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagtp2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagtp1,pagtp2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1,2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		}
	
}
