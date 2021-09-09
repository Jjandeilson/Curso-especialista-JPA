package com.algaworks.ecommerce.jpql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class DynamicQueryTest extends EntityManagerTest {

	@Test
	public void executarConsultaDinamica() {
		Produto consultado = new Produto();
		consultado.setNome("Câmera GoPro");
		
		List<Produto> lista = pesquisar(consultado);
		
		assertFalse(lista.isEmpty());
		assertEquals("Câmera GoPro Hero 7", lista.get(0).getNome());
	}

	private List<Produto> pesquisar(Produto consultado) {
		StringBuilder jpql = new StringBuilder("select p from Produto p where 1 = 1");
		
		if(consultado.getNome() != null) {
			jpql.append(" and p.nome like concat('%', :nome, '%')");
		}

		if(consultado.getDescricao() != null) {
			jpql.append(" and p.descricao like concat('%', :descricao, '%')");
		}
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(jpql.toString(), Produto.class);
		
		if(consultado.getNome() != null) {
			typedQuery.setParameter("nome", consultado.getNome());
		}

		if(consultado.getDescricao() != null) {
			typedQuery.setParameter("descricao", consultado.getDescricao());
		}
		return typedQuery.getResultList();
	}
}
