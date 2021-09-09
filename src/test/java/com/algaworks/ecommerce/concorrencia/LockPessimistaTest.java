package com.algaworks.ecommerce.concorrencia;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.algaworks.ecommerce.model.Produto;

public class LockPessimistaTest {

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
	
	@SuppressWarnings("unchecked")
	@Test
	public void usarlockTypedQuery() {
		Runnable runnable1 = () -> {
			log("iniciando Runnable 01.");
			
			String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			entityManager1.getTransaction().begin();
			
			log("Runnable 01 vai carregar todos os produtos");
			List<Produto> lista = entityManager1
						.createQuery("select p from Produto p where p.id in (3,4,5)")
						.setLockMode(LockModeType.PESSIMISTIC_READ)
						.getResultList();
			
			Produto produto = lista
								.stream()
								.filter(p -> p.getId().equals(3))
								.findFirst()
								.get();
			
			log("Runnable 01 vai alterar o produto de  ID igual a 1.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 01 vai esperar por 3 segundo(s).");
			esperar(3);
			
			log("Runnable 01 vai confirma a transação.");
			entityManager1.getTransaction().commit();
			entityManager1.close();
			
			log("Encerrando Runnable 01.");
		};
		
		Runnable runnable2 = () -> {
			log("iniciando Runnable 01.");
			
			String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager2 = entityManagerFactory.createEntityManager();
			entityManager2.getTransaction().begin();
			
			log("Runnable 02 vai carregar o produto 2.");			
			Produto produto = entityManager2.find(Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);
			
			log("Runnable 02 vai alterar o produto de  ID igual a 1.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 02 vai esperar por 1 segundo(s).");
			esperar(1);
			
			log("Runnable 02 vai confirma a transação.");
			entityManager2.getTransaction().commit();
			entityManager2.close();
			
			log("Encerrando Runnable 02.");
		};
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		
		esperar(1);
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
		
		assertTrue(produto.getDescricao().startsWith("Descrição detalhada"));
		
		log("Encerrando método de teste.");
	}
	
	@Test
	public void misturarTiposDelock() {
		Runnable runnable1 = () -> {
			log("iniiciando Runnable 01.");
			
			String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			entityManager1.getTransaction().begin();
			
			log("Runnable 01 vai carregar o produto 1.");
			Produto produto = entityManager1.find(Produto.class, 1, LockModeType.PESSIMISTIC_READ);
			
			log("Runnable 01 vai alterar o produto.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 01 vai esperar por 3 segundo(s).");
			esperar(3);
			
			log("Runnable 01 vai confirma a transação.");
			entityManager1.getTransaction().commit();
			entityManager1.close();
			
			log("Encerrando Runnable 01.");
		};		
		
		Runnable runnable2 = () -> {
			log("iniiciando Runnable 02.");
			
			String novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager2 = entityManagerFactory.createEntityManager();
			entityManager2.getTransaction().begin();
			
			log("Runnable 02 vai carregar o produto 2.");
			Produto produto = entityManager2.find(Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);
			
			log("Runnable 02 vai alterar o produto.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 02 vai esperar por 2 segundo(s).");
			esperar(1);
			
			log("Runnable 02 vai confirma a transação.");
			entityManager2.getTransaction().commit();
			entityManager2.close();
			
			log("Encerrando Runnable 02.");
		};	
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		
		esperar(1);
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
		
		assertTrue(produto.getDescricao().startsWith("Descrição detalhada"));
		
		log("Encerrando método de teste.");
	}
	
	@Test
	public void usarLockPessimistaLockModeTypePessimisticWrite() {
		Runnable runnable1 = () -> {
			log("iniiciando Runnable 01.");
			
			String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			entityManager1.getTransaction().begin();
			
			log("Runnable 01 vai carregar o produto 1.");
			Produto produto = entityManager1.find(Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);
			
			log("Runnable 01 vai alterar o produto.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 01 vai esperar por 3 segundo(s).");
			esperar(3);
			
			log("Runnable 01 vai confirma a transação.");
			entityManager1.getTransaction().commit();
			entityManager1.close();
			
			log("Encerrando Runnable 01.");
		};		
		
		Runnable runnable2 = () -> {
			log("iniiciando Runnable 02.");
			
			String novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager2 = entityManagerFactory.createEntityManager();
			entityManager2.getTransaction().begin();
			
			log("Runnable 02 vai carregar o produto 2.");
			Produto produto = entityManager2.find(Produto.class, 1, LockModeType.PESSIMISTIC_WRITE);
			
			log("Runnable 02 vai alterar o produto.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 02 vai esperar por 2 segundo(s).");
			esperar(1);
			
			log("Runnable 02 vai confirma a transação.");
			entityManager2.getTransaction().commit();
			entityManager2.close();
			
			log("Encerrando Runnable 02.");
		};	
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		
		esperar(1);
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
		
		assertTrue(produto.getDescricao().startsWith("Descrição massa!"));
		
		log("Encerrando método de teste.");
	}
	
	@Test
	public void usarLockPessimistaLockModeTypePessimisticRead() {
		Runnable runnable1 = () -> {
			log("iniiciando Runnable 01.");
			
			String novaDescricao = "Descrição detalhada. CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager1 = entityManagerFactory.createEntityManager();
			entityManager1.getTransaction().begin();
			
			log("Runnable 01 vai carregar o produto 1.");
			Produto produto = entityManager1.find(Produto.class, 1, LockModeType.PESSIMISTIC_READ);

			log("Runnable 01 vai alterar o produto.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 01 vai esperar por 3 segundo(s).");
			esperar(3);
			
			log("Runnable 01 vai confirma a transação.");
			entityManager1.getTransaction().commit();
			entityManager1.close();

			log("Encerrando Runnable 01.");
		};		

		Runnable runnable2 = () -> {
			log("iniiciando Runnable 02.");
			
			String novaDescricao = "Descrição massa! CTM: " + System.currentTimeMillis(); 
			
			EntityManager entityManager2 = entityManagerFactory.createEntityManager();
			entityManager2.getTransaction().begin();
			
			log("Runnable 02 vai carregar o produto 2.");
			Produto produto = entityManager2.find(Produto.class, 1);
			
			log("Runnable 02 vai alterar o produto.");
			produto.setDescricao(novaDescricao);
			
			log("Runnable 02 vai esperar por 2 segundo(s).");
			esperar(1);
			
			log("Runnable 02 vai confirma a transação.");
			entityManager2.getTransaction().commit();
			entityManager2.close();
			
			log("Encerrando Runnable 02.");
		};	
		
		Thread thread1 = new Thread(runnable1);
		Thread thread2 = new Thread(runnable2);
		
		thread1.start();
		
		esperar(1);
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
		
		assertTrue(produto.getDescricao().startsWith("Descrição detalhada"));
		
		log("Encerrando método de teste.");
	}
		
}
