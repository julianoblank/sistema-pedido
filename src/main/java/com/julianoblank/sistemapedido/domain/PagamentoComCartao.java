package com.julianoblank.sistemapedido.domain;

import javax.persistence.Entity;

import com.julianoblank.sistemapedido.domain.enums.EstadoPagamento;


@Entity
public class PagamentoComCartao extends Pagamento{
	
	private Integer numeroDeParcelas;
	
	private static final long serialVersionUID = 1L;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	
	

}
