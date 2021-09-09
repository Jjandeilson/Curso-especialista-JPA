package com.algaworks.ecommerce.metalmodel;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Pagamento.class)
public abstract class Pagamento_ extends EntidadeBaseInteger_ {

	public static volatile SingularAttribute<Pagamento, Pedido> pedido;
	public static volatile SingularAttribute<Pagamento, StatusPagamento> status;

	public static final String PEDIDO = "pedido";
	public static final String STATUS = "status";

}

