package com.pedidofacil.backend.repository;

import com.pedidofacil.backend.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Buscar pedidos por nome do comprador
    List<Pedido> findByNomeCompradorContainingIgnoreCase(String nomeComprador);

    // Buscar pedidos por nome do fornecedor
    List<Pedido> findByNomeFornecedorContainingIgnoreCase(String nomeFornecedor);

    // Buscar pedidos com valor total maior que um valor específico
    List<Pedido> findByValorTotalCompradoGreaterThan(BigDecimal valor);

    // Buscar pedidos com valor total entre dois valores
    List<Pedido> findByValorTotalCompradoBetween(BigDecimal valorMin, BigDecimal valorMax);

    // Query customizada para buscar pedidos com seus produtos
    @Query("SELECT p FROM Pedido p LEFT JOIN FETCH p.produtos WHERE p.id = :id")
    Pedido findByIdWithProdutos(@Param("id") Long id);

    // Contar total de produtos em todos os pedidos
    @Query("SELECT SUM(p.totalProdutosComprados) FROM Pedido p")
    Integer countTotalProdutos();
} 