package com.algaworks.ecommerce.metalmodel;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.Estoque;
import com.algaworks.ecommerce.model.Produto;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Estoque.class)
public abstract class Estoque_ extends EntidadeBaseInteger_ {

	public static volatile SingularAttribute<Estoque, Produto> produto;
	public static volatile SingularAttribute<Estoque, Integer> quantidade;

	public static final String PRODUTO = "produto";
	public static final String QUANTIDADE = "quantidade";

}

