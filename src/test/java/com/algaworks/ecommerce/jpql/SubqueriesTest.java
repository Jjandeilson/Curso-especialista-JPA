package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class SubqueriesTest extends EntityManagerTest {
	
	@Test
    public void pesquisarComAllExercicio() {
        // Todos os produtos que sempre foram vendidos pelo mesmo preço.
        String jpql = "select distinct p from ItemPedido ip join ip.produto p where " +
                " ip.precoProduto = ALL " +
                " (select precoProduto from ItemPedido where produto = p and id <> ip.id)";

        TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

        List<Produto> lista = typedQuery.getResultList();
        assertFalse(lista.isEmpty());

        lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
    }
	
	@Test
	public void pesquisarComAny() {
//		Podemos usar o ANY ou o SOME
//		Todos os produtos que já foram vendidos por um preco diferente do atual
		String jpql = "select p from Produto p "
				+ " where p.preco <> ANY (select precoProduto from ItemPedido where produto = p)";

//		Todos os produtos que já foram vendidos, pelo menos, uma vez pelo preço atual.
//		String jpql = "select p from Produto p "
//				+ " where p.preco = ANY (select precoProduto from ItemPedido where produto = p)";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		
		List<Produto> lista = typedQuery.getResultList();
	    assertFalse(lista.isEmpty());

	    lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarComAll() {
//		Todos os produtos não faram vendidos mais depois que encareceram
		String jpql = "select p from Produto p where "
				+ " p.preco > ALL (select precoProduto from ItemPedido where produto = p)";
		
//		Todos os produtos que sempre foram vendidos pelo preco atual.
//		String jpql = "select p from Produto p where "
//				+ " p.preco = ALL (select precoProduto from ItemPedido where produto = p)";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		
		List<Produto> lista = typedQuery.getResultList();
	    assertFalse(lista.isEmpty());

	    lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	 @Test
	 public void perquisarComExistsExercicio() {
		 String jpql = "select p from Produto p " +
	                " where exists " +
	                " (select 1 from ItemPedido where produto = p and precoProduto <> p.preco)";

	     TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);

	     List<Produto> lista = typedQuery.getResultList();
	      assertFalse(lista.isEmpty());

	     lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarComSubQueryExercicio() {
		String jpql = "select c from Cliente c where  "
				+ " (select count(cliente) from Pedido where cliente = c) >= 2";
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		
		List<Cliente> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}
	
	@Test
	public void pesquisarComINExercicio() {
		String jpql = "select p from Pedido p where p.id in "
				+ " (select p2.id from ItemPedido i2 "
				+ " 	join i2.pedido p2 join i2.produto pro2 join pro2.categorias cat where cat.id = 2)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarComExists() {
		String jpql = "select p from Produto p where exists "
				+ " (select 1  from ItemPedido ip2 join ip2.produto p2 where p2 = p)";
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);
		
		List<Produto> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}
	
	@Test
	public void pesquisarComIN() {
		String jpql = "select p from Pedido p where p.id in "
				+ " (select p2.id from ItemPedido i2 "
				+ " 	join i2.pedido p2 join i2.produto pro2 where pro2.preco > 100)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId()));
	}

	@Test
	public void pesquisarSubqueries() {
//		Bonos clientes. Versão 2
		String jpql = "select c from Cliente c where "
				+ " 500 < (select sum(p.total) from Pedido p where p.cliente = c)";
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		
//		Bons clientes. Versão 1
//		String jpql = "select c from Cliente c where "
//				+ " 500 < (select sum(p.total) from c.pedidos p)";
//		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		
//		Todos os pedidos acima da média de vendas
//		String jpql = "select p from Pedido p where "
//				+ " p.total > (select avg(total) from Pedido)";
//		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
//		O produto ou os produtos mais caros da base
//		String jpql = "select p from Produto p where "
//				+ " p.preco = (select max(preco) from Produto)";
//		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql, Produto.class);		
		
		List<Cliente> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(obj -> System.out.println("ID: " + obj.getId() + ", Nome: " + obj.getNome()));
	}
}
