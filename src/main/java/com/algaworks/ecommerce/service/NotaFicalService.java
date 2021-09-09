package com.algaworks.ecommerce.service;

import com.algaworks.ecommerce.model.Pedido;

public class NotaFicalService {

	public void gerar(Pedido pedido) {
		System.out.println("Gerando nota para o pedido " + pedido.getId());
	}
}
