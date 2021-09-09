package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPedido;

public class OperadoresLogicosTest extends EntityManagerTest {

	@Test
	public void usarOperadores() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
//		AND - forma implícita
//		criteriaQuery.where(
//				criteriaBuilder.greaterThan(root.get(Pedido_.TOTAL), new BigDecimal(499)),
//				criteriaBuilder.equal(root.get(Pedido_.STATUS), StatusPedido.PAGO));
		
//		AND - forma explícita
//		criteriaQuery.where(
//				criteriaBuilder.and(
//					criteriaBuilder.greaterThan(root.get(Pedido_.TOTAL), new BigDecimal(499)),
//					criteriaBuilder.equal(root.get(Pedido_.STATUS), StatusPedido.PAGO)
//		), criteriaBuilder.greaterThan(root.get(Pedido_.DATA_CRIACAO), LocalDateTime.now().minusDays(5)));
		
//		OR
		criteriaQuery.where(
				criteriaBuilder.or(
						criteriaBuilder.greaterThan(root.get(Pedido_.TOTAL), new BigDecimal(499)),
						criteriaBuilder.equal(root.get(Pedido_.STATUS), StatusPedido.PAGO)
						), criteriaBuilder.greaterThan(root.get(Pedido_.TOTAL), new BigDecimal(499)
		));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println("ID: " + p.getId() + ", Total: " + p.getTotal()));
	}
}
