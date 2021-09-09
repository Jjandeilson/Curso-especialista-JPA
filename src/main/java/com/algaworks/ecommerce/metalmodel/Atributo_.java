package com.algaworks.ecommerce.metalmodel;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.Atributo;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Atributo.class)
public abstract class Atributo_ {

	public static volatile SingularAttribute<Atributo, String> valor;
	public static volatile SingularAttribute<Atributo, String> nome;

	public static final String VALOR = "valor";
	public static final String NOME = "nome";

}

