# 🚀 Guia Rápido - PedidoFácil

## ⚡ Início Rápido (3 Passos)

### 1️⃣ **PostgreSQL** (Uma vez só)
```bash
# Instalar PostgreSQL (Windows: site oficial, Linux: apt install postgresql)
# Criar banco:
sudo -u postgres psql
CREATE DATABASE pedidofacil;
CREATE USER pedidofacil_user WITH ENCRYPTED PASSWORD 'pedidofacil123';
GRANT ALL PRIVILEGES ON DATABASE pedidofacil TO pedidofacil_user;
\q
```

### 2️⃣ **Backend Spring Boot**
```bash
# Terminal 1 - Backend
cd backend
mvn spring-boot:run
#se n tiver o mvn
./mvnw spring-boot:run

# ✅ Backend rodando: http://localhost:8080
# ✅ Swagger: http://localhost:8080/swagger-ui.html
```

### 3️⃣ **Frontend Angular**
```bash
# Terminal 2 - Frontend (NOVA ABA/JANELA)
cd frontend
npm install -g @angular/cli@12
npm install
ng s
#ou
npm start

# ✅ Frontend rodando: http://localhost:4200
```

## 🔥 **URLs Importantes:**
- **App Principal**: http://localhost:4200
- **API Backend**: http://localhost:8080
- **Swagger Docs**: http://localhost:8080/swagger-ui.html
- **H2 Console**: http://localhost:8080/h2-console (só se usar H2)

## 🛠 **Resolução de Problemas:**

### **Erro "ng command not found"**
```bash
cd frontend
npm install -g @angular/cli@12
```

### **Erro Node.js/Angular**
```bash
# Já está configurado no package.json, mas se precisar manualmente:
NODE_OPTIONS="--openssl-legacy-provider" ng serve
```

### **Erro PostgreSQL**
```bash
# Verificar se está rodando:
sudo systemctl status postgresql  # Linux
services.msc                      # Windows (procurar postgresql)

# Testar conexão:
psql -h localhost -U pedidofacil_user -d pedidofacil
```

### **Usar H2 em vez de PostgreSQL**
```bash
# Editar backend/src/main/resources/application.properties:
spring.profiles.active=h2
```

## 📁 **Estrutura de Comandos:**

```bash
PedidoFacil/
├── backend/          # cd backend && mvn spring-boot:run
├── frontend/         # cd frontend && npm start
├── README.md         # Documentação completa
└── QUICK_START.md    # Este arquivo
```

## 🔄 **Comandos Úteis:**

```bash
# Parar processos (Ctrl+C nos terminais)

# Verificar se portas estão ocupadas:
netstat -ano | findstr :8080  # Backend
netstat -ano | findstr :4200  # Frontend

# Reinstalar dependências frontend:
cd frontend && rm -rf node_modules && npm install

# Compilar backend:
cd backend && mvn clean compile

# Executar testes:
cd backend && mvn test
cd frontend && npm test
```

## ✅ **Teste Rápido:**

1. Acesse http://localhost:4200
2. Clique em "Novo Pedido"
3. Preencha os dados e adicione produtos
4. Salve o pedido
5. Veja a listagem atualizada

**Pronto! Sistema funcionando! 🎉** 