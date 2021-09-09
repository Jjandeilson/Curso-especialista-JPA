package com.algaworks.ecommerce.criteria;

import static org.junit.Assert.assertFalse;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.metalmodel.Produto_;
import com.algaworks.ecommerce.model.Produto;

public class MetaModelTest extends EntityManagerTest{

	@Test
	public void utilizarMetaModel() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Produto> criteriaQuery = criteriaBuilder.createQuery(Produto.class);
		Root<Produto> root = criteriaQuery.from(Produto.class);
		
		criteriaQuery.select(root);
		
		criteriaQuery.where(criteriaBuilder.or(
				criteriaBuilder.like(root.get(Produto_.NOME), "%C%"),
				criteriaBuilder.like(root.get(Produto_.DESCRICAO), "%C%")
		));
		
		TypedQuery<Produto> typedQuery = entityManager.createQuery(criteriaQuery);
		List<Produto> lista = typedQuery.getResultList();
		assertFalse(lista.isEmpty());
	}
}
