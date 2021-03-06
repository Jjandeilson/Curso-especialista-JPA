package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.metalmodel.Cliente_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class BasicoCriteriaTest extends EntityManagerTest {
	
	@Test
	public void usarDistinct() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		root.join(Pedido_.ITENS);
		
		criteriaQuery.select(root);
		criteriaQuery.distinct(true);
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Pedido> lista = typedQuery.getResultList();
		
		lista.forEach(p -> System.out.println("ID: " + p.getId()));
	}
	
	@Test
	public void ordenarResultado() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
		Root<Cliente> root = criteriaQuery.from(Cliente.class);
		
//		criteriaQuery.orderBy(criteriaBuilder.asc(root.get(Cliente_.NOME)));
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get(Cliente_.NOME)));
		
		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
		
		List<Cliente> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
		
		lista.forEach(c -> System.out.println(c.getId() + ", " + c.getNome()));
	}
	
	@Test
	public void projetarOResultadoDTO() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProdutoDTO> criteriaQuery = criteriaBuilder.createQuery(ProdutoDTO.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(criteriaBuilder.construct(ProdutoDTO.class, root.get("id"), root.get("nome")));
		
		TypedQuery<ProdutoDTO> typedQuery = entityManager.createQuery(criteriaQuery);
		List<ProdutoDTO> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(dto -> System.out.println("ID: " + dto.getId() + ", Nome: " + dto.getNome()));
	}
	
	@Test
	public void projetarOResultadoTuple() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
//		criteriaQuery.multiselect(root.get("id"), root.get("nome"));
		criteriaQuery.select(criteriaBuilder
				.tuple(root.get("id").alias("id"), root.get("nome").alias("nome")));
		
		TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Tuple> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
//		lista.forEach(t -> System.out.println("ID: " + t.get(0) + ", Nome: " + t.get(1)));
		lista.forEach(t -> System.out.println("ID: " + t.get("id") + ", Nome: " + t.get("nome")));
	}

	@Test
	public void projetarOResultado() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.multiselect(root.get("id"), root.get("nome"));
		
		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Object[]> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
		
		lista.forEach(arr -> System.out.println("ID: " + arr[0] + ", Nome: " + arr[1]));
	}
	
	@Test
	public void retornaTodosOsProdutosExecicio() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void selecionarUmAtributoParaRetorno() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> criteriaQuery = criteriaBuilder.createQuery(BigDecimal.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root.get("total"));
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		
		TypedQuery<BigDecimal> typedQuery = entityManager.createQuery(criteriaQuery);
		BigDecimal total = typedQuery.getSingleResult();
		assertEquals(new BigDecimal("2398.00"), total);
		
//		Retorna um cliente
//		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//		CriteriaQuery<Cliente> criteriaQuery = criteriaBuilder.createQuery(Cliente.class);
//		Root<Pedido> root = criteriaQuery.from(Pedido.class);
//		
//		criteriaQuery.select(root.get("cliente"));
//		
//		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
//		
//		TypedQuery<Cliente> typedQuery = entityManager.createQuery(criteriaQuery);
//		Cliente cliente= typedQuery.getSingleResult();
//		assertEquals("Fernando Medeiros", cliente.getNome());
	}

	@Test
	public void buscarPorIdentificador() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Pedido> criteriaQuery = criteriaBuilder.createQuery(Pedido.class);
		Root<Pedido> root = criteriaQuery.from(Pedido.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 1));
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(criteriaQuery);
		
		Pedido pedido = typedQuery.getSingleResult();
		assertNotNull(pedido);
	}
}
