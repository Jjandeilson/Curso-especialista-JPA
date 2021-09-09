package com.algaworks.ecommerce.concorrencia;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

public class LockOtimistaTest {

	protected static EntityManagerFactory entityManagerFactory;
	
	@BeforeClass
	public static void setUpBeforeClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory("Ecommerce-PU");
	}
	
	@AfterClass
	public static void tearDownAfterClass() {
		entityManagerFactory.close();
	}
	
	private static void log(Object obj, Object... args) {
		System.out.println(String.format("[LOG " + System.currentTimeMillis() + "] " + obj, args));		
	}
	
	private static void esperar(int segundos) {
		try {
			Thread.sleep(segundos * 1000);
		}catch(InterruptedException e) {}
	}
	
	@Test
	public void usarLockOtimista() {
		Runnable runnable1 = () -> {
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			entityManager1.getTransaction().begin();
			
			log("Runnable 01 vai carregar o produto 1.");
			Produto produto = entityManager1.find(Produto.class, 1);

			log("Runnable 01 esperar por 3 segundos.");
			esperar(3);
			
			log("Runnable 01 vai alterar o produto.");
			produto.setDescricao("Descrição detalhada");
			
			log("Runnable 01 vai confirma a transação.");
			entityManager1.getTransaction().commit();
			entityManager1.close();
		};

		Runnable runnable2 = () -> {
			EntityManager entityManager2 = entityManagerFactory.createEntityManager();
			entityManager2.getTransaction().begin();
			
			log("Runnable 02 vai carregar o produto 1.");
			Produto produto = entityManager2.find(Produto.class, 1);
			
			log("Runnable 02 esperar por 1 segundos.");
			esperar(1);
			
			log("Runnable 02 vai alterar o produto.");
			produto.setDescricao("Descrição massa!");
			
			log("Runnable 02 vai confirma a transação.");
			entityManager2.getTransaction().commit();
			entityManager2.close();
		};
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		}catch(InterruptedException e) {
			throw new RuntimeException(e);
		}		
		
		EntityManager entityManager3 = entityManagerFactory.createEntityManager();
		Produto produto = entityManager3.find(Produto.class, 1);
		entityManager3.close();
		
		assertEquals("Descrição massa!", produto.getDescricao());
		
		log("Encerrando método de teste.");
	}
		
}
