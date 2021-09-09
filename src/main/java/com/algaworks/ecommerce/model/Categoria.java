package com.algaworks.ecommerce.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "categoria", uniqueConstraints = @UniqueConstraint(name = "unq_nome", columnNames = {"nome"}))
public class Categoria extends EntidadeBaseInteger{


//	O id está vindo da class extendida na forma de herança
//	@Id
//	@EqualsAndHashCode.Include
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
//	@SequenceGenerator(name = "seq", sequenceName = "sequencia_chave_primaria")
//	@GeneratedValue(strategy = GenerationType.TABLE, generator = "tabela")
//	@TableGenerator(name = "tabela", table = "hibernate_sequences", pkColumnName = "sequence_name",
//			pkColumnValue = "categoria", valueColumnName = "next_val", initialValue = 0, allocationSize = 50)
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Integer id;
	
	@NotBlank
	@Column(length = 100, nullable = false)
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "categoria_pai_id", foreignKey = @ForeignKey(name = "fk_categoria_categoria"))
	private Categoria categoriaPai;

	@OneToMany(mappedBy = "categoriaPai")
	private List<Categoria> categorias;
	
	@ManyToMany(mappedBy = "categorias")
	private List<Produto> produtos;
}
