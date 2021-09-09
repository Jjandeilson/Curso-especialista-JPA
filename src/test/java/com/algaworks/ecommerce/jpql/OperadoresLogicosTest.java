package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class OperadoresLogicosTest extends EntityManagerTest {

	@Test
	public void usarExpressaoDiferente() {
//		String jpql = "select p from Pedido p where p.total > 500 and p.status = 'AGUARDANDO' and p.cliente.id = 1";
//		String jpql = "select p from Pedido p where p.status = 'AGUARDANDO' or p.status = 'PAGO'";
		String jpql = "select p from Pedido p where (p.status = 'AGUARDANDO' or p.status = 'PAGO') and p.total > 100";
		
		TypedQuery<Pedido>  typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
}
