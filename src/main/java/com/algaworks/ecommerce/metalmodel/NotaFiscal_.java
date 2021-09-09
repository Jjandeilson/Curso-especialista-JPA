package com.algaworks.ecommerce.metalmodel;

import java.util.Date;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(NotaFiscal.class)
public abstract class NotaFiscal_ extends EntidadeBaseInteger_ {

	public static volatile SingularAttribute<NotaFiscal, byte[]> xml;
	public static volatile SingularAttribute<NotaFiscal, Pedido> pedido;
	public static volatile SingularAttribute<NotaFiscal, Date> dataEmissao;

	public static final String XML = "xml";
	public static final String PEDIDO = "pedido";
	public static final String DATA_EMISSAO = "dataEmissao";

}

