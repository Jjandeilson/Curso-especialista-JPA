package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class PathExpressionTest extends EntityManagerTest {
	
	@Test
	public void buscarPedidosComProdutoEspecifico() {
//		Minha resposta
		String jpql = "select it.pedido from ItemPedido it inner join it.produto pro where pro.id = 1";
		
//		Resolução do curso
//		String jpql = "select p from Pedido p join p.itens i where i.id.produtoId = 1";
//      String jpql = "select p from Pedido p join p.itens i where i.produto.id = 1";
//      String jpql = "select p from Pedido p join p.itens i join i.produto pro where pro.id = 1";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		assertTrue(lista.size() == 2);
	}

	@Test
	public void usarPathExpression() {
		String jpql = "select p.cliente.nome from Pedido p";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
}
