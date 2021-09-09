package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorColumn(name = "tipo_pagamento") // nome da coluna para diferenciar a entidades que extende essa classe quando salva
//@Inheritance(strategy = InheritanceType.JOINED) // tipo de henraça e como vai ser contruida as tabelas (Tabela para cada entidade)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) // tipo de henraça e como vai ser contruida as tabelas (Tabela para cada entidade)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // tipo de henraça e como vai ser contruida as tabelas (Tabela única)
@Getter
@Setter
@Entity
@Table(name = "pagamento")
public abstract class Pagamento extends EntidadeBaseInteger {

	@NotNull
	@MapsId
	@OneToOne(optional = false)
	@JoinColumn(name = "pedido_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pagamento_pedido"))
	private Pedido pedido;
	
	@NotNull
	@Column(length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPagamento status;
}
