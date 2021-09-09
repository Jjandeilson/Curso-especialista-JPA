package com.algaworks.ecommerce.metalmodel;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Produto;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Categoria.class)
public abstract class Categoria_ extends EntidadeBaseInteger_ {

	public static volatile ListAttribute<Categoria, Produto> produtos;
	public static volatile ListAttribute<Categoria, Categoria> categorias;
	public static volatile SingularAttribute<Categoria, String> nome;
	public static volatile SingularAttribute<Categoria, Categoria> categoriaPai;

	public static final String PRODUTOS = "produtos";
	public static final String CATEGORIAS = "categorias";
	public static final String NOME = "nome";
	public static final String CATEGORIA_PAI = "categoriaPai";

}
