package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;

public class GroupByTest extends EntityManagerTest {

	@Test
	public void condicionarAgrupamentoComhaving() {
//		Total de vendas dentre as categorias que mais vendem
		String jpql = "select cat.nome, sum(ip.precoProduto) from ItemPedido ip "
				+ " join ip.produto pro join pro.categorias cat "
				+ " group by cat.id "
				+ " having sum(ip.precoProduto) > 100";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}

	@Test
	public void agruparEFiltrarResultado() {
//		Total de vendas por mês.
//		String jpql = "select concat( year(p.dataCriacao), '/' , function('monthname', p.dataCriacao)), sum(p.total) "
//				+ " from Pedido p "
//				+ " where year(p.dataCriacao) = year(current_date) "
//				+ " group by year(p.dataCriacao), month(p.dataCriacao) ";
		
//		Total de vendas por categoria.
//		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
//				+ " join ip.produto pro join pro.categorias c join  ip.pedido p "
//				+ " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) = month(current_date) "
//				+ " group by c.id";
		
//		Total de vendas por cliente
		 String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip " +
	                " join ip.pedido p join p.cliente c join ip.pedido p " +
	                " where year(p.dataCriacao) = year(current_date) and month(p.dataCriacao) >= (month(current_date) - 3) " +
	                " group by c.id";
				
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}

	@Test
	public void agruparResultado() {
//		Quantidade de produtos por categoria.
//		String jpql = "select c.nome, count(p.id) from Categoria c join c.produtos p group by c.id";
		
//		Total de vendas por mês.
//		String jpql = "select concat( year(p.dataCriacao), '/' , function('monthname', p.dataCriacao)), sum(p.total) from Pedido p "
//				+ " group by year(p.dataCriacao), month(p.dataCriacao) ";
		
//		Total de vendas por categoria.
//		String jpql = "select c.nome, sum(ip.precoProduto) from ItemPedido ip "
//				+ " join ip.produto pro join pro.categorias c "
//				+ " group by c.id";
		
//		Total de vendas por cliente
//		String jpql = "select p.cliente.nome, sum(ip.precoProduto) from Pedido p join p.itens ip group by p.cliente.id";
		
//		Total de vendas por dia e por categoria
		String jpql = "select " +
                " concat(year(p.dataCriacao), '/', month(p.dataCriacao), '/', day(p.dataCriacao)), " +
                " concat(c.nome, ': ', sum(ip.precoProduto)) " +
                " from ItemPedido ip join ip.pedido p join ip.produto pro join pro.categorias c " +
                " group by year(p.dataCriacao), month(p.dataCriacao), day(p.dataCriacao), c.id " +
                " order by p.dataCriacao, c.nome ";
		
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}
	
	@Test
	public void aplicarFuncaoAgregacao() {
//		avg, count, min, max, sum
		
//		String jpql = "select avg(p.total) from Pedido p";
//		String jpql = "select count(p.total) from Pedido p";
//		String jpql = "select min(p.total) from Pedido p";
//		String jpql = "select max(p.total) from Pedido p";
		String jpql = "select sum(p.total) from Pedido p";
		
		TypedQuery<Number> typedQuery = entityManager.createQuery(jpql, Number.class);
		
		List<Number> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println(obj));
	}
}
