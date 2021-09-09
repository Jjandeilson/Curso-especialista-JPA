package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;

public class AbordagemHibridaTest extends EntityManagerTest {
	
	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
		
		EntityManager em = entityManagerFactory.createEntityManager();
		
		String jpql = "select c from Categoria c";
		TypedQuery<Categoria> typedQuery = em.createQuery(jpql, Categoria.class);
		
		entityManagerFactory.addNamedQuery("Categoria.listar", typedQuery);
	}

	@Test
	public void usarAbordagemHibrida() {
		TypedQuery<Categoria> typedQuery = entityManager.createNamedQuery("Categoria.listar", Categoria.class);
		
		List<Categoria> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
}
