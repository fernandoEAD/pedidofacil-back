# Changelog - PedidoFácil

## [1.1.0] - 2025-08-05

### 🔄 Mudanças Principais

#### PostgreSQL como Banco Principal
- **BREAKING CHANGE**: PostgreSQL agora é o banco padrão (anteriormente H2)
- H2 mantido como alternativa para desenvolvimento
- Profile padrão alterado de `h2` para `postgresql`

#### Novos Arquivos
- `backend/scripts/create-database.sql` - Script para criação automática do banco
- `backend/scripts/windows-setup.md` - Guia específico para configuração no Windows
- `backend/src/main/resources/application-h2.properties` - Configuração específica do H2

#### Configurações
- Usuário padrão PostgreSQL: `pedidofacil_user`
- Senha padrão: `pedidofacil123`
- Database: `pedidofacil`

#### Documentação
- README.md atualizado com instruções completas do PostgreSQL
- Seção de troubleshooting adicionada
- Guia de instalação específico para Windows
- Instruções de configuração personalizadas

### 🔧 Alterações Técnicas

#### Backend
```properties
# Antes
spring.profiles.active=h2

# Agora
spring.profiles.active=postgresql
```

#### Estrutura de Arquivos
```
backend/
├── scripts/
│   ├── create-database.sql      # NOVO
│   └── windows-setup.md         # NOVO
├── src/main/resources/
│   ├── application.properties   # MODIFICADO
│   ├── application-h2.properties # NOVO
│   └── application-postgresql.properties # MODIFICADO
```

### 🚀 Como Migrar

#### Para Usuários Existentes (H2)
```bash
# Continuar usando H2 - altere no application.properties:
spring.profiles.active=h2
```

#### Para Novos Usuários (PostgreSQL - Recomendado)
1. Instalar PostgreSQL
2. Executar `backend/scripts/create-database.sql`
3. Executar o projeto normalmente

### 📋 Benefícios

- **Produção-ready**: PostgreSQL é mais adequado para produção
- **Consistência**: Mesmo banco em desenvolvimento e produção
- **Performance**: Melhor performance que H2 em aplicações reais
- **Recursos**: Suporte completo a recursos SQL avançados
- **Flexibilidade**: H2 continua disponível para testes rápidos

### 🛠 Suporte

Para problemas relacionados ao PostgreSQL:
1. Consulte `backend/scripts/windows-setup.md` (Windows)
2. Verifique a seção "Troubleshooting" no README.md
3. Use logs para diagnóstico: `mvn spring-boot:run | grep -i database`

---

## [1.0.0] - 2025-08-05

### ✨ Release Inicial
- Sistema completo de gestão de pedidos
- Backend Spring Boot com H2
- Frontend Angular 12 com Material Design
- Documentação completa
- Testes unitários 