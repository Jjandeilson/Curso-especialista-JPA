package com.algaworks.ecommerce.iniciandocomjpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.SexoCliente;

public class PrimeiroCrudTest extends EntityManagerTest{

	@Test
	public void inserirRegistro() {
		Cliente cliente  = new Cliente();
		cliente.setNome("José Lucas");
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setCpf("333");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		assertNotNull(clienteVerificacao);
	}
	
	@Test
	public void buscarPorIdentificador() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		assertNotNull(produto);
		assertEquals("Kindle", produto.getNome());
	}
	
	@Test
	public void atualizarRegistro() {
		Cliente cliente = new Cliente();
		cliente.setId(1);
		cliente.setNome("Fernando Medeiros Silva");
		cliente.setCpf("000");
		cliente.setSexo(SexoCliente.MASCULINO);
		
		entityManager.getTransaction().begin();
		entityManager.merge(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		assertEquals("Fernando Medeiros Silva", clienteVerificacao.getNome());
	}
	
	@Test
	public void removerRegistro() {
		Cliente cliente = entityManager.find(Cliente.class, 2);
		
		entityManager.getTransaction().begin();
		entityManager.remove(cliente);
		entityManager.getTransaction().commit();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		assertNull(clienteVerificacao);
	}
}
