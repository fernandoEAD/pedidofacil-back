# PedidoFácil - Sistema de Gestão de Pedidos

Sistema completo para gestão de pedidos desenvolvido com **Spring Boot** (backend) e **Angular 12** (frontend).

## 📋 Funcionalidades

### Backend (Spring Boot + Hibernate)
- ✅ CRUD completo de Pedidos
- ✅ Gerenciamento de Produtos por Pedido
- ✅ Cálculo automático de totais
- ✅ API RESTful documentada com Swagger
- ✅ Banco H2 (desenvolvimento) e PostgreSQL (produção)
- ✅ Testes unitários com JUnit
- ✅ Validação de dados
- ✅ Relacionamentos JPA

### Frontend (Angular 12 + Material Design)
- ✅ Listagem de pedidos com expansão de produtos
- ✅ Formulários para criar/editar pedidos
- ✅ Interface responsiva com Angular Material
- ✅ Integração completa com API
- ✅ Feedback visual para usuário

## 🛠 Tecnologias Utilizadas

### Backend
- **Spring Boot 3.2.0**
- **Spring Data JPA (Hibernate)**
- **PostgreSQL** (banco principal)
- **H2 Database** (alternativa para desenvolvimento)
- **Swagger/OpenAPI 3** (documentação)
- **Maven** (gerenciamento de dependências)
- **JUnit 5** (testes)
- **Lombok** (redução de boilerplate)
- **Bean Validation** (validação)

### Frontend
- **Angular 12**
- **Angular Material** (UI components)
- **TypeScript**
- **RxJS** (programação reativa)
- **SCSS** (estilização)

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Java 17** ou superior
- **Node.js 14** ou superior
- **NPM** ou **Yarn**
- **Maven 3.6** ou superior (opcional - o projeto inclui wrapper)
- **PostgreSQL 12** ou superior

### 1. Configurando o PostgreSQL

Antes de executar o backend, configure o banco PostgreSQL:

```bash
# 1. Instalar PostgreSQL (se não tiver)
# Windows: Ver guia detalhado em backend/scripts/windows-setup.md
# Ubuntu/Debian: sudo apt install postgresql postgresql-contrib
# macOS: brew install postgresql

# 2. Iniciar o serviço PostgreSQL
# Windows: Serviço inicia automaticamente
# Ubuntu/Debian: sudo systemctl start postgresql
# macOS: brew services start postgresql

# 3. Acessar o PostgreSQL como superusuário
sudo -u postgres psql

# 4. Executar o script de criação do banco
\i backend/scripts/create-database.sql

# OU criar manualmente:
CREATE DATABASE pedidofacil;
CREATE USER pedidofacil_user WITH ENCRYPTED PASSWORD 'pedidofacil123';
GRANT ALL PRIVILEGES ON DATABASE pedidofacil TO pedidofacil_user;
```

### 2. Executando o Backend

```bash
# Navegar para a pasta do backend
cd backend

# Executar com Maven
mvn spring-boot:run

# OU usar Maven Wrapper (se disponível)
./mvnw spring-boot:run

# OU executar o JAR compilado
mvn clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

O backend estará disponível em: **http://localhost:8080**

#### Configuração do Banco de Dados

**PostgreSQL (Padrão):**
- Host: `localhost:5432`
- Database: `pedidofacil`
- Usuário: `pedidofacil_user`
- Senha: `pedidofacil123`

**H2 (Desenvolvimento - Alternativo):**
```bash
# Para usar H2 em vez de PostgreSQL, altere no application.properties:
spring.profiles.active=h2
```
- Console H2: http://localhost:8080/h2-console
- URL JDBC: `jdbc:h2:mem:pedidofacil`
- Usuário: `sa`
- Senha: `password`

### 3. Executando o Frontend

```bash
# Navegar para a pasta do frontend
cd frontend

# Instalar dependências
npm install

# Executar em modo de desenvolvimento
npm start
# OU
ng serve
```

O frontend estará disponível em: **http://localhost:4200**

## 📚 Documentação da API

### Swagger UI
Acesse a documentação interativa da API em:
**http://localhost:8080/swagger-ui.html**

### Principais Endpoints

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/pedidos` | Listar todos os pedidos |
| GET | `/api/pedidos/{id}` | Buscar pedido por ID |
| POST | `/api/pedidos` | Criar novo pedido |
| PUT | `/api/pedidos/{id}` | Atualizar pedido |
| DELETE | `/api/pedidos/{id}` | Excluir pedido |
| GET | `/api/pedidos/{id}/produtos` | Listar produtos de um pedido |

### Exemplo de Payload para Criar Pedido

```json
{
  "nomeComprador": "João Silva",
  "nomeFornecedor": "TechStore Ltda",
  "produtos": [
    {
      "nomeProduto": "Notebook Dell",
      "quantidadeComprada": 2,
      "valorTotalProduto": 3500.00
    },
    {
      "nomeProduto": "Mouse Sem Fio",
      "quantidadeComprada": 3,
      "valorTotalProduto": 150.00
    }
  ]
}
```

## 🧪 Executando Testes

### Backend (JUnit)
```bash
cd backend
./mvnw test
```

### Frontend (Karma/Jasmine)
```bash
cd frontend
npm test
```

