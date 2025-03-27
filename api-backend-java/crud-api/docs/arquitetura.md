# Arquitetura do Sistema

## Visão Geral

O sistema de recrutamento da V8.TECH é uma aplicação baseada em microsserviços que segue uma arquitetura em camadas. Esta API representa o componente CRUD principal que gerencia as entidades de negócio relacionadas ao processo de recrutamento e seleção.

## Diagrama de Arquitetura

```
┌────────────────┐     ┌────────────────┐     ┌────────────────┐
│    Frontend    │     │    Gateway     │     │  Service       │
│    (React)     │────>│   (Spring      │────>│  Discovery     │
│                │     │   Cloud)       │     │  (Eureka)      │
└────────────────┘     └────────────────┘     └────────────────┘
                             │                        │
                             │                        │
                             ▼                        ▼
                       ┌────────────────┐     ┌────────────────┐
                       │  CRUD API      │     │  Login API     │
                       │  (Spring Boot) │<───>│  (Spring Boot) │
                       └────────────────┘     └────────────────┘
                              │
                              │
                              ▼
                       ┌────────────────┐
                       │  PostgreSQL    │
                       │  Database      │
                       └────────────────┘
```

## Componentes Principais

### 1. Spring Boot Application

A aplicação principal é baseada em Spring Boot, que fornece a infraestrutura básica:
- Gerenciamento de dependências
- Configuração automática
- Servidor web embutido
- Ferramentas de desenvolvimento

### 2. Camadas da Aplicação

A API segue uma arquitetura em camadas:

```
┌────────────────────────────────────┐
│           Controllers              │  ← Endpoints REST, validação de entrada
└───────────────┬────────────────────┘
                │
                ▼
┌────────────────────────────────────┐
│            Services                │  ← Lógica de negócios
└───────────────┬────────────────────┘
                │
                ▼
┌────────────────────────────────────┐
│          Repositories              │  ← Acesso a dados
└───────────────┬────────────────────┘
                │
                ▼
┌────────────────────────────────────┐
│           Database                 │  ← Persistência
└────────────────────────────────────┘
```

### 3. Gestão de Microsserviços

- **Service Discovery**: Registro e descoberta de serviços utilizando Netflix Eureka
- **Api Gateway**: Roteamento, balanceamento de carga e segurança (implementado separadamente)

## Princípios de Design

1. **Separação de Responsabilidades**: Cada componente tem uma função específica
2. **Design Orientado a Domínio**: Modelagem baseada no domínio de negócio
3. **SOLID**: Princípios de design orientado a objetos
4. **RESTful**: API segue os princípios REST para comunicação
5. **Validação**: Validação em múltiplas camadas (DTO, entidade, banco de dados)

## Fluxo de Dados

1. As requisições HTTP chegam nos controllers
2. Os controllers validam os dados de entrada e chamam os serviços apropriados
3. Os serviços implementam a lógica de negócios e utilizam repositories para acesso a dados
4. Os repositories interagem com o banco de dados PostgreSQL
5. O resultado é convertido em DTOs e retornado como resposta HTTP

## Convenções de Pacotes

```
tech.v8.crudbackendmvp
├── controller          # Endpoints REST
│   └── usuario         # Controllers agrupados por domínio
├── model               # Entidades e DTOs
│   ├── dto             # Data Transfer Objects
│   ├── enums           # Enumerações
│   ├── usuario         # Entidades agrupadas por domínio
│   └── vaga
├── repository          # Interfaces JPA
├── service             # Serviços de negócio
├── exception           # Classes de exceção
└── infra               # Configurações de infraestrutura
```

## Integração com Outros Serviços

Esta API se integra com outros componentes do ecossistema:

1. **Servidor Eureka**: Registro de serviço para descoberta
2. **API Gateway**: Roteamento e segurança (implementado separadamente)
3. **Login API**: Autenticação e autorização (implementado separadamente)

## Escalabilidade

O design modular da aplicação permite escalabilidade horizontal:
- Múltiplas instâncias podem ser executadas atrás de um balanceador de carga
- Conexões de banco de dados são gerenciadas através de pool de conexões
- O servidor Eureka facilita a descoberta e balanceamento entre instâncias