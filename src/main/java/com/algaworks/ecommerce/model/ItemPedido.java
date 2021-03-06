package com.algaworks.ecommerce.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SqlResultSetMappings({
	@SqlResultSetMapping(name = "item_pedido-produto.ItemPedido-Produto", 
	entities = { @EntityResult(entityClass =  ItemPedido.class), @EntityResult(entityClass = Produto.class)})
})
//@IdClass(ItemPedidoId.class) // Mapeamento para chave composta 
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "item_pedido")
public class ItemPedido {

//	Forma utilizada para @IdClass
//	@Id
//	@EqualsAndHashCode.Include
//	@Column(name = "pedido_id")
//	private Integer pedidoId;
//
//	@Id
//	@EqualsAndHashCode.Include
//	@Column(name = "produto_id")
//	private Integer produtoId;
	
	@Version
	private Integer versao;
	
	@EmbeddedId
	private ItemPedidoId id;

//	@ManyToOne(optional = false, cascade = CascadeType.MERGE)
//	@ManyToOne(optional = false,cascade = CascadeType.REMOVE)
	@NotNull
	@MapsId("pedidoId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_item_pedido_pedido")) // como est? sendo utilizado o @MapsId foi retirado , insertable = false, updatable = false
	private Pedido pedido;
	
	@NotNull
	@MapsId("produtoId")
	@ManyToOne(optional = false)
	@JoinColumn(name = "produto_id", nullable = false,
		foreignKey = @ForeignKey(name = "fk_item_pedido_produto")) // como est? sendo utilizado o @MapsId foi retirado , insertable = false, updatable = false
	private Produto produto;
	
	@Positive
	@NotNull
	@Column(name = "preco_produto", nullable = false)
	private BigDecimal precoProduto;
	
	@Positive
	@NotNull
	@Column(nullable = false)
	private Integer quantidade;
}
