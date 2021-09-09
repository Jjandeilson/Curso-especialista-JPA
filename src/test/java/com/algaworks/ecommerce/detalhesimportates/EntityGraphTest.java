package com.algaworks.ecommerce.detalhesimportates;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Cliente_;
import com.algaworks.ecommerce.metalmodel.Pedido_;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pedido;

public class EntityGraphTest extends EntityManagerTest {

	@Test
	public void buscarAtributosEssenciaisComNamedEntityGraph() {
		EntityGraph<?> entityGraph = entityManager.createEntityGraph("Pedido.dadosEssenciais");
		entityGraph.addAttributeNodes("pagamento");
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}

	@Test
	public void buscarAtributosEssenciaisDePedido03() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes(Pedido_.DATA_CRIACAO, Pedido_.STATUS, Pedido_.TOTAL);
		
		Subgraph<Cliente> subGraphCliente = entityGraph.addSubgraph(Pedido_.CLIENTE);
		subGraphCliente.addAttributeNodes(Cliente_.NOME, Cliente_.CPF);
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}

	@Test
	public void buscarAtributosEssenciaisDePedido02() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total");
		
		Subgraph<Cliente> subGraphCliente = entityGraph.addSubgraph("cliente", Cliente.class);
		subGraphCliente.addAttributeNodes("nome", "cpf");
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}

	@Test
	public void buscarAtributosEssenciaisDePedido01() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("dataCriacao", "status", "total", "notaFiscal");
		
//		Map<String, Object> propriedades = new HashMap<String, Object>();
//		propriedades.put("javax.persistence.fetchgraph", entityGraph);
//		propriedades.put("javax.persistence.loadgraph", entityGraph);
//		
//		Pedido pedido = entityManager.find(Pedido.class, 1, propriedades);
//		assertNotNull(pedido);
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery("select p from Pedido p", Pedido.class);
		
		typedQuery.setHint("javax.persistence.fetchgraph", entityGraph);
		
		List<Pedido> lista = typedQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}
}
