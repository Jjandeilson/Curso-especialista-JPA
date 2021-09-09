package com.algaworks.ecommerce.consultasnativas;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.algaworks.ecommerce.EntityManagerTest;
import com.algaworks.ecommerce.dto.CategoriaDTO;
import com.algaworks.ecommerce.dto.ProdutoDTO;
import com.algaworks.ecommerce.model.Categoria;
import com.algaworks.ecommerce.model.ItemPedido;
import com.algaworks.ecommerce.model.Produto;

public class ConsultasNativasTest extends EntityManagerTest {
	
	@SuppressWarnings("unchecked")
	@Test
	public void mapearConsultaParaDTOEmArquivoExternoExercicio() {
		Query query = entityManager.createNamedQuery("ecm_categoria.listar.dto");
		
		List<CategoriaDTO> lista = query.getResultList();
		
		lista.stream().forEach(obj -> System.out.println(
				String.format("CategoriaDTO => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void usarArquivoXML() {
		Query query = entityManager.createNamedQuery("ecm_categoria.listar");
		
		List<Categoria> lista = query.getResultList();
		
		lista.forEach(obj -> System.out.println(String.format("Categoria => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usarUmaNativeQuery02() {
		Query query = entityManager.createNamedQuery("ecm_produto.listar");
		
		List<Produto> lista = query.getResultList();
		
		lista.forEach(obj -> System.out.println(String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void usarUmaNativeQuery01() {
		Query query = entityManager.createNamedQuery("produto_loja.listar");
		
		List<Produto> lista = query.getResultList();
		
		lista.forEach(obj -> System.out.println(String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usarColumnResultRetornaDTO() {
		String jpql = "select * from ecm_produto";
		
		
		Query query = entityManager.createNativeQuery(jpql, "ecm_produto.ProdutoDTO");
		
		List<ProdutoDTO> lista = query.getResultList();
		
		lista.stream().forEach(obj -> System.out.println(
				String.format("ProdutoDTO s=> ID: %s, Nome: %s",obj.getId(), obj.getNome())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usarFieldResult() {
		String jpql = "select * from ecm_produto";
		
		
		Query query = entityManager.createNativeQuery(jpql, "ecm_produto.Produto");
		
		List<Produto> lista = query.getResultList();
		
		lista.stream().forEach(obj -> System.out.println(
				String.format("Produto => ID: %s, Nome: %s",obj.getId(), obj.getNome())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void usarSQLResultSetMapping02() {
		String jpql = "select ip.*, p.* from item_pedido ip join produto p on p.id = ip.produto_id";
		
		
		Query query = entityManager.createNativeQuery(jpql, "item_pedido-produto.ItemPedido-Produto");
		
		List<Object[]> lista = query.getResultList();
		
		lista.stream().forEach(arr -> System.out.println(
				String.format("Pedido => ID: %s ----Produto => ID: %s, Nome: %s",((ItemPedido)arr[0]).getId().getPedidoId(), ((Produto)arr[1]).getId(), ((Produto)arr[1]).getNome())));
	}
	
	@SuppressWarnings("unchecked")
	@Test
    public void usarSQLResultSetMapping01() {
        String sql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto " +
                " from produto_loja";

        Query query = entityManager.createNativeQuery(sql, "produto_loja.Produto");

        List<Produto> lista = query.getResultList();

        lista.stream().forEach(obj -> System.out.println(
                String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
    }
	
	@SuppressWarnings("unchecked")
	@Test
	public void passarParametro() {
		String jpql = "select prod_id id, prod_nome nome, prod_descricao descricao, prod_data_criacao data_criacao, "
				+ " prod_data_ultima_atualizacao data_ultima_atualizacao, prod_preco preco, prod_foto foto from ecm_produto "
				+ " where prd_id = :id";
		
		Query query = entityManager.createNativeQuery(jpql, Produto.class);
		query.setParameter("id", 201);
		
		List<Produto> lista = query.getResultList();
		
		lista.forEach(obj -> System.out.println(String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void executarSQLRetornandoEntidade() {
//		String jpql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto";
		
//		String jpql = "select id, nome, descricao, data_criacao, data_ultima_atualizacao, preco, foto from produto_loja";
		
//		String jpql = "select prod_id id, prod_nome nome, prod_descricao descricao, prod_data_criacao data_criacao, "
//				+ " prod_data_ultima_atualizacao data_ultima_atualizacao, prod_preco preco, prod_foto foto from ecm_produto";
		
		String jpql = "select id, nome, descricao, null data_criacao, null data_ultima_atualizacao, preco, null foto from erp_produto";
		
		Query query = entityManager.createNativeQuery(jpql, Produto.class);
		
		List<Produto> lista = query.getResultList();
		
		lista.forEach(obj -> System.out.println(String.format("Produto => ID: %s, Nome: %s", obj.getId(), obj.getNome())));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void executarSQL() {
		String jpql = "select id, nome from produto";
		
		Query query = entityManager.createNativeQuery(jpql);
		
		List<Object[]> lista = query.getResultList();
		
		lista.stream().forEach(arr -> System.out.println(String.format("Produto => ID: %s, Nome: %s", arr[0], arr[1])));
	}
}
