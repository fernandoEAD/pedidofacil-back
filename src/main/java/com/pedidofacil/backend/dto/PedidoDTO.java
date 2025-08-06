package com.pedidofacil.backend.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    @NotBlank(message = "Nome do comprador é obrigatório")
    private String nomeComprador;

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    private String nomeFornecedor;

    private BigDecimal valorTotalComprado;

    private Integer totalProdutosComprados;

    @Valid
    private List<ProdutoPedidoDTO> produtos = new ArrayList<>();
} 