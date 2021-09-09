package com.algaworks.ecommerce.iniciandocomjpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.model.Produto;

public class OperacoesComTrasacaoTest extends EntityManagerTest {
	
	@Test
	public void impedirOperacaoComBancoDeDados() {
		Produto produto = entityManager.find(Produto.class, 1);
		entityManager.detach(produto); // remover o objeto do gerenciamento do EntityManager e nenhuma a��o ser� realizada para esse objeto
		
		entityManager.getTransaction().begin();		
		produto.setNome("Kindle Paperwhite 2� Gera��o");
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao);
		assertEquals("Kindle", produtoVerificacao.getNome());
	}
	
	@Test
	public void mostrarDiferencaPersistMerge() {
		Produto produtoPersist = new Produto();
//		produtoPersist.setId(5); Comentado porque estamos utilizando IDENTITY
		produtoPersist.setNome("Smartphone One Olus");
		produtoPersist.setDescricao("O processador mais r�pido.");
		produtoPersist.setPreco(new BigDecimal(2000));
		produtoPersist.setDataCriacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		
//		o m�todo s� realiza a persist�ncia do objeto, coloca a inst�ncia persistida e coloca na m�moria do EntityManager
		entityManager.persist(produtoPersist);
		produtoPersist.setNome("Smarttphone One plus 2");
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacaoPersist = entityManager.find(Produto.class, produtoPersist.getId());
		assertNotNull(produtoVerificacaoPersist);

		Produto produtoMerge = new Produto();
//		produtoMerge.setId(6);
		produtoMerge.setNome("notebook Dell");
		produtoMerge.setDescricao("O melhor da categoria.");
		produtoMerge.setPreco(new BigDecimal(2000));
		produtoMerge.setDataCriacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		
//		o m�todo realiza a persist�ncia do objeto e tamb�m a atualiza��o, coloca uma c�pia inst�ncia persistida e coloca na m�moria do EntityManager
		produtoMerge = entityManager.merge(produtoMerge);
		produtoMerge.setNome("Notebook Dell 2");
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacaoMerge = entityManager.find(Produto.class, produtoMerge.getId());
		assertNotNull(produtoVerificacaoMerge);
	}

	@Test
	public void inserirObjetoComMerge() {
		Produto produto = new Produto();
//		produto.setId(4); Comentado porque estamos utilizando IDENTITY
		produto.setNome("Microfone Rode Videmic");
		produto.setDescricao("A melhor qualidade de som.");
		produto.setPreco(new BigDecimal(1000));
		produto.setDataCriacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		produto = entityManager.merge(produto); // tamb�m podemos fazer inser��o de objetos com o m�todo merge OBS: o m�todo merge n�o � obrigat�rio est� entre os m�toodos de transa��o
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao);
	}
	
	@Test
	public void atualizarObjetoGerenciado() {
		Produto produto = entityManager.find(Produto.class, 1);
		
		entityManager.getTransaction().begin();
		
//		OBS: essa altera��o do valor do atributo n�o � obrigat�rio est� entre os m�toodos de transa��o
		produto.setNome("Kindle Paperwhite 2� Gera��o"); // quando o objeto � gerenciado pelo EntityManager, quando alteramos alguma propriedade e tenha alguma trasan��o aberta � feita um update antes do fechamento da transa��o
		
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao);
		assertEquals("Kindle Paperwhite 2� Gera��o", produtoVerificacao.getNome());
	}

	@Test
	public void atualizarObjeto() {
		Produto produto = new Produto();
		produto.setId(1);
		produto.setNome("Kindle Paperwhite");
		produto.setDescricao("Conhe�a o novo Kindle");
		produto.setPreco(new BigDecimal(599));
		
		entityManager.getTransaction().begin();
		entityManager.merge(produto); // coloca o objeto no gerenciamento do EntityManager para ser atualizado no banco de dados OBS: o m�todo merge n�o � obrigat�rio est� entre os m�toodos de transa��o
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao);
		assertEquals("Kindle Paperwhite", produtoVerificacao.getNome());
	}
	
	@Test
	public void removerObjeto() {
		Produto produto = entityManager.find(Produto.class, 3);
		
		entityManager.getTransaction().begin();
		entityManager.remove(produto); // coloca o objeto no gerenciamento do EntityManager para ser removido no banco de dados OBS: o m�todo remove n�o � obrigat�rio est� entre os m�toodos de transa��o
		entityManager.getTransaction().commit();
		
//		entityManager.clear(); N�o � necess�rio na asser��o para opera��o remo��o
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNull(produtoVerificacao);
	}
	
	@Test
	public void inserirOPrimeiroObjeto() {
		Produto produto = new Produto();
//		produto.setId(2); Comentado porque estamos utilizando IDENTITY
		produto.setNome("C�mera Canon");
		produto.setDescricao("A melhor defini��o para suas fotos.");
		produto.setPreco(new BigDecimal(5000));
		produto.setDataCriacao(LocalDateTime.now());
		
		entityManager.getTransaction().begin();
		entityManager.persist(produto); // colocar o objeto no gerenciamento do EntityManager para ser inserido no banco de dados OBS: o m�todo persiste n�o � obrigat�rio est� entre os m�toodos de transa��o
		entityManager.getTransaction().commit();
		
		entityManager.clear();
		
		Produto produtoVerificacao = entityManager.find(Produto.class, produto.getId());
		assertNotNull(produtoVerificacao);
	}

	@Test
	public void abrirEFecharATransacao() {
//		Produto produto = new Produto();  Somente para  o m�todo n�o mostrar erro
		
		entityManager.getTransaction().begin(); // abertura da transa��o
		
//		a��es que podemos fazer entre a abertura e fechamento das transa��es
//		entityManager.persist(produto);
//		entityManager.merge(produto);
//		entityManager.remove(produto);
		
		entityManager.getTransaction().commit(); // fechamento da transa��o
	}
}
