package com.pedidofacil.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pedidofacil.backend.dto.PedidoDTO;
import com.pedidofacil.backend.dto.ProdutoPedidoDTO;
import com.pedidofacil.backend.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PedidoController.class)
class PedidoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PedidoService pedidoService;

    @Autowired
    private ObjectMapper objectMapper;

    private PedidoDTO pedidoDTO;
    private ProdutoPedidoDTO produtoDTO;

    @BeforeEach
    void setUp() {
        produtoDTO = new ProdutoPedidoDTO();
        produtoDTO.setId(1L);
        produtoDTO.setNomeProduto("Notebook");
        produtoDTO.setQuantidadeComprada(1);
        produtoDTO.setValorTotalProduto(new BigDecimal("1000.00"));

        pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(1L);
        pedidoDTO.setNomeComprador("João Silva");
        pedidoDTO.setNomeFornecedor("TechStore");
        pedidoDTO.setValorTotalComprado(new BigDecimal("1000.00"));
        pedidoDTO.setTotalProdutosComprados(1);
        pedidoDTO.setProdutos(Arrays.asList(produtoDTO));
    }

    @Test
    void listarTodos_DeveRetornarListaDePedidos() throws Exception {
        // Arrange
        List<PedidoDTO> pedidos = Arrays.asList(pedidoDTO);
        when(pedidoService.listarTodos()).thenReturn(pedidos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomeComprador").value("João Silva"))
                .andExpect(jsonPath("$[0].nomeFornecedor").value("TechStore"));

        verify(pedidoService, times(1)).listarTodos();
    }

    @Test
    void buscarPorId_ComIdValido_DeveRetornarPedido() throws Exception {
        // Arrange
        when(pedidoService.buscarPorId(1L)).thenReturn(pedidoDTO);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nomeComprador").value("João Silva"))
                .andExpect(jsonPath("$.nomeFornecedor").value("TechStore"));

        verify(pedidoService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_ComIdInvalido_DeveRetornarNotFound() throws Exception {
        // Arrange
        when(pedidoService.buscarPorId(999L)).thenThrow(new RuntimeException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).buscarPorId(999L);
    }

    @Test
    void criar_ComDadosValidos_DeveRetornarPedidoCriado() throws Exception {
        // Arrange
        PedidoDTO novoPedido = new PedidoDTO();
        novoPedido.setNomeComprador("Maria Santos");
        novoPedido.setNomeFornecedor("OfficeMax");

        when(pedidoService.criar(any(PedidoDTO.class))).thenReturn(pedidoDTO);

        // Act & Assert
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(novoPedido)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(pedidoService, times(1)).criar(any(PedidoDTO.class));
    }

    @Test
    void criar_ComDadosInvalidos_DeveRetornarBadRequest() throws Exception {
        // Arrange
        PedidoDTO pedidoInvalido = new PedidoDTO();
        // Não definir campos obrigatórios

        // Act & Assert
        mockMvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoInvalido)))
                .andExpect(status().isBadRequest());

        verify(pedidoService, never()).criar(any(PedidoDTO.class));
    }

    @Test
    void atualizar_ComDadosValidos_DeveRetornarPedidoAtualizado() throws Exception {
        // Arrange
        PedidoDTO pedidoAtualizado = new PedidoDTO();
        pedidoAtualizado.setNomeComprador("João Silva Atualizado");
        pedidoAtualizado.setNomeFornecedor("TechStore Updated");

        when(pedidoService.atualizar(eq(1L), any(PedidoDTO.class))).thenReturn(pedidoDTO);

        // Act & Assert
        mockMvc.perform(put("/api/pedidos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoAtualizado)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1));

        verify(pedidoService, times(1)).atualizar(eq(1L), any(PedidoDTO.class));
    }

    @Test
    void atualizar_ComIdInvalido_DeveRetornarNotFound() throws Exception {
        // Arrange
        PedidoDTO pedidoAtualizado = new PedidoDTO();
        pedidoAtualizado.setNomeComprador("João Silva");
        pedidoAtualizado.setNomeFornecedor("TechStore");

        when(pedidoService.atualizar(eq(999L), any(PedidoDTO.class)))
                .thenThrow(new RuntimeException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/pedidos/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pedidoAtualizado)))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).atualizar(eq(999L), any(PedidoDTO.class));
    }

    @Test
    void deletar_ComIdValido_DeveRetornarNoContent() throws Exception {
        // Arrange
        doNothing().when(pedidoService).deletar(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/pedidos/1"))
                .andExpect(status().isNoContent());

        verify(pedidoService, times(1)).deletar(1L);
    }

    @Test
    void deletar_ComIdInvalido_DeveRetornarNotFound() throws Exception {
        // Arrange
        doThrow(new RuntimeException("Pedido não encontrado")).when(pedidoService).deletar(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/pedidos/999"))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).deletar(999L);
    }

    @Test
    void listarProdutosDoPedido_ComIdValido_DeveRetornarListaDeProdutos() throws Exception {
        // Arrange
        List<ProdutoPedidoDTO> produtos = Arrays.asList(produtoDTO);
        when(pedidoService.listarProdutosDoPedido(1L)).thenReturn(produtos);

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/1/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nomeProduto").value("Notebook"));

        verify(pedidoService, times(1)).listarProdutosDoPedido(1L);
    }

    @Test
    void listarProdutosDoPedido_ComIdInvalido_DeveRetornarNotFound() throws Exception {
        // Arrange
        when(pedidoService.listarProdutosDoPedido(999L))
                .thenThrow(new RuntimeException("Pedido não encontrado"));

        // Act & Assert
        mockMvc.perform(get("/api/pedidos/999/produtos"))
                .andExpect(status().isNotFound());

        verify(pedidoService, times(1)).listarProdutosDoPedido(999L);
    }
} 