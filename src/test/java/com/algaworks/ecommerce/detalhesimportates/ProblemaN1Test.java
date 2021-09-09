package com.algaworks.ecommerce.detalhesimportates;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.EntityGraph;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Pedido;

public class ProblemaN1Test extends EntityManagerTest {

	@Test
	public void resolverComEntityGraph() {
		EntityGraph<Pedido> entityGraph = entityManager.createEntityGraph(Pedido.class);
		entityGraph.addAttributeNodes("cliente", "notaFiscal", "pagamento");
		List<Pedido> lista = entityManager.createQuery("select p from Pedido p", Pedido.class)
				.setHint("javax.persistence.loadgraph", entityGraph)
				.getResultList();
		
		assertFalse(lista.isEmpty());
	}

	@Test
	public void resolverComFetch() {
		List<Pedido> lista = entityManager.createQuery("select p from Pedido p join fetch p.cliente c "
				+ " join fetch p.pagamento pag "
				+ " join fetch p.notaFiscal nf ", Pedido.class).getResultList();
		
		assertFalse(lista.isEmpty());
	}
}