## 📦 Dados de Exemplo

O sistema carrega automaticamente dados de exemplo na primeira execução:

- **3 pedidos** com diferentes compradores e fornecedores
- **7 produtos** distribuídos entre os pedidos
- Valores calculados automaticamente

## 🏗 Estrutura do Projeto

```
PedidoFacil/
├── backend/                     # API Spring Boot
│   ├── src/main/java/
│   │   └── com/pedidofacil/backend/
│   │       ├── entity/          # Entidades JPA
│   │       ├── repository/      # Repositories
│   │       ├── service/         # Services
│   │       ├── controller/      # REST Controllers
│   │       ├── dto/            # Data Transfer Objects
│   │       └── config/         # Configurações
│   ├── src/test/               # Testes unitários
│   ├── scripts/                # Scripts de banco
│   │   └── create-database.sql # Script PostgreSQL
│   └── pom.xml                 # Dependências Maven
└── frontend/                   # Aplicação Angular
    ├── src/app/
    │   ├── components/         # Componentes Angular
    │   ├── services/          # Services HTTP
    │   ├── models/            # Interfaces TypeScript
    │   └── shared/            # Módulos compartilhados
    └── package.json           # Dependências NPM
```

## 🔧 Configurações Avançadas

### Profiles Spring Boot

**PostgreSQL (Padrão):**
```properties
spring.profiles.active=postgresql
```

**H2 (Desenvolvimento):**
```properties
spring.profiles.active=h2
```

### Personalização das Configurações

**Para PostgreSQL personalizado (application-postgresql.properties):**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/seu_banco
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
```

### Configurações do Frontend

**Environment de Desenvolvimento:**
```typescript
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:8080'
};
```

**Environment de Produção:**
```typescript
export const environment = {
  production: true,
  apiBaseUrl: 'https://api.pedidofacil.com'
};
```

## 🛠 Troubleshooting

### PostgreSQL

**Erro de conexão:**
```bash
# Verificar se PostgreSQL está rodando
sudo systemctl status postgresql

# Verificar se o banco existe
sudo -u postgres psql -l | grep pedidofacil

# Testar conexão
psql -h localhost -U pedidofacil_user -d pedidofacil
```

**Erro de permissão:**
```sql
-- Reconectar como postgres e executar:
GRANT ALL PRIVILEGES ON DATABASE pedidofacil TO pedidofacil_user;
GRANT ALL ON SCHEMA public TO pedidofacil_user;
```

### Logs úteis
```bash
# Ver logs do Spring Boot
./mvnw spring-boot:run | grep -E "(ERROR|WARN|INFO)"

# Verificar conectividade do banco
tail -f logs/spring.log | grep -i "database\|connection"
```

## 🚀 Deploy em Produção

### Backend
```bash
# Gerar JAR
./mvnw clean package

# Executar em produção
java -jar -Dspring.profiles.active=postgresql target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
# Build para produção
ng build --prod

# Servir arquivos estáticos (dist/pedido-facil-app)
```

## 🤝 Contribuindo

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

## 👥 Autores

- **Equipe PedidoFácil** - *Desenvolvimento inicial* - [PedidoFacil](https://github.com/pedidofacil)

## 🆘 Suporte

Para suporte, envie um email para contato@pedidofacil.com ou abra uma issue no GitHub.

## 🔄 Atualizações Recentes

### PostgreSQL como Banco Principal
- ✅ **PostgreSQL** agora é o banco de dados padrão
- ✅ **H2** mantido como alternativa para desenvolvimento
- ✅ Script automatizado para criação do banco (`backend/scripts/create-database.sql`)
- ✅ Guia específico para Windows (`backend/scripts/windows-setup.md`)
- ✅ Configurações de usuário e senha pré-definidas
- ✅ Instruções de troubleshooting adicionadas

### Configuração Simplificada
```bash
# PostgreSQL (Padrão)
Database: pedidofacil
Usuário: pedidofacil_user
Senha: pedidofacil123
```

### Para Desenvolvedores
- Use PostgreSQL para um ambiente mais próximo da produção
- H2 continua disponível para testes rápidos
- Todos os dados de exemplo funcionam em ambos os bancos

---

## 🎯 **Sistema Totalmente Funcional!**

### ✅ **Status de Funcionamento:**
- **Backend Spring Boot**: ✅ Testado e funcionando na porta 8080
- **Frontend Angular**: ✅ Compilado e rodando na porta 4200
- **Banco H2**: ✅ Funcionando com dados de exemplo
- **Banco PostgreSQL**: ✅ Configurado e pronto para uso
- **APIs REST**: ✅ Todas funcionando com validação
- **Swagger**: ✅ Documentação disponível em /swagger-ui.html
- **Testes**: ✅ JUnit implementados e funcionando

### 🚀 **Links de Acesso:**
- **Aplicação Principal**: http://localhost:4200
- **API Backend**: http://localhost:8080/api/pedidos
- **Documentação Swagger**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console (se usar H2)

### 📋 **Para Usar Agora:**
1. **PostgreSQL**: Siga as instruções em `QUICK_START.md`
2. **H2 (teste rápido)**: Altere `spring.profiles.active=h2` em `application.properties`

---

**PedidoFácil** - Simplificando a gestão de pedidos! 🎯 