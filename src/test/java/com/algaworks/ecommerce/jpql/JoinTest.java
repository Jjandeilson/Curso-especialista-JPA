package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class JoinTest extends EntityManagerTest {
	
	@Test
	public void usarJoinFetch() {
		String jpql = "select p from Pedido p " // usando o fetch os dados de outros relacionamentos são carregados no primeiro select
				+ " left join fetch p.pagamento "
				+ " join fetch p.cliente "
				+ " left join fetch p.notaFiscal "; 
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void fazerLeftJoin() {
		String jpql = "select p from Pedido p left join p.pagamento pag on pag.status = 'PROCESSANDO'";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void fazerJoin() {
		String jpql = "select p from Pedido p inner join p.pagamento pag";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
}
