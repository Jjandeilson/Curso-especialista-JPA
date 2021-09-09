package com.algaworks.ecommerce.relacionamentos;

import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.PagamentoCartao;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.StatusPagamento;

public class RelacionamentoOneToOneTest extends EntityManagerTest {
	
	@Test
	public void verificacaoRelacionamentoPedidoNotaFiscal() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setXml("TESTE".getBytes());
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setPedido(pedido);
		
		entityManager.getTransaction().begin();
		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertNotNull(pedidoVerificacao.getNotaFiscal());
	}
	
	@Test
	public void verificarRelacionamento() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		PagamentoCartao pagamentaoCartao = new PagamentoCartao();
		pagamentaoCartao.setNumeroCartao("1234");
		pagamentaoCartao.setStatus(StatusPagamento.PROCESSANDO);
		pagamentaoCartao.setPedido(pedido);
		
		entityManager.getTransaction().begin();
		entityManager.persist(pagamentaoCartao);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Pedido pedidoVerificacao = entityManager.find(Pedido.class, pedido.getId());
		assertNotNull(pedidoVerificacao.getPagamento());
	}
}
