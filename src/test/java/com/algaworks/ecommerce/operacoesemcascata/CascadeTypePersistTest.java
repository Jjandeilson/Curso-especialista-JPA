package com.algaworks.ecommerce.operacoesemcascata;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.ItemPedidoId;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;
import com.algaworks.ecommerce.model.SexoCliente;
import com.algaworks.ecommerce.model.StatusPedido;

public class CascadeTypePersistTest extends EntityManagerTest {
	
//	@Test
	public void persistirProdutoComCategoria() {
		Produto produto = new Produto();
		produto.setDataCriacao(LocalDateTime.now());
		produto.setPreco(BigDecimal.TEN);
		produto.setNome("Fones de Ouvido");
		produto.setDescricao("A melhor qualidade de som");
		
		Categoria categoria = new Categoria();
		categoria.setNome("�udio");
		
		produto.setCategorias(Arrays.asList(categoria));
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Categoria catgoriaVerificacao = entityManager.find(Categoria.class, categoria.getId());
		assertNotNull(catgoriaVerificacao);
	}

//	@Test
	public void persistPedidoComItens() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(produto.getPreco());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido);
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(1);
		itemPedido.setPrecoProduto(produto.getPreco());

		pedido.setItens(Arrays.asList(itemPedido)); // CascadeType.PERSIST
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertNotNull(pedidoVerificacao);
		assertFalse(pedidoVerificacao.getItens().isEmpty());
		
	}
	
	@Test
	public void persistirItemPedidoComPedido() {
		Cliente cliente = entityManager.find(Cliente.class, 1);
		Produto produto = entityManager.find(Produto.class, 1);
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente); // CascadeType.PERSIST
		pedido.setTotal(produto.getPreco());
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setId(new ItemPedidoId());
		itemPedido.setPedido(pedido); // N�o � necess�rio CascadeType.PERSIST porque possui @MapsId.
		itemPedido.setProduto(produto);
		itemPedido.setQuantidade(1);
		itemPedido.setPrecoProduto(produto.getPreco());
		
		entityManager.getTransaction().begin();
		entityManager.persist(itemPedido); 
		entityManager.getTransaction().commit();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertNotNull(pedidoVerificacao);
	}
	
//	@Test
	public void persistirPedidoComCliente() {
		Cliente cliente = new Cliente();
		cliente.setDataNascimento(LocalDate.of(1980, 1, 1));
		cliente.setSexo(SexoCliente.MASCULINO);
		cliente.setNome("Jos� Carlos");
		cliente.setCpf("01234567890");
		
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now());
		pedido.setCliente(cliente);
		pedido.setTotal(BigDecimal.ZERO);
		pedido.setStatus(StatusPedido.AGUARDANDO);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pedido);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		assertNotNull(clienteVerificacao);
	}
}
