package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Cliente_;
import com.algaworks.ecommerce.metalmodel.ItemPedido_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.metalmodel.Produto_;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Pedido;

public class PathExpressionTest extends EntityManagerTest {
	
	@Test
	public void buscarPedidosComProdutoDeIgual1Exercicio() {
		 CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	     CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
	     Root<Pedido> root = criteriaQuery.from(Pedido.class);
	     Join<Pedido, ItemPedido> joinItemPedido = root.join(Pedido_.ITENS);

	     criteriaQuery.select(root);

	     criteriaQuery.where(
	             criteriaBuilder.equal(
	                     joinItemPedido.get(ItemPedido_.PRODUTO).get(Produto_.ID), 1));

	     TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
	     List<Pedido> lista = typedQuery.getResultList();

	     assertFalse(lista.isEmpty());
	}

	@Test
	public void usarPathExpression() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(
				criteriaBuilder.like(root.get(Pedido_.CLIENTE).get(Cliente_.NOME), "M%"));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}
}
 ;
 