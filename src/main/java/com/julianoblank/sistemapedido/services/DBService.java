package com.julianoblank.sistemapedido.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.julianoblank.sistemapedido.domain.Categoria;
import com.julianoblank.sistemapedido.domain.Cidade;
import com.julianoblank.sistemapedido.domain.Cliente;
import com.julianoblank.sistemapedido.domain.Endereco;
import com.julianoblank.sistemapedido.domain.Estado;
import com.julianoblank.sistemapedido.domain.ItemPedido;
import com.julianoblank.sistemapedido.domain.Pagamento;
import com.julianoblank.sistemapedido.domain.PagamentoComBoleto;
import com.julianoblank.sistemapedido.domain.PagamentoComCartao;
import com.julianoblank.sistemapedido.domain.Pedido;
import com.julianoblank.sistemapedido.domain.Produto;
import com.julianoblank.sistemapedido.domain.enums.EstadoPagamento;
import com.julianoblank.sistemapedido.domain.enums.TipoCliente;
import com.julianoblank.sistemapedido.repositories.CategoriaRepository;
import com.julianoblank.sistemapedido.repositories.CidadeRepository;
import com.julianoblank.sistemapedido.repositories.ClienteRepository;
import com.julianoblank.sistemapedido.repositories.EnderecoRepository;
import com.julianoblank.sistemapedido.repositories.EstadoRepository;
import com.julianoblank.sistemapedido.repositories.ItemPedidoRepository;
import com.julianoblank.sistemapedido.repositories.PagamentoRepository;
import com.julianoblank.sistemapedido.repositories.PedidoRepository;
import com.julianoblank.sistemapedido.repositories.ProdutoRepository;

@Service
public class DBService {
	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Cliente cli2 = new Cliente(null, "João da Silva", "silvaj@gmail.com", "0321654987", TipoCliente.PESSOAFISICA);
		cli2.getTelefones().addAll(Arrays.asList("37125698", "99022369"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apto 325", "Universitário", "35620186", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Joao", "120", "apto 312", "Universitário", "35620186", cli1, c2);

		Endereco e3 = new Endereco(null, "Rua Dona Flores", "632", "Casa", "Centro", "96815630", cli2, c3);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:33"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.0);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}
	
	public void instantiateDevDatabase() throws ParseException {

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));

		Cliente cli2 = new Cliente(null, "João da Silva", "silvaj@gmail.com", "0321654987", TipoCliente.PESSOAFISICA);
		cli2.getTelefones().addAll(Arrays.asList("37125698", "99022369"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apto 325", "Universitário", "35620186", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Joao", "120", "apto 312", "Universitário", "35620186", cli1, c2);

		Endereco e3 = new Endereco(null, "Rua Dona Flores", "632", "Casa", "Centro", "96815630", cli2, c3);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:33"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"),
				null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.0);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
