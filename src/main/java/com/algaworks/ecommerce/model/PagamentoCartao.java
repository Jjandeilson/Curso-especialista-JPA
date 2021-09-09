package com.algaworks.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("cartao")
@Getter
@Setter
@Entity
//@Table(name = "pagamento_cartao") // A estrategeria é uma sigle table
public class PagamentoCartao extends Pagamento {

//	O id está vindo da class extendida na forma de herança
//	@Id
//	@EqualsAndHashCode.Include
//	@Column(name = "pedido_id")
//	private Integer id;
	
//	atributos movidos para uma classe abstrata
//	@MapsId
//	@OneToOne(optional = false)
//	@JoinColumn(name = "pedido_id")
//	private Pedido pedido;
//	
//	@Enumerated(EnumType.STRING)
//	private StatusPagamento status;
	
	@NotEmpty
	@Column(name = "numero_cartao", length = 50)
	private String numeroCartao;
}
