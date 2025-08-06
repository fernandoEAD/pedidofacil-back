package com.pedidofacil.backend.service;

import com.pedidofacil.backend.dto.PedidoDTO;
import com.pedidofacil.backend.dto.ProdutoPedidoDTO;
import com.pedidofacil.backend.entity.Pedido;
import com.pedidofacil.backend.entity.ProdutoPedido;
import com.pedidofacil.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public List<PedidoDTO> listarTodos() {
        log.info("Listando todos os pedidos");
        return pedidoRepository.findAll().stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public PedidoDTO buscarPorId(Long id) {
        log.info("Buscando pedido por ID: {}", id);
        Pedido pedido = pedidoRepository.findByIdWithProdutos(id);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado com ID: " + id);
        }
        return entityToDTO(pedido);
    }

    public PedidoDTO criar(PedidoDTO pedidoDTO) {
        log.info("Criando novo pedido para comprador: {}", pedidoDTO.getNomeComprador());
        
        Pedido pedido = new Pedido();
        pedido.setNomeComprador(pedidoDTO.getNomeComprador());
        pedido.setNomeFornecedor(pedidoDTO.getNomeFornecedor());
        pedido.setValorTotalComprado(BigDecimal.ZERO);
        pedido.setTotalProdutosComprados(0);

        // Adicionar produtos se existirem
        if (pedidoDTO.getProdutos() != null && !pedidoDTO.getProdutos().isEmpty()) {
            for (ProdutoPedidoDTO produtoDTO : pedidoDTO.getProdutos()) {
                ProdutoPedido produto = new ProdutoPedido();
                produto.setNomeProduto(produtoDTO.getNomeProduto());
                produto.setQuantidadeComprada(produtoDTO.getQuantidadeComprada());
                produto.setValorTotalProduto(produtoDTO.getValorTotalProduto());
                pedido.addProduto(produto);
            }
        }

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        log.info("Pedido criado com ID: {}", pedidoSalvo.getId());
        return entityToDTO(pedidoSalvo);
    }

    public PedidoDTO atualizar(Long id, PedidoDTO pedidoDTO) {
        log.info("Atualizando pedido ID: {}", id);
        
        Pedido pedidoExistente = pedidoRepository.findByIdWithProdutos(id);
        if (pedidoExistente == null) {
            throw new RuntimeException("Pedido não encontrado com ID: " + id);
        }

        // Atualizar dados básicos
        pedidoExistente.setNomeComprador(pedidoDTO.getNomeComprador());
        pedidoExistente.setNomeFornecedor(pedidoDTO.getNomeFornecedor());

        // Limpar produtos existentes
        pedidoExistente.getProdutos().clear();

        // Adicionar novos produtos
        if (pedidoDTO.getProdutos() != null && !pedidoDTO.getProdutos().isEmpty()) {
            for (ProdutoPedidoDTO produtoDTO : pedidoDTO.getProdutos()) {
                ProdutoPedido produto = new ProdutoPedido();
                produto.setNomeProduto(produtoDTO.getNomeProduto());
                produto.setQuantidadeComprada(produtoDTO.getQuantidadeComprada());
                produto.setValorTotalProduto(produtoDTO.getValorTotalProduto());
                pedidoExistente.addProduto(produto);
            }
        }

        pedidoExistente.calcularTotais();
        Pedido pedidoAtualizado = pedidoRepository.save(pedidoExistente);
        log.info("Pedido atualizado com ID: {}", pedidoAtualizado.getId());
        return entityToDTO(pedidoAtualizado);
    }

    public void deletar(Long id) {
        log.info("Deletando pedido ID: {}", id);
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido não encontrado com ID: " + id);
        }
        pedidoRepository.deleteById(id);
        log.info("Pedido deletado com ID: {}", id);
    }

    public List<ProdutoPedidoDTO> listarProdutosDoPedido(Long pedidoId) {
        log.info("Listando produtos do pedido ID: {}", pedidoId);
        Pedido pedido = pedidoRepository.findByIdWithProdutos(pedidoId);
        if (pedido == null) {
            throw new RuntimeException("Pedido não encontrado com ID: " + pedidoId);
        }
        
        return pedido.getProdutos().stream()
                .map(this::produtoEntityToDTO)
                .collect(Collectors.toList());
    }

    private PedidoDTO entityToDTO(Pedido pedido) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(pedido.getId());
        dto.setNomeComprador(pedido.getNomeComprador());
        dto.setNomeFornecedor(pedido.getNomeFornecedor());
        dto.setValorTotalComprado(pedido.getValorTotalComprado());
        dto.setTotalProdutosComprados(pedido.getTotalProdutosComprados());
        
        if (pedido.getProdutos() != null) {
            dto.setProdutos(pedido.getProdutos().stream()
                    .map(this::produtoEntityToDTO)
                    .collect(Collectors.toList()));
        }
        
        return dto;
    }

    private ProdutoPedidoDTO produtoEntityToDTO(ProdutoPedido produto) {
        ProdutoPedidoDTO dto = new ProdutoPedidoDTO();
        dto.setId(produto.getId());
        dto.setNomeProduto(produto.getNomeProduto());
        dto.setQuantidadeComprada(produto.getQuantidadeComprada());
        dto.setValorTotalProduto(produto.getValorTotalProduto());
        return dto;
    }
} 