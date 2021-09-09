package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Cliente_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.metalmodel.Produto_;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class ExpressoesCondicionaisCriteriaTest extends EntityManagerTest {
	
	@Test
	public void usarExpressionIN02() {
		Cliente cliente01 = entityManager.find(Cliente.class, 1);
		
		Cliente cliente02 = new Cliente();
		cliente02.setId(2);
		
		List<Cliente> clientes = Arrays.asList(cliente01, cliente02);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(root.get(Pedido_.CLIENTE).in(clientes));
		
		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Pedido_.TOTAL), new BigDecimal(499)));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressionIN01() {
		List<Integer> ids = Arrays.asList(1, 2, 4, 6);
		
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(root.get(Pedido_.ID).in(ids));
		
		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Pedido_.TOTAL), new BigDecimal(499)));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarExpressaoCase() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
//		criteriaQuery.multiselect(
//				root.get(Pedido_.ID),
//				criteriaBuilder.selectCase(root.get(Pedido_.STATUS))
//					.when(StatusPedido.PAGO.toString(), "Foi pago")
//					.when(StatusPedido.CANCELADO.toString(), "Foi cancelado")
//					.otherwise(root.get(Pedido_.STATUS))
//		);

		criteriaQuery.multiselect(
				root.get(Pedido_.ID),
				criteriaBuilder.selectCase(root.get(Pedido_.PAGAMENTO).type().as(String.class))
				.when("boleto", "Foi pago com boleto")
				.when("cartao", "Foi pago com cartão")
				.otherwise("não identificado")
				);
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
    }
	
	@Test
	public void usarExpressaoDiferente() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.notEqual(root.get(Pedido_.TOTAL), new BigDecimal(499)));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
	}

	@Test
	public void usarBetween() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
//		criteriaQuery.where(criteriaBuilder.between(root.get(Pedido_.TOTAL), new BigDecimal(499), new BigDecimal(2398)));
		criteriaQuery.where(criteriaBuilder.between(root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().minusDays(10), LocalDateTime.now()));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
	}

	@Test
	public void usarMaiorMenorComDatas() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
        Root<Pedido> root = criteriaQuery.from(Pedido.class);

        criteriaQuery.select(root);

        criteriaQuery.where(
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().minusDays(3)));

        TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
        List<Pedido> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());
	}
	
	@Test
	public void UsarMaiorMenor() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);

//		Maior que
//		criteriaQuery.where(criteriaBuilder.greaterThan(root.get(Produto_.PRECO), new BigDecimal("799.00")));
		
//		Maior ou igual
//		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Produto_.PRECO), new BigDecimal("799.00")));
		
//		menor que
//		criteriaQuery.where(criteriaBuilder.lessThan(root.get(Produto_.PRECO), new BigDecimal("3500.00")));

		criteriaQuery.where(criteriaBuilder.greaterThanOrEqualTo(root.get(Produto_.PRECO), new BigDecimal("799")),
				criteriaBuilder.lessThanOrEqualTo(root.get(Produto_.PRECO), new BigDecimal("3500.00")));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Nome: " + p.getNome() + ", Preço: " + p.getPreco()));
	}

	@Test
	public void usarIsEmpty() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
//		isEmpty
//		criteriaQuery.where(criteriaBuilder.isEmpty(root.get(Produto_.CATEGORIAS)));
		
//		isNotEmpty
		criteriaQuery.where(criteriaBuilder.isNotEmpty(root.get(Produto_.CATEGORIAS)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarIsNull() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
//		isNull
//		criteriaQuery.where(root.get(Produto_.FOTO).isNull());
		criteriaQuery.where(criteriaBuilder.isNull(root.get(Produto_.FOTO)));

		
//		isNotNull
//		criteriaQuery.where(root.get(Produto_.FOTO).isNotNull());
//		criteriaQuery.where(criteriaBuilder.isNotNull(root.get(Produto_.FOTO)));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressaoCondicionalLike() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.like(root.get(Cliente_.NOME), "%a%"));
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Cliente> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
}
