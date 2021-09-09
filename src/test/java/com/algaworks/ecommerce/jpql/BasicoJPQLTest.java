package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;

public class BasicoJPQLTest extends EntityManagerTest {
	
	@Test
	public void usarExpressaoIN() {
		String jpql = "select distinct p from Pedido p "
				+ " join p.itens i join i.produto pro "
				+ " where pro.id in (1, 2, 3, 4)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	
		System.out.println(lista.size());
	}
	
	@Test
	public void ordenarResultados() {
		String jpql = "select c from Cliente c order by c.id asc"; // ou desc
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(jpql, Cliente.class);
		List<Cliente> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
	}

	@Test
	public void projetarNoDTO() {
		String jpql = "select new com.algaworks.ecommerce.dto.ProdutoDTO(id, nome) from Produto"; // para retona o DTO usamos a palavra  new e o pacote que a classe está
		
		TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(jpql, ProdutoDTO.class);
		List<ProdutoDTO> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(p -> System.out.println(p.getId() + ", " + p.getNome()));
	}
	
	@Test
	public void projetarOResultado() {
		String jpql = "select id, nome from Produto";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertTrue(lista.get(0).length == 2);
		
		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}
	
	@Test
	public void selecionarUmAtributoParaRetorno() {
		String jpql = "select p.nome from Produto p";
		
		TypedQuery<String> typedQuery = entityManager.createQuery(jpql, String.class);
		List<String> lista = typedQuery.getResultList();
		assertTrue(String.class.equals(lista.get(0).getClass()));
		
		String jpqlCliente = "select p.cliente from Pedido p";
		TypedQuery<Cliente> typedQueryCliente = entityManager.createQuery(jpqlCliente, Cliente.class);
		List<Cliente> listaClientes = typedQueryCliente.getResultList();
		assertTrue(Cliente.class.equals(listaClientes.get(0).getClass()));
	}

	@Test
	public void buscarPorIdentificador() {
		// Java Persistence Query Language - JPQL
		// JPQL - select p from Pedido p join p.itens i where i.precoProduto > 10
		// SQL - select p.* from pedido p join item_pedido i on i.pedido_id = p.id where i.preco_produto > 10
		
//		entityManager.find(Pedido.class, 1);
		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p where p.id = 1", Pedido.class);
		// select p.* from pedido p where p.id = 1
		
		Pedido pedido = typedQuery.getSingleResult();
		assertNotNull(pedido);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void mostrarDiferencaQueries() {
		String jpql = "select p from Pedido p where p.id = 1";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		Pedido pedido1 = typedQuery.getSingleResult(); // com o typedQuery não temos que realizar o cast para o bojeto retornado
		assertNotNull(pedido1);
		
		Query query = entityManager.createQuery(jpql);
		Pedido pedido2 = (Pedido) query.getSingleResult(); // com p Query temos que utilizar o cas para  expecificar o bojeto retornado
		assertNotNull(pedido2);		
		
//		List<Pedido> lista = query.getResultList();
//		assertFalse(lista.isEmpty());
	}
}
	