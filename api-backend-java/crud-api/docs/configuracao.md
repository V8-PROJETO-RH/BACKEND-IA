# Configuração da Aplicação

Este documento detalha as configurações disponíveis para a API de Recrutamento da V8.TECH, incluindo ambientes, variáveis, perfis e opções de deployment.

## Variáveis de Ambiente

A aplicação utiliza as seguintes variáveis de ambiente que devem ser configuradas:

| Variável      | Descrição                          | Valor Padrão   | Obrigatória |
|---------------|-----------------------------------|----------------|------------|
| `EUREKA_IP`   | Endereço do servidor Eureka       | localhost      | Sim        |
| `DB_IP`       | Endereço do servidor PostgreSQL   | localhost      | Sim        |
| `DB`          | Nome do banco de dados            | projeto-v8     | Sim        |
| `DB_HOST`     | Usuário do banco de dados         | postgres       | Sim        |
| `DB_PASSWORD` | Senha do banco de dados           | -              | Sim        |
| `SERVER_PORT` | Porta onde a aplicação roda       | 8082           | Não        |

## Perfis de Execução (Spring Profiles)

### Perfil `dev` (Desenvolvimento)

Este é o perfil padrão, utilizado para desenvolvimento local.

**Arquivo:** `application-dev.properties`

Principais configurações:
```properties
spring.datasource.url=jdbc:postgresql://${DB_IP}:5432/${DB}
spring.datasource.username=${DB_HOST}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Perfil `test` (Testes)

Perfil utilizado para execução de testes automatizados.

**Arquivo:** `application-test.properties`

Principais configurações:
```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

### Perfil `prod` (Produção)

Perfil utilizado em ambiente de produção.

**Arquivo:** `application-prod.properties` (precisa ser criado se necessário)

Configurações recomendadas:
```properties
spring.datasource.url=jdbc:postgresql://${DB_IP}:5432/${DB}
spring.datasource.username=${DB_HOST}
spring.datasource.password=${DB_PASSWORD}
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=false
```

## Configuração de CORS

A aplicação está configurada para permitir solicitações CORS de qualquer origem. Em produção, é recomendável restringir para origens específicas.

```java
@Configuration
public class CorsConfiguration {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
            }
        };
    }
}
```

## Configuração do Swagger / OpenAPI

A documentação da API é gerada automaticamente usando Swagger/OpenAPI.

```java
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Recrutamento V8.TECH")
                        .version("1.0")
                        .description("API para gestão de vagas, candidatos e processos seletivos")
                        .contact(new Contact()
                                .name("Equipe V8.TECH")
                                .email("desenvolvimento@v8.tech")));
    }
}
```

## Logs

A aplicação utiliza Logback (padrão do Spring Boot) para registro de logs.

Níveis de log recomendados por ambiente:

- **Desenvolvimento**: `DEBUG`
- **Teste**: `INFO`
- **Produção**: `WARN` ou `ERROR`

## Opções de Deployment

### 1. JAR Standalone

A forma mais simples de executar a aplicação é como um JAR autocontido:

```bash
java -jar -Dspring.profiles.active=prod crud-api-0.0.1-SNAPSHOT.jar
```

### 2. Docker

A aplicação está preparada para ser executada via Docker:

```dockerfile
FROM openjdk:21-slim
WORKDIR /app
COPY target/crud-api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
```

Para construir e executar:

```bash
docker build -t v8tech/crud-api .
docker run -p 8082:8082 --name crud-api \
  -e EUREKA_IP=eureka-server \
  -e DB_IP=postgres \
  -e DB=projeto-v8 \
  -e DB_HOST=postgres \
  -e DB_PASSWORD=sua_senha \
  v8tech/crud-api
```

### 3. Docker Compose

Para orquestrar todos os serviços necessários:

```yaml
version: '3.8'
services:
  postgres:
    image: postgres:14-alpine
    environment:
      - POSTGRES_PASSWORD=sua_senha
      - POSTGRES_USER=postgres
      - POSTGRES_DB=projeto-v8
    volumes:
      - postgres_data:/var/lib/postgresql/data
    ports:
      - "5432:5432"
  
  eureka-server:
    image: server-gateway-img
    ports:
      - "8761:8761"
  
  crud-api:
    image: crud-api-img
    environment:
      - EUREKA_IP=eureka-server
      - DB_IP=postgres
      - DB=projeto-v8
      - DB_HOST=postgres
      - DB_PASSWORD=sua_senha
    depends_on:
      - postgres
      - eureka-server
    ports:
      - "8082:8082"

volumes:
  postgres_data:
```

## Monitoramento

### Actuator

O Spring Boot Actuator está configurado para fornecer endpoints de monitoramento:

- `/actuator/health`: status da aplicação
- `/actuator/info`: informações da aplicação
- `/actuator/metrics`: métricas diversas

### Prometheus e Grafana (Opcional)

Para monitoramento avançado, é recomendável configurar:

1. **Prometheus** para coleta de métricas
2. **Grafana** para visualização

## Backup do Banco de Dados

Recomenda-se configurar backups automatizados do PostgreSQL:

```bash
# Exemplo de script de backup
pg_dump -h ${DB_IP} -U ${DB_HOST} -d ${DB} -F c -b -v -f "/backups/projeto-v8-$(date +%Y%m%d_%H%M%S).backup"
```

## Configurações de Segurança (Nível de API)

- Validação de dados nos DTOs
- Tratamento centralizado de exceções
- Sanitização de entradas
- Proteção contra SQL injection através do uso de JPA/Hibernate

**Nota:** A autenticação e autorização são gerenciadas pelo API Gateway e pelo serviço de Login, não pela API CRUD diretamente.