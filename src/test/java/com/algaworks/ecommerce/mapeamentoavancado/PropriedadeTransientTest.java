package com.algaworks.ecommerce.mapeamentoavancado;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;

public class PropriedadeTransientTest extends EntityManagerTest {

	@Test
	public void validarPrimeiroNome() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		
		assertEquals("Fernando", cliente.getPrimeiroNome());
	}
}
