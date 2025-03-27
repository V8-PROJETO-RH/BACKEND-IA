# V8.TECH CRUD API

API de serviços CRUD para gerenciamento de vagas, candidatos e funcionários do sistema de recrutamento e seleção da V8.TECH.

## Visão Geral

Esta API fornece endpoints para criar, ler, atualizar e excluir registros relacionados ao processo de recrutamento. O sistema gerencia:

- Vagas de emprego
- Candidatos
- Funcionários
- Provas e Avaliações
- Aplicações para vagas
- Resultados finais

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.1
- Spring Data JPA
- Spring Cloud Netflix Eureka Client
- PostgreSQL
- Lombok
- Validation
- Springdoc OpenAPI (Swagger UI)
- JUnit & Mockito (testes)
- H2 Database (ambiente de teste)

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

```
src/
├── main/
│   ├── java/tech/v8/crudbackendmvp/
│   │   ├── controller/     # Controladores REST
│   │   ├── exception/      # Exceções personalizadas e handlers
│   │   ├── infra/          # Configurações e infraestrutura
│   │   ├── model/          # Entidades, DTOs e mapeadores
│   │   │   ├── dto/        # Data Transfer Objects
│   │   │   ├── enums/      # Enumerações
│   │   │   ├── usuario/    # Modelos de usuário (Pessoa, Candidato, etc.)
│   │   │   └── vaga/       # Modelos de vagas e processos
│   │   ├── repository/     # Repositórios JPA
│   │   └── service/        # Serviços de negócio
│   └── resources/          # Configurações (properties)
└── test/                   # Testes automatizados
```

## Configuração e Execução

### Pré-requisitos

- Java 21
- Maven
- PostgreSQL
- Serviço Eureka em execução

### Variáveis de Ambiente

A aplicação requer as seguintes variáveis de ambiente:

- `EUREKA_IP` - Endereço IP do servidor Eureka
- `DB_IP` - Endereço IP do PostgreSQL
- `DB` - Nome do banco de dados
- `DB_HOST` - Usuário do banco de dados
- `DB_PASSWORD` - Senha do banco de dados

### Compilação

```bash
mvn clean package
```

### Execução

```bash
java -jar target/crud-api-0.0.1-SNAPSHOT.jar
```

Ou com variáveis de ambiente específicas:

```bash
EUREKA_IP=localhost DB_IP=localhost DB=projeto-v8 DB_HOST=postgres DB_PASSWORD=senha java -jar target/crud-api-0.0.1-SNAPSHOT.jar
```

### Docker

O projeto inclui um arquivo docker-compose.yaml na raiz do repositório para facilitar a execução:

```bash
docker-compose up -d
```

## API Documentation

Documentação Swagger UI está disponível em:

- Local: http://localhost:8082/swagger-ui.html
- Cloud: http://34.172.20.219:8082/swagger-ui.html - Provisório, será alterado.

## Perfis

A aplicação possui os seguintes perfis:

- `dev` (padrão) - Para desenvolvimento local
- `test` - Para execução dos testes automatizados, usa H2 em memória

## Testes

Execute os testes com:

```bash
mvn test
```

Para executar com o perfil de teste específico:

```bash
mvn test -Dspring.profiles.active=test
```

## Mais Informações

Consulte a documentação adicional na pasta `docs/` para informações detalhadas:

- [API Endpoints](/api-backend-java/crud-api/docs/api-endpoints.md)
- [Arquitetura](/api-backend-java/crud-api/docs/arquitetura.md)
- [Desenvolvimento](/api-backend-java/crud-api/docs/desenvolvimento.md)
- [Modelos de Domínio](/api-backend-java/crud-api/docs/modelos-dominio.md)