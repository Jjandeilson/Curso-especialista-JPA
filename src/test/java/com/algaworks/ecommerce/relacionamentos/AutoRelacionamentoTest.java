package com.algaworks.ecommerce.relacionamentos;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;

public class AutoRelacionamentoTest extends EntityManagerTest {
	
	

	@Test
	public void verificarRelacionamento() {
		Categoria categoriaPai = new Categoria();
		categoriaPai.setNome("Futebol");
		
		Categoria categoria = new Categoria();
		categoria.setNome("Uniformes");
		categoria.setCategoriaPai(categoriaPai);
		
		entityManager.getTransaction().begin();
		entityManager.persist(categoriaPai);
		entityManager.persist(categoria);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		assertNotNull(categoriaVerificacao.getCategoriaPai());
		
		Categoria categoriaPaiVerificacao = entityManager.find(Categoria.class, categoriaPai.getId());
		assertFalse(categoriaPaiVerificacao.getCategorias().isEmpty());
	}
}
