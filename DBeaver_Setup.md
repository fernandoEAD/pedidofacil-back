# 🐘 PostgreSQL Setup no DBeaver

## 📋 **Passo a Passo DBeaver:**

### 1️⃣ **Criar Banco e Usuário:**

```sql
-- 1. Conectar como postgres (usuário admin)
-- No DBeaver: Nova Conexão > PostgreSQL
-- Host: localhost, Port: 5432
-- Database: postgres
-- Username: postgres
-- Password: (sua senha do postgres)

-- 2. Executar estes comandos:
CREATE DATABASE pedidofacil;

CREATE USER pedidofacil_user WITH ENCRYPTED PASSWORD 'pedidofacil123';

GRANT ALL PRIVILEGES ON DATABASE pedidofacil TO pedidofacil_user;

-- PostgreSQL 15+ precisa de permissões extras:
GRANT ALL ON SCHEMA public TO pedidofacil_user;
GRANT CREATE ON SCHEMA public TO pedidofacil_user;

-- 3. Verificar se funcionou:
\l  -- Lista bancos
\du -- Lista usuários
```

### 2️⃣ **Nova Conexão para o App:**

```
Host: localhost
Port: 5432
Database: pedidofacil
Username: pedidofacil_user  
Password: pedidofacil123
```

### 3️⃣ **Testar Conexão:**

```sql
-- Na conexão pedidofacil_user, executar:
SELECT current_database(), current_user;

-- Deve retornar:
-- pedidofacil | pedidofacil_user
```

## 🔧 **Configuração Backend:**

1. **Verificar application-postgresql.properties:**
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/pedidofacil
   spring.datasource.username=pedidofacil_user
   spring.datasource.password=pedidofacil123
   ```

2. **Verificar application.properties:**
   ```properties
   spring.profiles.active=postgresql
   ```

## ✅ **Verificação Final:**

```sql
-- Após executar o Spring Boot, verificar se tabelas foram criadas:
\dt  -- Lista tabelas

-- Deve mostrar:
-- pedido
-- produto_pedido
```

## 🚨 **Resolução de Problemas:**

### **Erro "role does not exist":**
```sql
-- Reconectar como postgres e executar:
CREATE USER pedidofacil_user WITH ENCRYPTED PASSWORD 'pedidofacil123';
GRANT ALL PRIVILEGES ON DATABASE pedidofacil TO pedidofacil_user;
```

### **Erro de permissão schema:**
```sql
-- PostgreSQL 15+:
GRANT ALL ON SCHEMA public TO pedidofacil_user;
GRANT CREATE ON SCHEMA public TO pedidofacil_user;
```

### **Verificar se PostgreSQL está rodando:**
```bash
# Windows:
services.msc (procurar postgresql)

# Ou via comando:
pg_ctl status
``` 