package com.julianoblank.sistemapedido.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julianoblank.sistemapedido.domain.ItemPedido;
import com.julianoblank.sistemapedido.domain.PagamentoComBoleto;
import com.julianoblank.sistemapedido.domain.Pedido;
import com.julianoblank.sistemapedido.domain.enums.EstadoPagamento;
import com.julianoblank.sistemapedido.repositories.ItemPedidoRepository;
import com.julianoblank.sistemapedido.repositories.PagamentoRepository;
import com.julianoblank.sistemapedido.repositories.PedidoRepository;
import com.julianoblank.sistemapedido.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException
				("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento(); // garanto que o pagto é do tipo boleto
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante()); // setar a data de vencimento para uma semana.
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	} 
	
}
