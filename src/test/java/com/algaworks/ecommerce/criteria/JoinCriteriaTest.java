package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.StatusPagamento;

public class JoinCriteriaTest extends EntityManagerTest {
	
	@Test
	public void buscarPedidosComProdutoEspecifico() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
//		Join<Pedido, ItemPedido> joinIntemPedido = root.join("itens");
//		Join<ItemPedido, Produto> joinProduto = joinIntemPedido.join("produto");
		Join<ItemPedido, Produto> joinItemPedidoProduto = root.join("itens").join("produto");
		
		criteriaQuery.where(criteriaBuilder.equal(joinItemPedidoProduto.get("id"), 1));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		
		lista.forEach(p -> System.out.println("ID: " + p.getId()));
	}

	@Test
	public void usarJoinFetch() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
//		root.fetch("itens");
		root.fetch("cliente");
		root.fetch("notaFiscal", JoinType.LEFT);
		root.fetch("pagamento", JoinType.LEFT);
//		Join<Pedido, Cliente> joinCliente = (Join<Pedido, Cliente>) root.<Pedido, Cliente>fetch("cliente");
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		
		TypedQuery<Pedido>  typedQuery = entityManager.createQuery(criteriaQuery);
		
		Pedido pedido = typedQuery.getSingleResult();
		assertNotNull(pedido);
		assertFalse(pedido.getItens().isEmpty());
	}

	@SuppressWarnings("unused")
	@Test
	public void fazerLeftOuterJoin() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento", JoinType.LEFT);
		
		criteriaQuery.select(root);
		
		TypedQuery<Pedido>  typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertTrue(lista.size() == 5);
	}

	@Test
	public void fazerJoinComOn() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
		joinPagamento.on(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));
		
		criteriaQuery.select(root);
				
		TypedQuery<Pedido>  typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertTrue(lista.size() == 2);
	}

	@SuppressWarnings("unused")
	@Test
	public void fazerJoin() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join("pagamento");
//		Join<Pedido, ItemPedido> joinItens = root.join("itens");		
//		Join<ItemPedido, Produto> joinItemProduto = joinItens.join("produto");		
		
		criteriaQuery.select(root);
		
//		criteriaQuery.select(joinPagamento);
//		criteriaQuery.where(criteriaBuilder.equal(joinPagamento.get("status"), StatusPagamento.PROCESSANDO));
		
		TypedQuery<Pedido>  typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertTrue(lista.size() == 4);
	}
}
