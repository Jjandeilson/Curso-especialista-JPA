package com.algaworks.ecommerce.operacoesemcascata;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class CascadeTypeRemoveTest extends EntityManagerTest {
	
	@Test
	public void removeritensOrfaos() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		assertFalse(pedido.getItens().isEmpty());
		
		entityManager.getTransaction().begin();
		pedido.getItens().clear();
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertTrue(pedidoVerificacao.getItens().isEmpty());
	}
	
	@Test
	public void removerRelacaoProdutoCategoria() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		assertFalse(produto.getCategorias().isEmpty());
		
		entityManager.getTransaction().begin();
		produto.getCategorias().clear();
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertTrue(produtoVerificacao.getCategorias().isEmpty());		
	}

//	@Test
	public void removerPedidoEItens() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		entityManager.getTransaction().begin();
		entityManager.remove(pedido); // Necessário CascadeType.REMOVE no atributo "itens".
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertNull(pedidoVerificacao);
	}
	
//	@Test
	public void removerItemPedidoEPedido() {
		ItemPedido itemPedido = entityManager.find(ItemPedido.class, new ItemPedidoId(1, 1));
		
		entityManager.getTransaction().begin();
		entityManager.remove(itemPedido); // Necessário CascadeType.REMOVE no atributo "pedido".
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, itemPedido.getPedido().getId());
		assertNull(pedidoVerificacao);
	}
}
