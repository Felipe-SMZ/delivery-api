# 🍕 Delivery API - Sistema de Delivery com Spring Boot

Projeto de aprendizado de **Java**, **Spring Boot**, **Docker**, **MySQL** e **Redis**.

## 🚀 Tecnologias

- **Java 17+**
- **Spring Boot 4.0.3**
- **MySQL 8.0** (Docker)
- **Redis 7** (Docker)
- **JPA/Hibernate**
- **Lombok**
- **Maven**
- **Docker & Docker Compose**

## 📋 Funcionalidades

- ✅ CRUD de Restaurantes
- ✅ CRUD de Produtos (Cardápio)
- ✅ CRUD de Clientes
- ✅ Sistema de Pedidos
- ✅ Controle de Status de Pedidos
- ✅ Cache com Redis
- 🔜 API REST (em desenvolvimento)

## 🎯 Conceitos Aplicados

### POO (Programação Orientada a Objetos):
- Encapsulamento
- Composição (HAS-A)
- Polimorfismo
- Abstração (Enums)

### Spring Boot:
- Injeção de Dependência
- JPA/Hibernate
- Relacionamentos (@OneToMany, @ManyToOne)
- Validações
- Cache

## 🗄️ Modelo de Dados

```
Restaurante (1) ──────< (N) Produto
Cliente (1) ──────< (N) Pedido
Pedido (1) ──────< (N) ItemPedido
ItemPedido (N) ──────> (1) Produto
```

### Entidades:
- **Restaurante**: Dados do estabelecimento
- **Produto**: Itens do cardápio
- **Cliente**: Usuários do sistema
- **Pedido**: Pedidos realizados
- **ItemPedido**: Produtos dentro de um pedido

## 🐳 Como Rodar com Docker

### 1. Subir MySQL e Redis:
```bash
cd delivery-project
docker-compose up -d
```

### 2. Verificar se os containers estão rodando:
```bash
docker-compose ps
```

### 3. Rodar a aplicação Spring Boot:
```bash
cd delivery-api
./mvnw spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## ⚙️ Configuração

### Portas:
- **MySQL**: `localhost:3307`
- **Redis**: `localhost:6379`
- **API**: `localhost:8080`

### Credenciais MySQL:
- **Database**: `delivery_db`
- **User**: `root`
- **Password**: `root123`

## 📁 Estrutura do Projeto

```
delivery-project/
├── docker-compose.yml          # Configuração MySQL + Redis
└── delivery-api/               # Aplicação Spring Boot
    ├── src/
    │   └── main/
    │       ├── java/com/delivery/
    │       │   ├── model/          # Entidades JPA
    │       │   ├── repository/     # Acesso a dados
    │       │   ├── service/        # Lógica de negócio
    │       │   ├── controller/     # API REST
    │       │   └── dto/            # Data Transfer Objects
    │       └── resources/
    │           └── application.properties
    └── pom.xml
```

## 🛠️ Comandos Úteis

### Docker:
```bash
# Subir containers
docker-compose up -d

# Ver logs
docker-compose logs -f

# Parar containers
docker-compose down

# Parar e remover volumes (CUIDADO: apaga dados!)
docker-compose down -v
```

### Maven:
```bash
# Compilar
./mvnw clean install

# Rodar aplicação
./mvnw spring-boot:run

# Rodar testes
./mvnw test
```

### MySQL (via Docker):
```bash
# Entrar no MySQL
docker exec -it delivery-mysql mysql -uroot -p

# Ver tabelas
SHOW TABLES;

# Ver estrutura de uma tabela
DESCRIBE restaurantes;
```

### Redis (via Docker):
```bash
# Entrar no Redis CLI
docker exec -it delivery-redis redis-cli

# Ver todas as chaves
KEYS *

# Ver um valor
GET chave
```

## 📚 Aprendizado

Este projeto foi desenvolvido como parte de um estudo de:
- Docker e containerização
- Java e Programação Orientada a Objetos
- Spring Boot e ecossistema Spring
- Banco de dados relacionais (MySQL)
- Cache com Redis
- API REST
- Boas práticas de desenvolvimento

## 🔜 Próximos Passos

- [ ] Implementar Controllers (API REST)
- [ ] Adicionar cache Redis
- [ ] Criar DTOs
- [ ] Implementar validações
- [ ] Adicionar testes unitários
- [ ] Dockerizar a aplicação Java
- [ ] Implementar autenticação JWT
- [ ] Deploy

## 📝 Licença

Projeto de estudo - livre para uso educacional.

---

Desenvolvido com ☕ e 🐋