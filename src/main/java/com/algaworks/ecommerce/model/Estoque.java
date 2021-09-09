package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "estoque")
public class Estoque extends EntidadeBaseInteger{

//	O id está vindo da class extendida na forma de herança
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@EqualsAndHashCode.Include
//	private Integer id;
	
	@NotNull
	@OneToOne(optional = false)
	@JoinColumn(name = "produto_id", foreignKey = @ForeignKey(name = "fk_estoque_produto"))
	private Produto produto;
	
	@PositiveOrZero
	@NotNull
	@Column(nullable = false)
	private Integer quantidade;
}
