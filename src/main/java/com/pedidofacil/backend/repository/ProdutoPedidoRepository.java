package com.pedidofacil.backend.repository;

import com.pedidofacil.backend.entity.ProdutoPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProdutoPedidoRepository extends JpaRepository<ProdutoPedido, Long> {

    // Buscar produtos por ID do pedido
    List<ProdutoPedido> findByPedidoId(Long pedidoId);

    // Buscar produtos por nome do produto
    List<ProdutoPedido> findByNomeProdutoContainingIgnoreCase(String nomeProduto);

    // Buscar produtos com quantidade maior que um valor específico
    List<ProdutoPedido> findByQuantidadeCompradaGreaterThan(Integer quantidade);

    // Buscar produtos com valor total maior que um valor específico
    List<ProdutoPedido> findByValorTotalProdutoGreaterThan(BigDecimal valor);

    // Query para calcular total de produtos de um pedido específico
    @Query("SELECT SUM(pp.quantidadeComprada) FROM ProdutoPedido pp WHERE pp.pedido.id = :pedidoId")
    Integer sumQuantidadeByPedidoId(@Param("pedidoId") Long pedidoId);

    // Query para calcular valor total dos produtos de um pedido específico
    @Query("SELECT SUM(pp.valorTotalProduto) FROM ProdutoPedido pp WHERE pp.pedido.id = :pedidoId")
    BigDecimal sumValorTotalByPedidoId(@Param("pedidoId") Long pedidoId);

    // Buscar produtos mais caros
    @Query("SELECT pp FROM ProdutoPedido pp ORDER BY pp.valorTotalProduto DESC")
    List<ProdutoPedido> findTopByOrderByValorTotalProdutoDesc();
} 