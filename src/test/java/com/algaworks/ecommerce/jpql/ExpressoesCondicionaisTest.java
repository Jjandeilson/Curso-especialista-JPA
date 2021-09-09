package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Assert;
import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class ExpressoesCondicionaisTest extends EntityManagerTest {
	
	@Test
	public void usarExpressaoIN() {
		Cliente cliente1 = entityManager.find(Cliente.class, 1);
		Cliente cliente2 = entityManager.find(Cliente.class, 2);

		//		List<Integer> parametros = Arrays.asList(1,3,4);
		List<Cliente> clientes = Arrays.asList(cliente1,cliente2);
		
		String jpql = "select p from Pedido p where p.cliente in (:clientes)";
//		String jpql = "select p from Pedido p where p.id in (:lista)";
//		String jpql = "select p from Pedido p where p.id in (1, 3, 4)";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("clientes", clientes);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarExpressaoCase() {
//		Total de vendas dentre as categorias que mais vendem
//		String jpql = "select p.id, "
//				+ " case p.status "
//				+ " 	when 'PAGO' then 'Está pago' "
//				+ "		when 'CANCELADO' then 'Foi cancelado' "
//				+ "		else 'Está aguardando' "
//				+ " end "
//				+ " from Pedido p";
		String jpql = "select p.id, "
				+ " case type(p.pagamento) "
				+ " 	when PagamentoBoleto then 'Pago com boleto' "
				+ "		when PagamentoCartao then 'Pago com cartão' "
				+ "		else 'Não pago ainda' "
				+ " end "
				+ " from Pedido p";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println(arr[0] + ", " + arr[1]));
	}
	
	@Test
	public void usarExpressaoDiferente() {
		String jpql = "select p from Produto p where p.id <> 1";
		
		TypedQuery<Produto>  typedQuery = entityManager.createQuery(jpql, Produto.class);
		
		List<Produto> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarBetween() {
		String jpql = "select p from Pedido p "
				+ " where p.dataCriacao between :dataInicial and :dataFinal";
		
		TypedQuery<Pedido>  typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("dataInicial", LocalDateTime.now().minusDays(10));
		typedQuery.setParameter("dataFinal", LocalDateTime.now());
		
		List<Pedido> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarMaiorMenorComDatas() {
		String jpql = "select p from Pedido p where p.dataCriacao > :data";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("data", LocalDateTime.now().minusDays(2));
		
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void usarMaiorMenor() {
//		String jpql = "select p from Produto p where p.preco > :preco"; // preco maior que 499
//		String jpql = "select p from Produto p where p.preco <= :preco"; // preco menor que 
		String jpql = "select p from Produto p where p.preco >= :precoInicial and p.preco <= :precoFinal"; // preco menor que 
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		typedQuery.setParameter("precoInicial", new BigDecimal(4));
		typedQuery.setParameter("precoFinal", new BigDecimal(1500));
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarIsNull() {
		String jpql = "select p from Produto p where p.foto is null"; // produto que não tem foto
//		String jpql = "select p from Produto p where p.foto is not null"; produto que tenha foto
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarIsEmpty() {
		String jpql = "select p from Produto p where p.categorias is empty"; // produto que não possui categoria
//		String jpql = "select p from Produto p where p.categorias is not empty"; produto que possui categoria
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		
		List<Object[]> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarExpressaoCondicionalLike() {
		String jpql = "select c from Cliente c where c.nome like concat('%', :nome, '%')";
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(jpql, Object[].class);
		typedQuery.setParameter("nome", "a");
		
		List<Object[]> lista = typedQuery.getResultList();
		Assert.assertFalse(lista.isEmpty());
	}	
}
