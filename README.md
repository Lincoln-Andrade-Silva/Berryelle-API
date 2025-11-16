# Berryelle API
Backend em Spring Boot para sistema de e-commerce com gestão de produtos, autenticação JWT e integração com banco PostgreSQL.
## 🛠️ Tecnologias Utilizadas
- **Java** 17
- **Spring Boot** 3.1.8
- **Spring Security** (autenticação e autorização)
- **Spring Data JPA** (persistência)
- **PostgreSQL** (banco de dados)
- **Docker** (containerização)
- **JWT** (JSON Web Token)
- **Lombok** (redução de boilerplate)
- **Swagger/OpenAPI** (documentação)

## 🏗️ Pré-requisitos
- Java 17+
- Docker e Docker Compose
- Maven

## 🚀 Como Executar
### 1. Clonar o repositório
``` bash
git clone https://github.com/seu-usuario/berryelle-api.git
cd berryelle-api
```
### 2. Iniciar o banco de dados (Docker)
``` bash
docker-compose up -d
```
O PostgreSQL será iniciado na porta 6432 e os dados serão persistidos na pasta `./data/postgres`.
### 3. Compilar e executar a aplicação
``` bash
mvn clean install
mvn spring-boot:run
```
A API estará disponível em: [http://localhost:8080](http://localhost:8080)
## 📁 Estrutura do Projeto
``` 
src/
├── main/
│   ├── java/
│   │   └── com/berryelle/
│   │       ├── config/           # Configurações (Security, Swagger)
│   │       ├── controller/       # Controllers REST
│   │       ├── core/
│   │       │   ├── domain/      # Entidades e DTOs
│   │       │   ├── mapper/      # Conversores entidade-DTO
│   │       │   ├── service/     # Lógica de negócio
│   │       │   └── validator/   # Validações
│   │       └── utils/           # Classes utilitárias
│   └── resources/
│       └── application.properties # Configurações da aplicação
```
## 🔐 Endpoints
### Autenticação
- `POST /auth/login` - Login
- `POST /auth/register` - Registro de usuário

### Produtos
- `GET /product/list` - Lista produtos (paginado)
- `POST /product/create` - Cria produto
- `PUT /product/edit/{id}` - Edita produto
- `DELETE /product/delete/{id}` - Remove produto
- `POST /product/checkout` - Processa checkout

## 🗄️ Banco de Dados
### Docker Compose
O arquivo `docker-compose.yml` configura:
- PostgreSQL na porta 6432
- Usuário e senha: postgres/postgres
- Database: berryelle001db
- Volume persistente em `./data/postgres`

### Execução
``` bash
# Iniciar
docker-compose up -d

# Parar
docker-compose down

# Logs
docker-compose logs -f
```
## 👤 Usuário Padrão
A aplicação inicia com um usuário administrador:
- **Login**: `admin`
- **Senha**: `123`

## 📝 Documentação da API
Swagger UI disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
## ⚙️ Configurações
Principais configurações em `application.properties`:
- Conexão com banco
- CORS
- JWT
- Upload de arquivos

## 🔒 Segurança
- Autenticação via JWT
- Endpoints protegidos exceto:
    - `/auth/**`
    - `/product/list`
    - Swagger UI

## 📦 Features
### Gestão de Produtos
- CRUD completo
- Upload de imagens
- Controle de estoque
- Paginação e busca

### Checkout
- Validação de estoque
- Atualização automática após compra
- Processamento em transação

### Segurança
- Autenticação JWT
- Proteção contra CSRF
- Validação de inputs

## 🤝 Integração Frontend
A API está preparada para integração com frontend React através de:
- CORS configurado
- Endpoints RESTful
- Respostas padronizadas
- Tratamento de erros consistente

## ⚠️ Observações
- O banco é criado automaticamente pelo Docker
- Dados persistem entre restarts em `./data/postgres`
- Necessário Java 17+ para compilar e executar
