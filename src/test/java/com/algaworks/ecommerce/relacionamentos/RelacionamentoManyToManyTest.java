package com.algaworks.ecommerce.relacionamentos;

import static org.junit.Assert.assertFalse;

import java.util.Arrays;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;

public class RelacionamentoManyToManyTest extends EntityManagerTest {
	
	@Test
	public void verificarRelacionamento() {
		Produto produto = entityManager.find(Produto.class, 1);
		Categoria categoria = entityManager.find(Categoria.class, 1);
		
		entityManager.getTransaction().begin();
//		categoria.setProdutos(Arrays.asList(produto)); como categoria não é o dono da relação a persistencia não acontece no banco de dados
		produto.setCategorias(Arrays.asList(categoria));
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Categoria categoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		assertFalse(categoriaVerificacao.getProdutos().isEmpty());
	}
}
