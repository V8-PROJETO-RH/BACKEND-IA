# Guia de Desenvolvimento

Este guia fornece informações essenciais para desenvolvedores que desejam contribuir com o projeto CRUD API da V8.TECH.

## Requisitos de Ambiente

- JDK 21 ou superior
- Maven 3.8+
- PostgreSQL 14+
- IDE recomendada: IntelliJ IDEA ou VS Code com extensões Java

## Configuração do Ambiente de Desenvolvimento

### 1. Clone o Repositório

```bash
git clone [URL_DO_REPOSITORIO]
cd api-backend-java/crud-api
```

### 2. Configuração de Banco de Dados

#### Opção 1: Banco de Dados Local

1. Instale o PostgreSQL na sua máquina
2. Crie um banco de dados chamado `projeto-v8`
3. Configure as credenciais nas variáveis de ambiente (veja abaixo)

#### Opção 2: Container Docker

```bash
docker run --name postgres-v8 -e POSTGRES_PASSWORD=sua_senha -e POSTGRES_USER=postgres -e POSTGRES_DB=projeto-v8 -p 5432:5432 -d postgres:14-alpine
```

### 3. Variáveis de Ambiente

Configure as seguintes variáveis de ambiente:

```bash
export DB_IP=localhost
export DB=projeto-v8
export DB_HOST=postgres
export DB_PASSWORD=sua_senha
export EUREKA_IP=localhost
```

No Windows (PowerShell):
```powershell
$env:DB_IP = "localhost"
$env:DB = "projeto-v8"
$env:DB_HOST = "postgres"
$env:DB_PASSWORD = "sua_senha"
$env:EUREKA_IP = "localhost"
```

### 4. Execute a Aplicação

```bash
mvn spring-boot:run
```

Alternativamente, você pode executar diretamente pela sua IDE, configurando as variáveis de ambiente.

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── tech/v8/crudbackendmvp/
│   │       ├── controller/         # Controladores REST
│   │       ├── model/              # Entidades e DTOs
│   │       ├── repository/         # Interfaces de repositório
│   │       ├── service/            # Camada de serviço
│   │       ├── exception/          # Classes de exceção
│   │       ├── infra/              # Infraestrutura e configuração
│   │       └── CrudApiApplication.java
│   └── resources/
│       ├── application.properties  # Configuração principal
│       └── application-dev.properties # Configurações de desenvolvimento
└── test/
    ├── java/                      # Testes unitários e de integração
    └── resources/
        └── application-test.properties # Configurações para testes
```

## Fluxo de Desenvolvimento

### 1. Ciclo de Desenvolvimento

1. Crie um branch para sua feature/correção: `git checkout -b feature/nome-da-feature`
2. Implemente suas alterações seguindo as convenções do projeto
3. Escreva ou atualize os testes unitários
4. Execute os testes: `mvn test`
5. Faça commit das alterações: `git commit -m "descrição da alteração"`
6. Envie para o repositório: `git push origin feature/nome-da-feature`
7. Abra um Pull Request para revisão

### 2. Padrões de Codificação

- Use [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)
- Mantenha métodos pequenos e com responsabilidade única
- Documente APIs públicas com Javadoc
- Utilize nomes significativos para classes, métodos e variáveis
- Mantenha a cobertura de testes acima de 80%

### 3. Testes

#### Testes Unitários

- Use JUnit 5 para testes unitários
- Mockito para simulação de dependências
- Cada método público deve ter pelo menos um teste

#### Testes de Integração

- Use TestRestTemplate ou MockMvc para testar endpoints
- Configure o perfil `test` para usar H2 em vez de PostgreSQL

Exemplo:
```java
@SpringBootTest(properties = {"spring.profiles.active=test"})
public class ExemploIntegracaoTest {
    // Testes de integração
}
```

## Arquitetura e Design Patterns

### Camadas da Aplicação

1. **Controller**: Responsável pela API REST, validação de entrada e conversão de DTOs
2. **Service**: Lógica de negócios e regras de aplicação
3. **Repository**: Acesso a dados e consultas ao banco
4. **Model**: Entidades JPA e DTOs para transferência de dados

### Design Patterns Utilizados

- **DTO (Data Transfer Object)**: Para transferência de dados entre camadas
- **Repository Pattern**: Para abstração da camada de dados
- **Service Layer**: Para encapsular lógica de negócios
- **Dependency Injection**: Via Spring Framework

### Validação de Dados

- Use anotações Bean Validation (Jakarta Validation) nos DTOs
- Implemente validações adicionais na camada de serviço quando necessário
- Use exceções personalizadas para erros específicos de domínio

## Resolvendo Problemas Comuns

### Problemas de Conexão com Banco de Dados

- Verifique se o PostgreSQL está em execução
- Confirme as credenciais nas variáveis de ambiente
- Teste a conexão diretamente via `psql` ou outra ferramenta

### Erros de Compilação

- Verifique a versão do JDK (deve ser 21+)
- Execute `mvn clean` para limpar artefatos antigos
- Atualize as dependências: `mvn versions:display-dependency-updates`

### Testes Falhando

- Execute testes específicos: `mvn test -Dtest=NomeDoTeste`
- Verifique se o perfil de teste está ativado
- Confirme que o H2 está configurado corretamente para testes

## Recursos Adicionais

- [Documentação do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Documentação do Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
- [Tutorial do Hibernate](https://hibernate.org/orm/documentation/)