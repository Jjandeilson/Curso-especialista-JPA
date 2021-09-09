package com.algaworks.ecommerce.iniciandocomjpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class ConsultantoRegistrosTest extends EntityManagerTest {
	
	@Test
	public void buscarPorIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1); // a consulta no banco é feita no momento que  é utilizado

//		Produto produto = entityManager.getReference(Produto.class, 1);  a consulta só é realizada quando é buscada um valor do objeto
//		System.out.println("Inda não buscou!!!");
		
		assertNotNull(produto);
		assertEquals("Kindle", produto.getNome());
	}
	
	@Test
	public void atualizarAReferencia() {
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setNome("Microfone Sanson");
		
		entityManager.refresh(produto); // descarta alguma mudança feita antes de persistir no banco e busca as informações que estão no banco para o objeto novamente
		
		assertEquals("Kindle", produto.getNome());
	}
}
