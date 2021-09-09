package com.algaworks.ecommerce.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class GerenciamentoTransacoesTest extends EntityManagerTest {

	@Test(expected = Exception.class)
	public void abrirFecharCancelarTransacao() {
		try {
			entityManager.getTransaction().begin(); // inicia a transa��o
			metodoDeNegocio();
			entityManager.getTransaction().commit(); // finaliza uma transa��o
		}catch(Exception e) {
			entityManager.getTransaction().rollback(); // cancela uma transa��o		
			throw e;
		}
		
	}
	
	private void metodoDeNegocio() {
		Pedido pedido = entityManager.find(Pedido.class, 1);

		pedido.setStatus(StatusPedido.PAGO);
		
		if(pedido.getPagamento() == null) {		
			throw new RuntimeException("Pedido ainda n�o foi pago");
		} 
	}
}
