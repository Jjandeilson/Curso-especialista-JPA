package com.algaworks.ecommerce.validation;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;

public class ValidationTest extends EntityManagerTest {

	@Test
	public void validarCliente() {
		entityManager.getTransaction().begin();
		
		Cliente cliente = new Cliente();
		entityManager.merge(cliente);
		
		entityManager.getTransaction().commit();
	}
}
