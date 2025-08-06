# 🧪 Teste Completo - PedidoFácil

## 📋 **Checklist Antes de Testar:**

### ✅ **PostgreSQL (DBeaver):**
- [ ] Banco `pedidofacil` criado
- [ ] Usuário `pedidofacil_user` criado  
- [ ] Permissões concedidas
- [ ] Conexão testada no DBeaver

### ✅ **Código:**
- [x] Frontend compilando (build ok)
- [x] Angular Material funcionando
- [x] Node.js 18+ resolvido
- [x] Backend com PostgreSQL configurado

## 🚀 **Executar Sistema:**

### **Terminal 1 - Backend:**
```bash
cd backend
mvn spring-boot:run

# ✅ Aguardar: "Started PedidoFacilApplication in X seconds"
# ✅ Ver: Tabelas criadas no PostgreSQL
# ✅ Ver: 3 pedidos de exemplo inseridos
```

### **Terminal 2 - Frontend:**
```bash
cd frontend  
npm start

# ✅ Aguardar: "Compiled successfully"
# ✅ Abrir: http://localhost:4200
```

## 🔍 **Testes de Funcionalidade:**

### **1. Backend Direto (APIs):**
```bash
# Listar pedidos:
curl http://localhost:8080/api/pedidos

# Swagger UI:
# http://localhost:8080/swagger-ui.html
```

### **2. Frontend Completo:**
```bash
# Abrir: http://localhost:4200

# Testar:
✅ Lista de pedidos carrega
✅ Expandir produtos de um pedido  
✅ Clicar "Novo Pedido"
✅ Preencher formulário
✅ Adicionar múltiplos produtos
✅ Salvar pedido
✅ Ver lista atualizada
✅ Editar pedido existente
✅ Excluir pedido
```

### **3. PostgreSQL (DBeaver):**
```sql
-- Verificar dados:
SELECT * FROM pedido;
SELECT * FROM produto_pedido;

-- Verificar totais calculados:
SELECT 
    p.id,
    p.nome_comprador,
    p.total_produtos_comprados,
    p.valor_total_comprado,
    COUNT(pp.id) as produtos_real,
    SUM(pp.valor_total_produto) as valor_real
FROM pedido p
LEFT JOIN produto_pedido pp ON p.id = pp.pedido_id  
GROUP BY p.id, p.nome_comprador, p.total_produtos_comprados, p.valor_total_comprado;
```

## 🚨 **Resolução de Problemas:**

### **Erro PostgreSQL "role does not exist":**
```sql
-- No DBeaver, conectado como postgres:
DROP USER IF EXISTS pedidofacil_user;
CREATE USER pedidofacil_user WITH ENCRYPTED PASSWORD 'pedidofacil123';
GRANT ALL PRIVILEGES ON DATABASE pedidofacil TO pedidofacil_user;
GRANT ALL ON SCHEMA public TO pedidofacil_user;
GRANT CREATE ON SCHEMA public TO pedidofacil_user;
```

### **Frontend não carrega/erros:**
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
npm start
```

### **CORS/API não conecta:**
```bash
# Verificar se backend está rodando:
curl http://localhost:8080/actuator/health

# Verificar se frontend está acessando API correta:
# frontend/src/environments/environment.ts
# apiBaseUrl: 'http://localhost:8080'
```

### **Portas ocupadas:**
```bash
# Verificar:
netstat -ano | findstr :8080  # Backend  
netstat -ano | findstr :4200  # Frontend

# Parar processos:
taskkill /PID [PID_NUMBER] /F
```

## ✅ **Sucesso Total:**
- ✅ Backend Spring Boot + PostgreSQL
- ✅ Frontend Angular + Material  
- ✅ APIs REST funcionando
- ✅ CRUD completo de pedidos
- ✅ Validações funcionando
- ✅ Swagger documentado
- ✅ Dados persistidos no PostgreSQL

**🎉 Sistema de produção funcionando perfeitamente!** 