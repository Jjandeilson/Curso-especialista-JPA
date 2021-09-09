package com.algaworks.ecommerce.conhecendoentitymanager;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPedido;

public class LIstenersTest extends EntityManagerTest {
	
	@SuppressWarnings("unused")
	@Test
	public void carregarEntidades() {
		Produto produto = entityManager.find(Produto.class, 1);
		Pedido pedido = entityManager.find(Pedido.class, 1);
	}

	@Test
	public void acionarCallBacks() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		pedido.setTotal(BigDecimal.TEN);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.flush();
		
		pedido.setStatus(StatusPedido.PAGO);
		
		entityManager.getTransaction() .commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertNotNull(pedidoVerificacao.getDataCriacao());
		assertNotNull(pedidoVerificacao.getDataUltimaAtualizacao());
	}
}
