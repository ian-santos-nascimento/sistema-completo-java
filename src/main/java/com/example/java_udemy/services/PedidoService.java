package com.example.java_udemy.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.java_udemy.domain.Cliente;
import com.example.java_udemy.domain.ItemPedido;
import com.example.java_udemy.domain.PagamentoComBoleto;
import com.example.java_udemy.domain.Pedido;
import com.example.java_udemy.domain.enums.EstadoPagamento;
import com.example.java_udemy.repositories.ItemRepository;
import com.example.java_udemy.repositories.PagamentoRepository;
import com.example.java_udemy.repositories.PedidoRepository;
import com.example.java_udemy.security.UserSS;
import com.example.java_udemy.services.exception.AuthorizationException;
import com.example.java_udemy.services.exception.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private ItemRepository itemPedidoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		if (obj.getInstante() == null) {
			obj.setInstante(new Date());
		}
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = pedidoRepository.save(obj);

		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));

			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		emailService.sendOrderConfirmationEmail(obj);

		return obj;
	}

	/**
	 * Método que verifica se o usuário está logado e cria uma página de seus
	 * pedidos
	 * 
	 * @param page_id
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return retorna uma página de pedidos do usuário que está logado
	 */
	public Page<Pedido> findPage(Integer page_id, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado para buscar pedidos");
		}

		PageRequest pageRequest = PageRequest.of(page_id, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteService.find(user.getId());
		return pedidoRepository.findByCliente(cliente, pageRequest);
	}
}
