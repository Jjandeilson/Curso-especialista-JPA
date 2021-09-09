package com.algaworks.ecommerce.mapeamentoavancado;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Cliente;
import com.algaworks.ecommerce.model.Pagamento;
import com.algaworks.ecommerce.model.PagamentoCartao;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.SexoCliente;
import com.algaworks.ecommerce.model.StatusPagamento;

public class HerancaTest extends EntityManagerTest {

	@Test
	public void salvarCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("Fernanda Morais");
		cliente.setSexo(SexoCliente.FEMININO);
		cliente.setCpf("333");
		
		entityManager.getTransaction().begin();
		entityManager.persist(cliente);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Cliente clienteVerificacao = entityManager.find(Cliente.class, cliente.getId());
		assertNotNull(clienteVerificacao.getId());
	}
	
	@Test
	public void buscarPagamentos() {
		List<Pagamento> pagamentos = entityManager.createQuery("select p from Pagamento p", Pagamento.class).getResultList();
		
		assertFalse(pagamentos.isEmpty());
	}
	
	@Test
	public void incluirPagamentoPedido() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		PagamentoCartao pagamento = new PagamentoCartao();
		pagamento.setPedido(pedido);
		pagamento.setStatus(StatusPagamento.PROCESSANDO);
		pagamento.setNumeroCartao("123");
		
		entityManager.getTransaction().begin();
		entityManager.persist(pagamento);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pagamento.getId());
		assertNotNull(pedidoVerificacao.getPagamento());
	}
}
