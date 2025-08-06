package com.pedidofacil.backend.controller;

import com.pedidofacil.backend.dto.PedidoDTO;
import com.pedidofacil.backend.dto.ProdutoPedidoDTO;
import com.pedidofacil.backend.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    @GetMapping
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<PedidoDTO>> listarTodos() {
        log.info("Endpoint GET /api/pedidos chamado");
        List<PedidoDTO> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PedidoDTO> buscarPorId(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        log.info("Endpoint GET /api/pedidos/{} chamado", id);
        try {
            PedidoDTO pedido = pedidoService.buscarPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar pedido: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PedidoDTO> criar(@Valid @RequestBody PedidoDTO pedidoDTO) {
        log.info("Endpoint POST /api/pedidos chamado");
        try {
            PedidoDTO pedidoCriado = pedidoService.criar(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCriado);
        } catch (Exception e) {
            log.error("Erro ao criar pedido: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pedido", description = "Atualiza um pedido existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<PedidoDTO> atualizar(
            @Parameter(description = "ID do pedido") @PathVariable Long id,
            @Valid @RequestBody PedidoDTO pedidoDTO) {
        log.info("Endpoint PUT /api/pedidos/{} chamado", id);
        try {
            PedidoDTO pedidoAtualizado = pedidoService.atualizar(id, pedidoDTO);
            return ResponseEntity.ok(pedidoAtualizado);
        } catch (RuntimeException e) {
            log.error("Erro ao atualizar pedido: {}", e.getMessage());
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pedido", description = "Remove um pedido do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        log.info("Endpoint DELETE /api/pedidos/{} chamado", id);
        try {
            pedidoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Erro ao deletar pedido: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/produtos")
    @Operation(summary = "Listar produtos de um pedido", description = "Retorna todos os produtos de um pedido específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<List<ProdutoPedidoDTO>> listarProdutosDoPedido(
            @Parameter(description = "ID do pedido") @PathVariable Long id) {
        log.info("Endpoint GET /api/pedidos/{}/produtos chamado", id);
        try {
            List<ProdutoPedidoDTO> produtos = pedidoService.listarProdutosDoPedido(id);
            return ResponseEntity.ok(produtos);
        } catch (RuntimeException e) {
            log.error("Erro ao buscar produtos do pedido: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
} 