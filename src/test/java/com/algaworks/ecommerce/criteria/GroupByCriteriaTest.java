package com.algaworks.ecommerce.criteria;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Categoria_;
import com.algaworks.ecommerce.metalmodel.Cliente_;
import com.algaworks.ecommerce.metalmodel.ItemPedido_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.metalmodel.Produto_;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class GroupByCriteriaTest extends EntityManagerTest {

	@Test
	public void condicionarAgrupamentoComHaving() {
//	         Total de vendas dentre as categorias que mais vendem.

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.PRODUTO);
		Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.CATEGORIAS);

		criteriaQuery.multiselect(joinProdutoCategoria.get(Categoria_.NOME),
				criteriaBuilder.sum(root.get(ItemPedido_.PRECO_PRODUTO)),
				criteriaBuilder.avg(root.get(ItemPedido_.PRECO_PRODUTO)));

		criteriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.ID));

		criteriaQuery.having(criteriaBuilder.greaterThan(
				criteriaBuilder.avg(root.get(ItemPedido_.PRECO_PRODUTO)).as(BigDecimal.class), new BigDecimal(700)));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome categoria: " + arr[0] + ", SUM: " + arr[1] + ", AVG: " + arr[2]));
	}

	@Test
	public void agruparResultadoComFuncoes() {
//	         Total de vendas por mês.

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);

		Expression<Integer> anoCriacaoPedido = criteriaBuilder.function("year", Integer.class,
				root.get(Pedido_.DATA_CRIACAO));
		Expression<Integer> mesCriacaoPedido = criteriaBuilder.function("month", Integer.class,
				root.get(Pedido_.DATA_CRIACAO));
		Expression<String> nomeMesCriacaoPedido = criteriaBuilder.function("monthname", String.class,
				root.get(Pedido_.DATA_CRIACAO));

		Expression<String> anoMesConcat = criteriaBuilder
				.concat(criteriaBuilder.concat(anoCriacaoPedido.as(String.class), "/"), nomeMesCriacaoPedido);

		criteriaQuery.multiselect(anoMesConcat, criteriaBuilder.sum(root.get(Pedido_.TOTAL)));

		criteriaQuery.groupBy(anoCriacaoPedido, mesCriacaoPedido);

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Ano/Mês: " + arr[0] + ", Sum: " + arr[1]));
	}

	@Test
	public void agruparResultado03Exercicio() {
//		Total de vendas por cliente
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<ItemPedido> root = criteriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Pedido> joinPedido = root.join(ItemPedido_.PEDIDO);
		Join<Pedido, Cliente> joinPedidoCliente = joinPedido.join(Pedido_.CLIENTE);

		criteriaQuery.multiselect(joinPedidoCliente.get(Cliente_.NOME),
				criteriaBuilder.sum(root.get(ItemPedido_.PRECO_PRODUTO)));

		criteriaQuery.groupBy(joinPedidoCliente.get(Cliente_.ID));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome cliente: " + arr[0] + ", Sum: " + arr[1]));
	}

	@Test
	public void agruparResultado02() {
//		Total de vendas por categoria.
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criateriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<ItemPedido> root = criateriaQuery.from(ItemPedido.class);
		Join<ItemPedido, Produto> joinProduto = root.join(ItemPedido_.PRODUTO);
		Join<Produto, Categoria> joinProdutoCategoria = joinProduto.join(Produto_.CATEGORIAS);

		criateriaQuery.multiselect(joinProduto.get(Categoria_.NOME),
				criteriaBuilder.sum(root.get(ItemPedido_.PRECO_PRODUTO)));

		criateriaQuery.groupBy(joinProdutoCategoria.get(Categoria_.ID));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criateriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome categoria: " + arr[0] + ", Sum: " + arr[1]));
	}

	@Test
	public void agruparResultado01() {
//		Quantidade de produtos por categoria.

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criateriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Categoria> root = criateriaQuery.from(Categoria.class);
		Join<Categoria, Produto> joinProduto = root.join(Categoria_.PRODUTOS, JoinType.LEFT);

		criateriaQuery.multiselect(root.get(Categoria_.NOME), criteriaBuilder.count(joinProduto.get(Produto_.ID)));

		criateriaQuery.groupBy(root.get(Categoria_.ID));

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criateriaQuery);
		List<Object[]> lista = typedQuery.getResultList();

		lista.forEach(arr -> System.out.println("Nome: " + arr[0] + ", Count: " + arr[1]));
	}
}
