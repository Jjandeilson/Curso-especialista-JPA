package com.algaworks.ecommerce.mapeamentoavancado;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Date;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.NotaFiscal;
import com.algaworks.ecommerce.model.Pedido;
import com.algaworks.ecommerce.model.Produto;

public class SalvandoArquivosTest extends EntityManagerTest {

	@Test
	public void salvarXmlNota() {
		Pedido pedido = entityManager.find(Pedido.class, 1);
		
		NotaFiscal notaFiscal = new NotaFiscal();
		notaFiscal.setPedido(pedido);
		notaFiscal.setDataEmissao(new Date());
		notaFiscal.setXml(carregarNotaFiscal());
		
		entityManager.getTransaction().begin();
		entityManager.persist(notaFiscal);
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		NotaFiscal notaFiscalVerificacao = entityManager.find(NotaFiscal.class, notaFiscal.getId());
		assertNotNull(notaFiscalVerificacao.getXml());
		assertTrue(notaFiscalVerificacao.getXml().length > 0);
		
//		try {
//            OutputStream out = new FileOutputStream(
//                    Files.createFile(Paths.get(
//                            System.getProperty("user.home") + "/xml.xml")).toFile());
//            out.write(notaFiscalVerificacao.getXml());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
	}
	
	@Test
	public void salvarFoto() {
		entityManager.getTransaction().begin();
		Produto produto = entityManager.find(Produto.class, 1);
		produto.setFoto(carregarFoto());
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao.getFoto());
		assertTrue(produtoVerificacao.getFoto().length > 0);
	}
	
	private byte[] carregarFoto() {
		try {
			return SalvandoArquivosTest.class.getResourceAsStream("/assinatura_email_Jandeilson (1).png").readAllBytes();
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static byte[] carregarNotaFiscal() {
		try {
			return SalvandoArquivosTest.class.getResourceAsStream("/nota-fiscal.xml").readAllBytes();
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}
