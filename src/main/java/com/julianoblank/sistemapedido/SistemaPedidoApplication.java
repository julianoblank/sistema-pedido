package com.julianoblank.sistemapedido;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.julianoblank.sistemapedido.domain.Categoria;
import com.julianoblank.sistemapedido.domain.Cidade;
import com.julianoblank.sistemapedido.domain.Cliente;
import com.julianoblank.sistemapedido.domain.Endereco;
import com.julianoblank.sistemapedido.domain.Estado;
import com.julianoblank.sistemapedido.domain.Produto;
import com.julianoblank.sistemapedido.domain.enums.TipoCliente;
import com.julianoblank.sistemapedido.repositories.CategoriaRepository;
import com.julianoblank.sistemapedido.repositories.CidadeRepository;
import com.julianoblank.sistemapedido.repositories.ClienteRepository;
import com.julianoblank.sistemapedido.repositories.EnderecoRepository;
import com.julianoblank.sistemapedido.repositories.EstadoRepository;
import com.julianoblank.sistemapedido.repositories.ProdutoRepository;

@SpringBootApplication
public class SistemaPedidoApplication implements CommandLineRunner{
	
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

	public static void main(String[] args) {
		SpringApplication.run(SistemaPedidoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null,"Informática");
		Categoria cat2 = new Categoria(null,"Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia",est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria da Silva", "maria@gmail.com", "12345678910", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838393"));
		
		Cliente cli2 = new Cliente(null, "João da Silva", "silvaj@gmail.com", "0321654987", TipoCliente.PESSOAFISICA);
		cli2.getTelefones().addAll(Arrays.asList("37125698", "99022369"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apto 325", "Universitário", "35620186", cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Joao", "120", "apto 312", "Universitário", "35620186", cli1, c2);
		
		Endereco e3 = new Endereco(null, "Rua Dona Flores", "632", "Casa", "Centro", "96815630", cli2, c3);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		enderecoRepository.saveAll(Arrays.asList(e1,e2,e3));
	}

}
