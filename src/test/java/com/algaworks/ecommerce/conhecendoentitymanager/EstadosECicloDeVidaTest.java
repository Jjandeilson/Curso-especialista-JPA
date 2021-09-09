package com.algaworks.ecommerce.conhecendoentitymanager;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;

public class EstadosECicloDeVidaTest extends EntityManagerTest {

	@SuppressWarnings("unused")
	@Test
	public void analisarEstados() {
		// o objeto est� no estado transient
		Categoria categoriaNovo = new Categoria();
		categoriaNovo.setNome("Eletr�nicos");
		
		// o objeto � colocado no estado managed
		Categoria categoriaGerenciadaMerge = entityManager.merge(categoriaNovo);
		
		// o objeto est� no estado managed
		Categoria categoriaGenreciada = entityManager.find(Categoria.class, 1);
		
		// o objeto est� no estado removed
		entityManager.remove(categoriaGenreciada);
		
		// o objeto retorno au estado managed
		entityManager.persist(categoriaGenreciada);
		
		// o objeto � colocado no estado detached
		entityManager.detach(categoriaGenreciada);
	}
}
