package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class FuncoesTest extends EntityManagerTest {
	
	@Test
	public void aplicarFuncaoNativas() {
		String jpql = "select p from Pedido p where function('acima_media_faturamento', p.total) = 1";

//		String jpql = "select function('dayname', p.dataCriacao) from Pedido p 
//		 where function('acima_media_faturamento', p.total) = 1";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println(obj));
	}
	
	@Test
	public void aplicaFuncaoColecao() {
		String jpql = "select size(p.itens) from Pedido p where size(p.itens) > 1";
		
		TypedQuery<Integer> typedQuery = entityManager.createQuery(jpql, Integer.class);
		
		List<Integer> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(size -> System.out.println(size));
	}
	
	@Test
	public void aplicarFuncaoNumero() {
		String jpql = "select abs(p.total), mod(p.id, 2), sqrt(p.total) from Pedido p "
				+ " where abs(p.total) > 1000";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
	}

	@Test
	public void aplicarFuncaoData() {
//		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		
//		String jpql = "select current_date, current_time, current_timestamp from Pedido p "
//				+ " where p.dataCriacao < current_date";

//		String jpql = "select year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao) from Pedido p";

		String jpql = "select hour(p.dataCriacao), minute(p.dataCriacao), second(p.dataCriacao) from Pedido p ";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + " | " + arr[1] + " | " + arr[2]));
	}

	@Test
	public void aplicaFuncaoString() {
		// concat, length, loate, substring, lower, upper, trim
		
//		String jpql = "select c.nome, concat('Categoria: ', c.nome) from Categoria c";
//		String jpql = "select c.nome, length(c.nome) from Categoria c";
//		String jpql = "select c.nome, locate('a', c.nome) from Categoria c";
//		String jpql = "select c.nome, substring(c.nome, 1, 2) from Categoria c";
//		String jpql = "select c.nome, lower(c.nome) from Categoria c";
//		String jpql = "select c.nome, upper(c.nome) from Categoria c";
//		String jpql = "select c.nome, trim(c.nome) from Categoria c";
		String jpql = "select c.nome, length(c.nome) from Categoria c where substring(c.nome, 1, 1) = 'N'";
		
		TypedQuery<Object[]> typedQeury = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQeury.getResultList();
		Assert.assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + " - " + arr[1]));
	}	
}
