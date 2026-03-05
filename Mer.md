# 📊 MER e DER - Sistema de Delivery

## 🎯 Modelo Entidade-Relacionamento (MER)

### Entidades e Atributos:

#### **RESTAURANTE**
- id (PK)
- nome
- descricao
- endereco
- telefone
- data_criacao
- ativo

#### **PRODUTO**
- id (PK)
- nome
- descricao
- preco
- disponivel
- data_criacao
- restaurante_id (FK)

#### **CLIENTE**
- id (PK)
- nome
- email (UNIQUE)
- telefone
- endereco
- cpf
- data_criacao

#### **PEDIDO**
- id (PK)
- data_pedido
- endereco_entrega
- status (ENUM)
- taxa_entrega
- total
- cliente_id (FK)

#### **ITEM_PEDIDO**
- id (PK)
- quantidade
- preco_unitario
- subtotal
- pedido_id (FK)
- produto_id (FK)

---

### Relacionamentos:

1. **RESTAURANTE** (1) ───< (N) **PRODUTO**
   - Um restaurante TEM muitos produtos
   - Um produto PERTENCE a um restaurante

2. **CLIENTE** (1) ───< (N) **PEDIDO**
   - Um cliente FAZ muitos pedidos
   - Um pedido É FEITO POR um cliente

3. **PEDIDO** (1) ───< (N) **ITEM_PEDIDO**
   - Um pedido CONTÉM muitos itens
   - Um item PERTENCE a um pedido

4. **PRODUTO** (1) ───< (N) **ITEM_PEDIDO**
   - Um produto PODE ESTAR EM muitos itens
   - Um item REFERENCIA um produto

---

## 📐 Cardinalidades:

```
RESTAURANTE ──1:N── PRODUTO
CLIENTE ──1:N── PEDIDO
PEDIDO ──1:N── ITEM_PEDIDO
PRODUTO ──1:N── ITEM_PEDIDO
```

---

## 🗂️ Diagrama ER (Notação Crow's Foot)

```
┌─────────────────────────┐
│      RESTAURANTE        │
├─────────────────────────┤
│ PK  id                  │
│     nome                │
│     descricao           │
│     endereco            │
│     telefone            │
│     data_criacao        │
│     ativo               │
└────────────┬────────────┘
             │
             │ 1
             │
             │ N
             ↓
┌─────────────────────────┐
│       PRODUTO           │
├─────────────────────────┤
│ PK  id                  │
│ FK  restaurante_id      │────┐
│     nome                │    │
│     descricao           │    │ Relacionamento:
│     preco               │    │ restaurante_id → restaurante.id
│     disponivel          │    │
│     data_criacao        │────┘
└────────────┬────────────┘
             │
             │ 1
             │
             │ N
             ↓
      ┌─────────────┐
      │ ITEM_PEDIDO │
      └─────────────┘


┌─────────────────────────┐
│       CLIENTE           │
├─────────────────────────┤
│ PK  id                  │
│     nome                │
│     email (UNIQUE)      │
│     telefone            │
│     endereco            │
│     cpf                 │
│     data_criacao        │
└────────────┬────────────┘
             │
             │ 1
             │
             │ N
             ↓
┌─────────────────────────┐
│        PEDIDO           │
├─────────────────────────┤
│ PK  id                  │
│ FK  cliente_id          │────┐
│     data_pedido         │    │
│     endereco_entrega    │    │ Relacionamento:
│     status              │    │ cliente_id → cliente.id
│     taxa_entrega        │    │
│     total               │────┘
└────────────┬────────────┘
             │
             │ 1
             │
             │ N
             ↓
┌─────────────────────────┐
│     ITEM_PEDIDO         │
├─────────────────────────┤
│ PK  id                  │
│ FK  pedido_id           │────┐ Relacionamento:
│ FK  produto_id          │────┤ pedido_id → pedido.id
│     quantidade          │    │ produto_id → produto.id
│     preco_unitario      │    │
│     subtotal            │────┘
└─────────────────────────┘
```

---

## 🔗 Integridade Referencial

### Chaves Estrangeiras:

1. **produto.restaurante_id** → **restaurante.id**
   - ON DELETE: CASCADE (se deletar restaurante, deleta produtos)

2. **pedido.cliente_id** → **cliente.id**
   - ON DELETE: RESTRICT (não pode deletar cliente com pedidos)

3. **item_pedido.pedido_id** → **pedido.id**
   - ON DELETE: CASCADE (se deletar pedido, deleta itens)

4. **item_pedido.produto_id** → **produto.id**
   - ON DELETE: RESTRICT (não pode deletar produto que está em pedidos)

---

## 📋 Dicionário de Dados

### Tabela: RESTAURANTE
| Campo        | Tipo         | Nulo | Chave | Default | Descrição                    |
|--------------|--------------|------|-------|---------|------------------------------|
| id           | BIGINT       | NÃO  | PK    | AUTO    | Identificador único          |
| nome         | VARCHAR(100) | NÃO  | -     | -       | Nome do restaurante          |
| descricao    | VARCHAR(500) | SIM  | -     | -       | Descrição do estabelecimento |
| endereco     | VARCHAR(200) | NÃO  | -     | -       | Endereço completo            |
| telefone     | VARCHAR(20)  | SIM  | -     | -       | Telefone de contato          |
| data_criacao | DATETIME     | NÃO  | -     | NOW()   | Data de cadastro             |
| ativo        | BOOLEAN      | SIM  | -     | TRUE    | Status do restaurante        |

