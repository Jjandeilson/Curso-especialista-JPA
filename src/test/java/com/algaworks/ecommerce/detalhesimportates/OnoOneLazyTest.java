package com.algaworks.ecommerce.detalhesimportates;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class OnoOneLazyTest extends EntityManagerTest{

	@Test
	public void mostrarProblema() {
		System.out.println("BUSCANDO UM PEDIDO");
		Pedido pedido = entityManager.find(Pedido.class, 1);
		assertNotNull(pedido);
		
		System.out.println("--------------------------------------------------");
		
		System.out.println("BUSCANDO UMA LISTA DE PEDIDOS:");
		List<Pedido> lista = entityManager.createQuery("select p from Pedido p", Pedido.class).getResultList();
		assertFalse(lista.isEmpty());
	}
}
