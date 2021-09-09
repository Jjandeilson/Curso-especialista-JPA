package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Categoria_;
import com.algaworks.ecommerce.metalmodel.ItemPedidoId_;
import com.algaworks.ecommerce.metalmodel.ItemPedido_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.metalmodel.Produto_;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class SubQueriesCriteriaTest extends EntityManagerTest {

	@Test
	public void pesquisarComAllExercicio() {
//	        Todos os produtos que sempre foram vendidos pelo mesmo preço.

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);

		criteriaQuery.select(root.get(ItemPedido_.PRODUTO));
		criteriaQuery.distinct(true);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.PRECO_PRODUTO));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root.get(ItemPedido_.PRODUTO)),
				criteriaBuilder.notEqual(subqueryRoot, root));

		criteriaQuery.where(criteriaBuilder.equal(root.get(ItemPedido_.PRECO_PRODUTO), criteriaBuilder.all(subquery)));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisaromAny02() {
//		Todos os produtos que já foram vendidos por um preco diferente do atual
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.PRECO_PRODUTO));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root));

		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Produto_.PRECO), criteriaBuilder.any(subquery)));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisarComAny01() {
//		Todoso os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.PRECO_PRODUTO));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.PRECO), criteriaBuilder.any(subquery)));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void perquisarComAll02() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.PRECO_PRODUTO));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Produto_.PRECO), criteriaBuilder.all(subquery)),
				criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void perquisarComAll01() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(subqueryRoot.get(ItemPedido_.PRECO_PRODUTO));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.PRECO), criteriaBuilder.all(subquery)));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void perquisarComExistsExercicio() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root),
				criteriaBuilder.notEqual(subqueryRoot.get(ItemPedido_.PRECO_PRODUTO), root.get(Produto_.PRECO)));

		criteriaQuery.where(criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisarComINExercicio() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.PRODUTO);
		Join<Produto, Categoria> subqueryJoinProdutoCategoria = subqueryJoinProduto.join(Produto_.CATEGORIAS);
		subquery.select(subqueryRoot.get(ItemPedido_.ID).get(ItemPedidoId_.PEDIDO_ID));
		subquery.where(criteriaBuilder.equal(subqueryJoinProdutoCategoria.get(Categoria_.ID), 2));

		criteriaQuery.where(root.get(Pedido_.ID).in(subquery));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void perquisarComSubqueryExercicio() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		Subquery<Long> subquery = criteriaQuery.subquery(Long.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.count(criteriaBuilder.literal(1)));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(Pedido_.CLIENTE), root));

		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, 2L));

		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}

	@Test
	public void pesquisarComExists() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		subquery.select(criteriaBuilder.literal(1));
		subquery.where(criteriaBuilder.equal(subqueryRoot.get(ItemPedido_.PRODUTO), root));

		criteriaQuery.where(criteriaBuilder.exists(subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out
				.println("ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Preço: " + obj.getPreco()));
	}

	@Test
	public void pesquisarComIN() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<Integer> subquery = criteriaQuery.subquery(Integer.class);
		Root<ItemPedido> subqueryRoot = subquery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> subqueryJoinPedido = subqueryRoot.join(ItemPedido_.PEDIDO);
		Join<ItemPedido, Produto> subqueryJoinProduto = subqueryRoot.join(ItemPedido_.PRODUTO);
		subquery.select(subqueryJoinPedido.get(Pedido_.ID));
		subquery.where(criteriaBuilder.greaterThan(subqueryJoinProduto.get(Produto_.PRECO), new BigDecimal(100)));

		criteriaQuery.where(root.get(Pedido_.ID).in(subquery));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getTotal()));
	}

	@Test
	public void pesquisarSubqueries03() {
//        Bons clientes.

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.sum(subqueryRoot.get(Pedido_.TOTAL)));
		subquery.where(criteriaBuilder.equal(root, subqueryRoot.get(Pedido_.CLIENTE)));

		criteriaQuery.where(criteriaBuilder.greaterThan(subquery, new BigDecimal(1300)));

		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Cliente> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}

	@Test
	public void pesquisarSubqueries02() {
//	         Todos os pedidos acima da média de vendas

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Pedido> subqueryRoot = subquery.from(Pedido.class);
		subquery.select(criteriaBuilder.avg(subqueryRoot.get(Pedido_.TOTAL)).as(BigDecimal.class));

		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Pedido_.TOTAL), subquery));

		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Pedido> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Total: " + obj.getTotal()));
	}

	@Test
	public void pesquisarSubqueries01() {
//	         O produto ou os produtos mais caros da base.

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);

		criteriaQuery.select(root);

		Subquery<BigDecimal> subquery = criteriaQuery.subquery(BigDecimal.class);
		Root<Produto> subqueryRoot = subquery.from(Produto.class);
		subquery.select(criteriaBuilder.max(subqueryRoot.get(Produto_.PRECO)));

		criteriaQuery.where(criteriaBuilder.equal(root.get(Produto_.PRECO), subquery));

		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);

		List<Produto> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());

		lista.forEach(obj -> System.out
				.println("ID: " + obj.getId() + ", Nome: " + obj.getNome() + ", Preço: " + obj.getPreco()));
	}
}
