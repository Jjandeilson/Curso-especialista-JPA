package com.algaworks.ecommerce.mapeamentobasico;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.SexoCliente;

public class MapeandoEnumeracoesTest extends EntityManagerTest {

	@Test
	public void testarEnum() {
		Cliente cliente = new Cliente();
//		cliente.setId(4); Comentado porque estamos utilizando IDENTITY
		cliente.setNome("jos� Mineiro");
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setCpf("777");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		assertNotNull(clienteVerificacao);
	}
}
