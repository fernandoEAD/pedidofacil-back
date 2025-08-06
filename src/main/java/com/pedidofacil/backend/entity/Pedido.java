package com.pedidofacil.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do comprador é obrigatório")
    @Column(name = "nome_comprador", nullable = false)
    private String nomeComprador;

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    @Column(name = "nome_fornecedor", nullable = false)
    private String nomeFornecedor;

    @NotNull(message = "Valor total é obrigatório")
    @PositiveOrZero(message = "Valor total deve ser maior ou igual a zero")
    @Column(name = "valor_total_comprado", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorTotalComprado;

    @Column(name = "total_produtos_comprados", nullable = false)
    private Integer totalProdutosComprados = 0;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private List<ProdutoPedido> produtos = new ArrayList<>();

    // Métodos auxiliares para manter sincronização
    public void addProduto(ProdutoPedido produto) {
        produtos.add(produto);
        produto.setPedido(this);
        calcularTotais();
    }

    public void removeProduto(ProdutoPedido produto) {
        produtos.remove(produto);
        produto.setPedido(null);
        calcularTotais();
    }

    public void calcularTotais() {
        this.totalProdutosComprados = produtos.stream()
                .mapToInt(ProdutoPedido::getQuantidadeComprada)
                .sum();
        
        this.valorTotalComprado = produtos.stream()
                .map(ProdutoPedido::getValorTotalProduto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 