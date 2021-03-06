package com.algaworks.ecommerce.consultasnativas;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;

public class StoredProceduresTest extends EntityManagerTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void chamarNamedStoredProcedure() {
		StoredProcedureQuery storedProcedureQuery = entityManager.createNamedStoredProcedureQuery("compraram_acima_media");
		
		storedProcedureQuery.setParameter("ano", 2021);
		
		List<Cliente> lista = storedProcedureQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}
	
	@Test
    public void atualizarPrecoProdutoExercicio() {
        StoredProcedureQuery storedProcedureQuery = entityManager
                .createStoredProcedureQuery("ajustar_preco_produto", Cliente.class);

        storedProcedureQuery.registerStoredProcedureParameter(
                "produto_id", Integer.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "percentual_ajuste", BigDecimal.class, ParameterMode.IN);

        storedProcedureQuery.registerStoredProcedureParameter(
                "preco_ajustado", BigDecimal.class, ParameterMode.OUT);

        storedProcedureQuery.setParameter("produto_id", 1);
        storedProcedureQuery.setParameter("percentual_ajuste", new BigDecimal("0.1"));

        BigDecimal precoAjustado = (BigDecimal) storedProcedureQuery
                .getOutputParameterValue("preco_ajustado");

        assertEquals(new BigDecimal("878.9"), precoAjustado);
    }

	@SuppressWarnings("unchecked")
	@Test
	public void receberListaDaProcedure() {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("compraram_acima_media");
		
		storedProcedureQuery.registerStoredProcedureParameter("ano", Integer.class, ParameterMode.IN);
		
		storedProcedureQuery.setParameter("ano", 2021);
		
		List<Cliente> lista = storedProcedureQuery.getResultList();
		
		assertFalse(lista.isEmpty());
	}

	@Test
	public void usarParametrosInEOut() {
		StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("buscar_nome_produto");
		
		storedProcedureQuery.registerStoredProcedureParameter("produto_id", Integer.class, ParameterMode.IN);
		storedProcedureQuery.registerStoredProcedureParameter("produto_nome", String.class, ParameterMode.OUT);
		
		storedProcedureQuery.setParameter("produto_id", 1);
		
		String nome = (String) storedProcedureQuery.getOutputParameterValue("produto_nome");
		
		assertEquals("Kindle", nome);
	}
}