### Tabela: PRODUTO
| Campo          | Tipo          | Nulo | Chave | Default | Descrição                    |
|----------------|---------------|------|-------|---------|------------------------------|
| id             | BIGINT        | NÃO  | PK    | AUTO    | Identificador único          |
| restaurante_id | BIGINT        | NÃO  | FK    | -       | ID do restaurante            |
| nome           | VARCHAR(100)  | NÃO  | -     | -       | Nome do produto              |
| descricao      | VARCHAR(500)  | SIM  | -     | -       | Descrição do produto         |
| preco          | DECIMAL(10,2) | NÃO  | -     | -       | Preço unitário               |
| disponivel     | BOOLEAN       | SIM  | -     | TRUE    | Disponibilidade              |
| data_criacao   | DATETIME      | NÃO  | -     | NOW()   | Data de cadastro             |

### Tabela: CLIENTE
| Campo        | Tipo         | Nulo | Chave  | Default | Descrição            |
|--------------|--------------|------|--------|---------|----------------------|
| id           | BIGINT       | NÃO  | PK     | AUTO    | Identificador único  |
| nome         | VARCHAR(100) | NÃO  | -      | -       | Nome completo        |
| email        | VARCHAR(100) | NÃO  | UNIQUE | -       | Email único          |
| telefone     | VARCHAR(20)  | SIM  | -      | -       | Telefone de contato  |
| endereco     | VARCHAR(200) | NÃO  | -      | -       | Endereço de entrega  |
| cpf          | VARCHAR(11)  | SIM  | -      | -       | CPF do cliente       |
| data_criacao | DATETIME     | NÃO  | -      | NOW()   | Data de cadastro     |

### Tabela: PEDIDO
| Campo            | Tipo          | Nulo | Chave | Default   | Descrição                        |
|------------------|---------------|------|-------|-----------|----------------------------------|
| id               | BIGINT        | NÃO  | PK    | AUTO      | Identificador único              |
| cliente_id       | BIGINT        | NÃO  | FK    | -         | ID do cliente                    |
| data_pedido      | DATETIME      | NÃO  | -     | NOW()     | Data/hora do pedido              |
| endereco_entrega | VARCHAR(200)  | NÃO  | -     | -         | Endereço de entrega              |
| status           | VARCHAR(20)   | NÃO  | -     | PENDENTE  | Status do pedido                 |
| taxa_entrega     | DECIMAL(10,2) | SIM  | -     | 0.00      | Taxa de entrega                  |
| total            | DECIMAL(10,2) | SIM  | -     | -         | Valor total do pedido            |

### Tabela: ITEM_PEDIDO
| Campo          | Tipo          | Nulo | Chave | Default | Descrição                    |
|----------------|---------------|------|-------|---------|------------------------------|
| id             | BIGINT        | NÃO  | PK    | AUTO    | Identificador único          |
| pedido_id      | BIGINT        | NÃO  | FK    | -       | ID do pedido                 |
| produto_id     | BIGINT        | NÃO  | FK    | -       | ID do produto                |
| quantidade     | INT           | NÃO  | -     | -       | Quantidade do item           |
| preco_unitario | DECIMAL(10,2) | NÃO  | -     | -       | Preço no momento da compra   |
| subtotal       | DECIMAL(10,2) | SIM  | -     | -       | Quantidade × Preço unitário  |

---

## 🎯 Status do Pedido (ENUM)

```
PENDENTE           → Pedido criado, aguardando confirmação
CONFIRMADO         → Restaurante confirmou o pedido
EM_PREPARO         → Pedido sendo preparado
SAIU_PARA_ENTREGA  → Pedido saiu para entrega
ENTREGUE           → Pedido foi entregue
CANCELADO          → Pedido foi cancelado
```

---

## 🔄 Fluxo de Dados

### Criação de Pedido:
```
1. Cliente cria pedido
2. Sistema cria registro em PEDIDO (status: PENDENTE)
3. Cliente adiciona produtos
4. Sistema cria registros em ITEM_PEDIDO
5. Sistema calcula TOTAL (soma itens + taxa entrega)
6. Pedido fica aguardando confirmação
```

### Atualização de Status:
```
PENDENTE → CONFIRMADO → EM_PREPARO → SAIU_PARA_ENTREGA → ENTREGUE
    ↓
CANCELADO (só pode cancelar se PENDENTE ou CONFIRMADO)
```

---

## 📊 Consultas Importantes

### 1. Produtos de um restaurante:
```sql
SELECT * FROM produto 
WHERE restaurante_id = ? AND disponivel = TRUE;
```

### 2. Pedidos de um cliente:
```sql
SELECT * FROM pedido 
WHERE cliente_id = ? 
ORDER BY data_pedido DESC;
```

### 3. Itens de um pedido (com detalhes do produto):
```sql
SELECT ip.*, p.nome, p.descricao 
FROM item_pedido ip
JOIN produto p ON ip.produto_id = p.id
WHERE ip.pedido_id = ?;
```

### 4. Total de vendas de um restaurante:
```sql
SELECT r.nome, SUM(p.total) as total_vendas
FROM restaurante r
JOIN produto pr ON pr.restaurante_id = r.id
JOIN item_pedido ip ON ip.produto_id = pr.id
JOIN pedido p ON p.id = ip.pedido_id
WHERE p.status = 'ENTREGUE'
GROUP BY r.id;
```

---

Esse é o modelo completo do banco de dados! 🎉