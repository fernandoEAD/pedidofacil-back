package com.pedidofacil.backend.config;

import com.pedidofacil.backend.entity.Pedido;
import com.pedidofacil.backend.entity.ProdutoPedido;
import com.pedidofacil.backend.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        if (pedidoRepository.count() == 0) {
            carregarDadosIniciais();
        }
    }

    private void carregarDadosIniciais() {
        log.info("Carregando dados iniciais...");

        // Pedido 1
        Pedido pedido1 = new Pedido();
        pedido1.setNomeComprador("João Silva");
        pedido1.setNomeFornecedor("TechStore Ltda");

        ProdutoPedido produto1 = new ProdutoPedido();
        produto1.setNomeProduto("Notebook Dell Inspiron");
        produto1.setQuantidadeComprada(2);
        produto1.setValorTotalProduto(new BigDecimal("3500.00"));

        ProdutoPedido produto2 = new ProdutoPedido();
        produto2.setNomeProduto("Mouse Sem Fio");
        produto2.setQuantidadeComprada(3);
        produto2.setValorTotalProduto(new BigDecimal("150.00"));

        pedido1.addProduto(produto1);
        pedido1.addProduto(produto2);

        // Pedido 2
        Pedido pedido2 = new Pedido();
        pedido2.setNomeComprador("Maria Santos");
        pedido2.setNomeFornecedor("OfficeMax");

        ProdutoPedido produto3 = new ProdutoPedido();
        produto3.setNomeProduto("Cadeira Ergonômica");
        produto3.setQuantidadeComprada(1);
        produto3.setValorTotalProduto(new BigDecimal("800.00"));

        ProdutoPedido produto4 = new ProdutoPedido();
        produto4.setNomeProduto("Mesa de Escritório");
        produto4.setQuantidadeComprada(1);
        produto4.setValorTotalProduto(new BigDecimal("450.00"));

        ProdutoPedido produto5 = new ProdutoPedido();
        produto5.setNomeProduto("Luminária LED");
        produto5.setQuantidadeComprada(2);
        produto5.setValorTotalProduto(new BigDecimal("120.00"));

        pedido2.addProduto(produto3);
        pedido2.addProduto(produto4);
        pedido2.addProduto(produto5);

        // Pedido 3
        Pedido pedido3 = new Pedido();
        pedido3.setNomeComprador("Carlos Oliveira");
        pedido3.setNomeFornecedor("ElectroWorld");

        ProdutoPedido produto6 = new ProdutoPedido();
        produto6.setNomeProduto("Smartphone Samsung Galaxy");
        produto6.setQuantidadeComprada(1);
        produto6.setValorTotalProduto(new BigDecimal("1200.00"));

        ProdutoPedido produto7 = new ProdutoPedido();
        produto7.setNomeProduto("Carregador Portátil");
        produto7.setQuantidadeComprada(2);
        produto7.setValorTotalProduto(new BigDecimal("180.00"));

        pedido3.addProduto(produto6);
        pedido3.addProduto(produto7);

        // Salvar pedidos
        pedidoRepository.save(pedido1);
        pedidoRepository.save(pedido2);
        pedidoRepository.save(pedido3);

        log.info("Dados iniciais carregados com sucesso!");
        log.info("Total de pedidos criados: {}", pedidoRepository.count());
    }
} 