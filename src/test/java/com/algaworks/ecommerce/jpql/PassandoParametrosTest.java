package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;

public class PassandoParametrosTest extends EntityManagerTest {

	@Test
	public void passarParametroDate() {
		String jpql = "select nf from NotaFiscal nf where nf.dataEmissao <= ?1";
		
		TypedQuery<NotaFiscal> typedQuery = entityManager.createQuery(jpql, NotaFiscal.class);
		typedQuery.setParameter(1, new Date(), TemporalType.TIMESTAMP); // forma de passar parãmetro do tipo date para uma consulta
		
		List<NotaFiscal> lista = typedQuery.getResultList();
		assertTrue(lista.size() == 1);
	}	

	@Test
	public void passarParametro() {
		String jpql = "select p from Pedido p join p.pagamento pag where p.id = :pedidoId and pag.status = :pagamentoStatus";
		
		TypedQuery<Pedido> typedQuery = entityManager.createQuery(jpql, Pedido.class);
		typedQuery.setParameter("pedidoId", 2);
		typedQuery.setParameter("pagamentoStatus", StatusPagamento.PROCESSANDO);
		
		List<Pedido> lista = typedQuery.getResultList();
		assertTrue(lista.size() == 1);
	}	
}
