package com.pedidofacil.backend.service;

import com.pedidofacil.backend.dto.PedidoDTO;
import com.pedidofacil.backend.dto.ProdutoPedidoDTO;
import com.pedidofacil.backend.entity.Pedido;
import com.pedidofacil.backend.entity.ProdutoPedido;
import com.pedidofacil.backend.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedidoMock;
    private PedidoDTO pedidoDTOMock;

    @BeforeEach
    void setUp() {
        // Criar mock de entidade
        pedidoMock = new Pedido();
        pedidoMock.setId(1L);
        pedidoMock.setNomeComprador("João Silva");
        pedidoMock.setNomeFornecedor("TechStore");
        pedidoMock.setValorTotalComprado(new BigDecimal("1000.00"));
        pedidoMock.setTotalProdutosComprados(2);

        ProdutoPedido produto = new ProdutoPedido();
        produto.setId(1L);
        produto.setNomeProduto("Notebook");
        produto.setQuantidadeComprada(1);
        produto.setValorTotalProduto(new BigDecimal("800.00"));
        produto.setPedido(pedidoMock);

        pedidoMock.setProdutos(Arrays.asList(produto));

        // Criar mock de DTO
        pedidoDTOMock = new PedidoDTO();
        pedidoDTOMock.setId(1L);
        pedidoDTOMock.setNomeComprador("João Silva");
        pedidoDTOMock.setNomeFornecedor("TechStore");
        pedidoDTOMock.setValorTotalComprado(new BigDecimal("1000.00"));
        pedidoDTOMock.setTotalProdutosComprados(2);

        ProdutoPedidoDTO produtoDTO = new ProdutoPedidoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNomeProduto("Notebook");
        produtoDTO.setQuantidadeComprada(1);
        produtoDTO.setValorTotalProduto(new BigDecimal("800.00"));

        pedidoDTOMock.setProdutos(Arrays.asList(produtoDTO));
    }

    @Test
    void listarTodos_DeveRetornarListaDePedidos() {
        // Arrange
        List<Pedido> pedidos = Arrays.asList(pedidoMock);
        when(pedidoRepository.findAll()).thenReturn(pedidos);

        // Act
        List<PedidoDTO> resultado = pedidoService.listarTodos();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("João Silva", resultado.get(0).getNomeComprador());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_ComIdValido_DeveRetornarPedido() {
        // Arrange
        when(pedidoRepository.findByIdWithProdutos(1L)).thenReturn(pedidoMock);

        // Act
        PedidoDTO resultado = pedidoService.buscarPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("João Silva", resultado.getNomeComprador());
        verify(pedidoRepository, times(1)).findByIdWithProdutos(1L);
    }

    @Test
    void buscarPorId_ComIdInvalido_DeveLancarExcecao() {
        // Arrange
        when(pedidoRepository.findByIdWithProdutos(999L)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.buscarPorId(999L);
        });

        assertEquals("Pedido não encontrado com ID: 999", exception.getMessage());
        verify(pedidoRepository, times(1)).findByIdWithProdutos(999L);
    }

    @Test
    void criar_ComDadosValidos_DeveRetornarPedidoCriado() {
        // Arrange
        PedidoDTO novoPedidoDTO = new PedidoDTO();
        novoPedidoDTO.setNomeComprador("Maria Santos");
        novoPedidoDTO.setNomeFornecedor("OfficeMax");
        novoPedidoDTO.setProdutos(new ArrayList<>());

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoMock);

        // Act
        PedidoDTO resultado = pedidoService.criar(novoPedidoDTO);

        // Assert
        assertNotNull(resultado);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void atualizar_ComIdValido_DeveRetornarPedidoAtualizado() {
        // Arrange
        when(pedidoRepository.findByIdWithProdutos(1L)).thenReturn(pedidoMock);
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedidoMock);

        PedidoDTO pedidoAtualizado = new PedidoDTO();
        pedidoAtualizado.setNomeComprador("João Silva Atualizado");
        pedidoAtualizado.setNomeFornecedor("TechStore Updated");
        pedidoAtualizado.setProdutos(new ArrayList<>());

        // Act
        PedidoDTO resultado = pedidoService.atualizar(1L, pedidoAtualizado);

        // Assert
        assertNotNull(resultado);
        verify(pedidoRepository, times(1)).findByIdWithProdutos(1L);
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    void atualizar_ComIdInvalido_DeveLancarExcecao() {
        // Arrange
        when(pedidoRepository.findByIdWithProdutos(999L)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.atualizar(999L, pedidoDTOMock);
        });

        assertEquals("Pedido não encontrado com ID: 999", exception.getMessage());
        verify(pedidoRepository, times(1)).findByIdWithProdutos(999L);
        verify(pedidoRepository, never()).save(any(Pedido.class));
    }

    @Test
    void deletar_ComIdValido_DeveExecutarSemErro() {
        // Arrange
        when(pedidoRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pedidoRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> pedidoService.deletar(1L));

        // Assert
        verify(pedidoRepository, times(1)).existsById(1L);
        verify(pedidoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletar_ComIdInvalido_DeveLancarExcecao() {
        // Arrange
        when(pedidoRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.deletar(999L);
        });

        assertEquals("Pedido não encontrado com ID: 999", exception.getMessage());
        verify(pedidoRepository, times(1)).existsById(999L);
        verify(pedidoRepository, never()).deleteById(anyLong());
    }

    @Test
    void listarProdutosDoPedido_ComIdValido_DeveRetornarListaDeProdutos() {
        // Arrange
        when(pedidoRepository.findByIdWithProdutos(1L)).thenReturn(pedidoMock);

        // Act
        List<ProdutoPedidoDTO> resultado = pedidoService.listarProdutosDoPedido(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Notebook", resultado.get(0).getNomeProduto());
        verify(pedidoRepository, times(1)).findByIdWithProdutos(1L);
    }

    @Test
    void listarProdutosDoPedido_ComIdInvalido_DeveLancarExcecao() {
        // Arrange
        when(pedidoRepository.findByIdWithProdutos(999L)).thenReturn(null);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.listarProdutosDoPedido(999L);
        });

        assertEquals("Pedido não encontrado com ID: 999", exception.getMessage());
        verify(pedidoRepository, times(1)).findByIdWithProdutos(999L);
    }
} 