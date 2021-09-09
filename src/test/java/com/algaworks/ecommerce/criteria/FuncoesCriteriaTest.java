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
import com.algaworks.ecommerce.metalmodel.PagamentoBoleto_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.PagamentoBoleto;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class FuncoesCriteriaTest extends EntityManagerTest {
	
	@Test
	public void aplicarFuncaAgregacao() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.multiselect(
				criteriaBuilder.count(root.get(Pedido_.ID)),
				criteriaBuilder.avg(root.get(Pedido_.TOTAL)),
				criteriaBuilder.sum(root.get(Pedido_.TOTAL)),
				criteriaBuilder.min(root.get(Pedido_.TOTAL)),
				criteriaBuilder.max(root.get(Pedido_.TOTAL))
				);
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
						"count: " + arr[0]
						+ ", avg: " + arr[1]
						+ ", sum: " + arr[2]
						+ ", min: " + arr[3]
						+ ", max: " + arr[4]
				));
	}

	@Test
	public void aplicarFuncaoNativas() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.multiselect(
				root.get(Pedido_.ID),
				criteriaBuilder.function("dayname", String.class, root.get(Pedido_.DATA_CRIACAO))
		);
		
		criteriaQuery.where(criteriaBuilder.isTrue(
				criteriaBuilder.function("acima_media_faturamento", Boolean.class, root.get(Pedido_.TOTAL))
		));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ", dayname: " + arr[1]
				));
	}

	@Test
	public void aplicarFuncaoColecao() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.multiselect(
				root.get(Pedido_.ID),
				criteriaBuilder.size(root.get(Pedido_.ITENS))
		);
		
		criteriaQuery.where(criteriaBuilder.greaterThan(criteriaBuilder.size(root.get(Pedido_.ITENS)), 1));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ", size: " + arr[1]
				));
	}

	@Test
	public void aplicarFuncaoNumero() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.multiselect(
				root.get(Pedido_.ID),
				criteriaBuilder.abs(criteriaBuilder.prod(root.get(Pedido_.ID), -1)),
				criteriaBuilder.mod(root.get(Pedido_.ID), 2),
				criteriaBuilder.sqrt(root.get(Pedido_.ID))
		);
		
		criteriaQuery.where(criteriaBuilder.greaterThan(criteriaBuilder.sqrt(root.get(Pedido_.TOTAL)), 10.0));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ", abs: " + arr[1]
						+ ", mod: " + arr[2]
						+ ", sqrt: " + arr[3]
		));
	}

	@Test
	public void aplicaFuncaoData() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		Join<Pedido, Pagamento> joinPagamento = root.join(Pedido_.PAGAMENTO);
		Join<Pedido, PagamentoBoleto> joinPagamentoBoleto = criteriaBuilder.treat(joinPagamento, PagamentoBoleto.class);
		
		criteriaQuery.multiselect(
				root.get(Pedido_.ID),
				criteriaBuilder.currentDate(),
				criteriaBuilder.currentTime(),
				criteriaBuilder.currentTimestamp()
		);
		
		criteriaQuery.where(
				criteriaBuilder.between(criteriaBuilder.currentDate(), root.get(Pedido_.DATA_CRIACAO).as(java.sql.Date.class),
						joinPagamentoBoleto.get(PagamentoBoleto_.DATA_VENCIMENTO).as(java.sql.Date.class)),
				criteriaBuilder.equal(root.get(Pedido_.STATUS), StatusPedido.AGUARDANDO)
		);
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ", current_date: " + arr[1]
						+ ", current_time: " + arr[2]
						+ ", current_timestamp: " + arr[3]
		));
	}

	@Test
	public void aplicaFuncaoString() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
		criteriaQuery.multiselect(
				root.get(Cliente_.NOME),
				criteriaBuilder.concat("Nome do cliente", root.get(Cliente_.NOME)),
				criteriaBuilder.length(root.get(Cliente_.NOME)),
				criteriaBuilder.locate(root.get(Cliente_.NOME), "a"),
				criteriaBuilder.substring(root.get(Cliente_.NOME), 1, 2),
				criteriaBuilder.lower(root.get(Cliente_.NOME)),
				criteriaBuilder.upper(root.get(Cliente_.NOME)),
				criteriaBuilder.trim(root.get(Cliente_.NOME))
		);
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(
				arr[0]
						+ ", contat: " + arr[1]
						+ ", length: " + arr[2]
						+ ", locate: " + arr[3]
						+ ", substring: " + arr[4]
						+ ", lower: " + arr[5]
						+ ", upepr: " + arr[6]
						+ ", trim: " + arr[7]));
	}
}
