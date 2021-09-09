package com.algaworks.ecommerce.listener;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.service.NotaFicalService;

public class GerarNotaFiscalListener {
	
	private NotaFicalService notaFiscalService = new NotaFicalService();

	@PrePersist
	@PreUpdate
	public void gerar(Pedido pedido) {
		if(pedido.isPago() && pedido.getNotaFiscal() == null) {
			notaFiscalService.gerar(pedido);
		}
	}
}
