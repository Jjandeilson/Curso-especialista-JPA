package com.algaworks.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;

import com.algaworks.ecommerce.listener.GenericoListener;
import com.algaworks.ecommerce.listener.GerarNotaFiscalListener;

import lombok.Getter;
import lombok.Setter;

@NamedEntityGraphs({
	@NamedEntityGraph(
			name = "Pedido.dadosEssenciais",
			attributeNodes = {
					@NamedAttributeNode("dataCriacao"),
					@NamedAttributeNode("status"),
					@NamedAttributeNode("total"),
					@NamedAttributeNode(
							value="cliente",
							subgraph = "cli"),
			},
			subgraphs = {
					@NamedSubgraph(
							name = "cli",
							attributeNodes = {
									@NamedAttributeNode("nome"),
									@NamedAttributeNode("cpf"),
							})
			})
})
@EntityListeners({GerarNotaFiscalListener.class, GenericoListener.class})
@Getter
@Setter
@Entity
@Table(name = "pedido")
public class Pedido extends EntidadeBaseInteger /*implements PersistentAttributeInterceptable*/{

//	O id está vindo da class extendida na forma de herança
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@EqualsAndHashCode.Include
//	private Integer id;
	
//	@ManyToOne(optional = false, cascade = CascadeType.PERSIST)
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente"))
	private Cliente cliente;
	
//	@OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
//	@OneToMany(mappedBy = "pedido", cascade = CascadeType.MERGE)
//	@OneToMany(mappedBy = "pedido", cascade = CascadeType.REMOVE)
//	@OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST, orphanRemoval = true)
	@NotEmpty
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itens;
	
	@PastOrPresent
	@Column(name = "data_criacao", updatable = false, nullable = false)
	private LocalDateTime dataCriacao;

	@PastOrPresent
	@Column(name = "data_ultima_atualizacao", insertable = false)
	private LocalDateTime dataUltimaAtualizacao;
	
	@PastOrPresent
	@Column(name = "data_conclusao")
	private LocalDateTime dataConclusao;
	
//	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(mappedBy = "pedido")
	private NotaFiscal notaFiscal;
	
	@NotNull
	@Positive
	@Column(nullable = false)
	private BigDecimal total;
	
	@NotNull
	@Column(length = 30, nullable = false)
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
//	@OneToOne(mappedBy = "pedido")
//	private PagamentoCartao pagamento;
	
//	@LazyToOne(LazyToOneOption.NO_PROXY)
	@OneToOne(mappedBy = "pedido")
	private Pagamento pagamento;
	
	@Embedded
	private EnderecoEntregaPedido enderecoEntrega;
	
//	Implementação para resolver o problema do N+1
//	public NotaFiscal getNotaFiscal(){
//		if(this.persistentAttributeInterceptor != null) {
//			return (NotaFiscal) persistentAttributeInterceptor.readObject(this, "notaFiscal",this.notaFiscal);
//		}
//		return this.notaFiscal;
//	}
//	
//	public void setNotaFiscal(NotaFiscal notaFiscal) {
//		if(this.persistentAttributeInterceptor != null) {
//			this.notaFiscal = (NotaFiscal) persistentAttributeInterceptor.writeObject(this, "notaFiscal", this.notaFiscal, notaFiscal);
//		}else {
//			this.notaFiscal = notaFiscal;			
//		}
//	}
//	
//	public Pagamento getPagamento() {
//		if(this.persistentAttributeInterceptor != null) {
//			return (Pagamento) persistentAttributeInterceptor.readObject(this, "pagamento",this.pagamento);
//		}
//		return this.pagamento;
//	}
//	
//	public void setPagamento(Pagamento pagamento) {
//		if(this.persistentAttributeInterceptor != null) {
//			this.pagamento = (Pagamento) persistentAttributeInterceptor.writeObject(this, "pagamento", this.pagamento, pagamento);
//		}else {			
//			this.pagamento = pagamento;
//		}
//	}
//	
//	@Setter(AccessLevel.NONE)
//	@Getter(AccessLevel.NONE)
//	@Transient
//	private PersistentAttributeInterceptor persistentAttributeInterceptor;
//	
//	@Override
//	public PersistentAttributeInterceptor $$_hibernate_getInterceptor() {
//		return this.persistentAttributeInterceptor;
//	}
//
//	@Override
//	public void $$_hibernate_setInterceptor(PersistentAttributeInterceptor interceptor) {
//		this.persistentAttributeInterceptor = interceptor;
//	}
	
	public boolean isPago() {
		return StatusPedido.PAGO.equals(status);
	}
	
	// callbacks do hibernate
	
//	@PrePersist
//	@PreUpdate
	public void calcularTotal() {
		if(itens != null) {
			total = itens.stream().map(i -> new BigDecimal(i.getQuantidade()).multiply(i.getPrecoProduto()))
						.reduce(BigDecimal.ZERO, BigDecimal::add);
		}else {
			total = BigDecimal.ZERO;
		}
	}
	
	@PrePersist // é executado antes de persistir o objeto 
	public void aoPersistir() {
		dataCriacao = LocalDateTime.now();
		calcularTotal();
	}
	
	@PreUpdate // é executado antes de atualizar o objeto
	public void aoAtualizar() {
		dataUltimaAtualizacao = LocalDateTime.now();
		calcularTotal();
	}
	
	@PostPersist
	public void aposPersistir() {
		System.out.println("Após persistir Pedido.");
	}
	
	@PostUpdate
	public void aposAtualizar() {
		System.out.println("Após atualizar Pedido.");
	}
	
	@PreRemove
	public void aoRemover() {
		System.out.println("Antes de remover Pedido.");
	}
	
	@PostRemove
	public void aposRemover() {
		System.out.println("Após remover Pedido");
	}
	
	@PostLoad
	public void aoCarregar() {
		System.out.println("Após carregar o Pedido.");
	}
}
