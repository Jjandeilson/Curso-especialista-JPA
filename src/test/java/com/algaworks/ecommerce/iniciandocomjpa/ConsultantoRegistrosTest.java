package com.algaworks.ecommerce.iniciandocomjpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class ConsultantoRegistrosTest extends EntityManagerTest {
	
	@Test
	public void buscarPorIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1); // a consulta no banco � feita no momento que  � utilizado

//		Produto produto = entityManager.getReference(Produto.class, 1);  a consulta s� � realizada quando � buscada um valor do objeto
//		System.out.println("Inda n�o buscou!!!");
		
		assertNotNull(produto);
		assertEquals("Kindle", produto.getNome());
	}
	
	@Test
	public void atualizarAReferencia() {
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Microfone Sanson");
		
		entityManager.refresh(produto); // descarta alguma mudan�a feita antes de persistir no banco e busca as informa��es que est�o no banco para o objeto novamente
		
		assertEquals("Kindle", produto.getNome());
	}
}
