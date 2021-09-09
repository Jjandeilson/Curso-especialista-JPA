package com.algaworks.ecommerce.metalmodel;

import java.math.BigDecimal;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ItemPedido.class)
public abstract class ItemPedido_ {

	public static volatile SingularAttribute<ItemPedido, BigDecimal> precoProduto;
	public static volatile SingularAttribute<ItemPedido, Produto> produto;
	public static volatile SingularAttribute<ItemPedido, Pedido> pedido;
	public static volatile SingularAttribute<ItemPedido, ItemPedidoId> id;
	public static volatile SingularAttribute<ItemPedido, Integer> quantidade;

	public static final String PRECO_PRODUTO = "precoProduto";
	public static final String PRODUTO = "produto";
	public static final String PEDIDO = "pedido";
	public static final String ID = "id";
	public static final String QUANTIDADE = "quantidade";

}

