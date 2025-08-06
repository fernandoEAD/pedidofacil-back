package com.pedidofacil.backend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("PedidoFácil API")
                        .description("Sistema de Gestão de Pedidos - API REST para gerenciar pedidos e produtos")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Equipe PedidoFácil")
                                .email("contato@pedidofacil.com")
                                .url("https://www.pedidofacil.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de Desenvolvimento"),
                        new Server()
                                .url("https://api.pedidofacil.com")
                                .description("Servidor de Produção")
                ));
    }
} 