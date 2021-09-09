package com.algaworks.ecommerce.mapeamentoavancado;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class DetalhesColumnTest extends EntityManagerTest {

	@Test
	public void impedirInsercaoDaColunaAtualizacao() {
		Produto produto = new Produto();
		produto.setNome("Teclado para smartphone");
		produto.setDescricao("O mais confortável");
		produto.setPreco(BigDecimal.ONE);
		produto.setDataCriacao(LocalDateTime.now());
		produto.setDataUltimaAtualizacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao.getDataCriacao());
		assertNull(produtoVerificacao.getDataUltimaAtualizacao());
	}

	@Test
	public void impedirAtualizacaoDaColunaCriacao() {
		entityManager.getTransaction().begin();
	
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setPreco(BigDecimal.ONE);
		produto.setDataCriacao(LocalDateTime.now());
		produto.setDataUltimaAtualizacao(LocalDateTime.now());
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotEquals(produto.getDataCriacao().truncatedTo(ChronoUnit.SECONDS),
				produtoVerificacao.getDataCriacao().truncatedTo(ChronoUnit.SECONDS));
		assertEquals(produto.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS),
				produtoVerificacao.getDataUltimaAtualizacao().truncatedTo(ChronoUnit.SECONDS));
	}
}
