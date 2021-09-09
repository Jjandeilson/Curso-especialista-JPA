package com.algaworks.ecommerce.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@DiscriminatorValue("boleto")
@Getter
@Setter
@Entity
//@Table(name = "pagamento_boleto") // A estrategeria é uma sigle table
public class PagamentoBoleto extends Pagamento {

//	O id está vindo da class extendida na forma de herança
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@EqualsAndHashCode.Include
//	private Integer id;
	
//	atributos movidos para uma classe abstrata	
//	@Column(name = "pedido_id")
//	private Integer pedidoId;
//	
//	@Enumerated(EnumType.STRING)
//	private StatusPagamento status;
	
	@NotBlank
	@Column(name = "codigo_barras", length = 100)
	private String codigoBarras;
	
	@NotNull
	@FutureOrPresent
	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;
}
