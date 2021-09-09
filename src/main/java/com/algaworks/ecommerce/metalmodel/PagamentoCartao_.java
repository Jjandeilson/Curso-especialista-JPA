package com.algaworks.ecommerce.metalmodel;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.PagamentoCartao;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(PagamentoCartao.class)
public abstract class PagamentoCartao_ extends Pagamento_ {

	public static volatile SingularAttribute<PagamentoCartao, String> numeroCartao;

	public static final String NUMERO_CARTAO = "numeroCartao";

}

