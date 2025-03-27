# Solução de Problemas (Troubleshooting)

Este guia fornece soluções para problemas comuns que podem ocorrer durante o desenvolvimento, implantação e operação da API de Recrutamento da V8.TECH.

## Problemas de Conexão com o Banco de Dados

### Sintoma: A aplicação não inicia com erro de conexão com o banco de dados

```
org.postgresql.util.PSQLException: Connection to localhost:5432 refused.
```

**Possíveis causas e soluções:**

1. **PostgreSQL não está em execução**
   - Verifique se o serviço PostgreSQL está rodando:
     ```bash
     # Linux
     sudo systemctl status postgresql
     
     # Windows (via PowerShell como Administrador)
     Get-Service -Name postgresql*
     ```
   - Inicie o serviço se necessário:
     ```bash
     # Linux
     sudo systemctl start postgresql
     
     # Windows (via PowerShell como Administrador)
     Start-Service -Name postgresql*
     ```

2. **Credenciais incorretas**
   - Verifique se as variáveis de ambiente `DB_HOST` e `DB_PASSWORD` estão configuradas corretamente
   - Teste as credenciais diretamente no PostgreSQL:
     ```bash
     psql -h localhost -U seu_usuario -d projeto-v8
     ```

3. **Banco de dados não existe**
   - Crie o banco de dados manualmente:
     ```bash
     psql -h localhost -U postgres -c "CREATE DATABASE \"projeto-v8\";"
     ```

4. **Porta bloqueada por firewall**
   - Verifique se a porta 5432 está liberada no firewall
   - Para testes, tente desabilitar temporariamente o firewall

### Sintoma: Erro "Schema-validation: missing table"

```
Schema-validation: missing table [tabela_esperada]
```

**Solução:**
1. Verifique se o valor de `spring.jpa.hibernate.ddl-auto` está configurado corretamente:
   - Para desenvolvimento: `update` ou `create`
   - Para produção: `validate` ou `none`

2. Em ambiente de desenvolvimento, pode-se forçar a criação do esquema:
   ```properties
   spring.jpa.hibernate.ddl-auto=create
   ```
   (Atenção: isso apaga dados existentes!)

## Problemas de Service Discovery (Eureka)

### Sintoma: Aplicação inicia mas não registra no Eureka

```
com.netflix.discovery.shared.transport.TransportException: Cannot execute request on any known server
```

**Possíveis causas e soluções:**

1. **Servidor Eureka não está em execução**
   - Verifique se o servidor Eureka está rodando:
     ```bash
     curl -f http://localhost:8761/
     ```
   - Inicie o servidor Eureka se necessário

2. **Variável de ambiente `EUREKA_IP` incorreta**
   - Verifique se `EUREKA_IP` aponta para o endereço correto
   - Tente utilizar o endereço IP completo em vez de `localhost`

3. **Problemas de rede**
   - Verifique a conectividade entre o serviço e o Eureka:
     ```bash
     ping ${EUREKA_IP}
     telnet ${EUREKA_IP} 8761
     ```

## Erros de Validação

### Sintoma: Erros 400 Bad Request ao enviar dados

```json
{
  "timestamp": "14:30:00 25/03/2025",
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validação nos campos fornecidos.",
  "fieldErrors": {
    "email": "endereço de email inválido",
    "cpf": "CPF deve estar no formato XXX.XXX.XXX-XX."
  }
}
```

**Soluções:**

1. **Verifique o formato dos campos enviados**
   - Para CPF: Use o formato `XXX.XXX.XXX-XX`
   - Para email: Use um endereço válido
   - Para datas: Formato `YYYY-MM-DD`

2. **Verifique campos obrigatórios**
   - Certifique-se de que todos os campos marcados com `@NotNull`, `@NotBlank` ou `@NotEmpty` estão sendo preenchidos

3. **Valor inválido em enum**
   - Para campos enum, use exatamente os valores aceitos (ex: `"COMUM"`, `"RH"` para Role)
   - O sistema não é case-sensitive para enums (tanto "CLT" quanto "clt" são aceitos)

## Erros em Testes

### Sintoma: Falha em testes automatizados

**Possíveis causas e soluções:**

1. **Perfil incorreto**
   - Certifique-se de que o perfil `test` está sendo utilizado:
     ```bash
     mvn test -Dspring.profiles.active=test
     ```

2. **Banco de dados H2 não configurado**
   - Verifique se as dependências do H2 estão no pom.xml:
     ```xml
     <dependency>
         <groupId>com.h2database</groupId>
         <artifactId>h2</artifactId>
         <scope>test</scope>
     </dependency>
     ```
   - Confirme que `application-test.properties` está configurado corretamente

3. **Dados de teste inconsistentes**
   - Problemas comuns incluem testes que dependem da ordem de execução
   - Use `@DirtiesContext` para reiniciar o contexto entre testes

## Problemas de Performance

### Sintoma: API lenta ou com tempos de resposta inconsistentes

**Possíveis causas e soluções:**

1. **Consultas N+1**
   - Identifique a causa com o log SQL habilitado:
     ```properties
     spring.jpa.show-sql=true
     ```
   - Utilize `JOIN FETCH` nas queries ou configure `FetchType` adequadamente

2. **Falta de índices no banco de dados**
   - Adicione índices a colunas frequentemente pesquisadas:
     ```java
     @Index(name = "idx_email", columnList = "email")
     ```

3. **Cache não utilizado**
   - Ative o cache para entidades raramente alteradas:
     ```java
     @Cacheable("vagas")
     ```

4. **Conexões de banco de dados esgotadas**
   - Verifique a configuração de pool de conexões:
     ```properties
     spring.datasource.hikari.maximum-pool-size=10
     spring.datasource.hikari.minimum-idle=5
     ```

## Problemas de Deployment

### Sintoma: Erro ao iniciar o container Docker

```
Error: Unable to access jarfile /app/app.jar
```

**Soluções:**
1. Verifique se o JAR foi copiado corretamente para a imagem Docker
2. Confirme o caminho no `ENTRYPOINT` do Dockerfile
3. Verifique permissões no arquivo JAR dentro do container

### Sintoma: Variáveis de ambiente não estão sendo reconhecidas

**Soluções:**
1. **Docker Compose**: Verifique a seção `environment` no arquivo docker-compose.yml
2. **Docker run**: Certifique-se de passar todas as variáveis com a flag `-e`
3. **Kubernetes**: Verifique o ConfigMap e os Secrets

## Problemas de Logs

### Sintoma: Logs não informativos ou excessivos

**Soluções:**

1. **Ajuste o nível de log**
   - Em `application.properties`:
     ```properties
     logging.level.root=WARN
     logging.level.tech.v8.crudbackendmvp=INFO
     logging.level.org.springframework.web=INFO
     ```

2. **Configure formato mais detalhado**
   - Adicione detalhes como horário e nome da thread:
     ```properties
     logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
     ```

3. **Exporte logs para arquivo**
   ```properties
   logging.file.name=./logs/application.log
   logging.file.max-size=10MB
   logging.file.max-history=10
   ```

## Problemas de Segurança

### Sintoma: Exceção CORS ao acessar do frontend

```
Access to XMLHttpRequest has been blocked by CORS policy
```

**Soluções:**

1. Verifique a configuração CORS da aplicação:
   ```java
   registry.addMapping("/**")
           .allowedOrigins("https://seu-frontend.com")
           .allowedMethods("GET", "POST", "PUT", "DELETE");
   ```

2. Para desenvolvimento local, pode-se permitir todas as origens:
   ```java
   registry.addMapping("/**").allowedOrigins("*");
   ```

### Sintoma: Falha na integração com API de Login

**Soluções:**

1. Verifique se o serviço de login está acessível
2. Confirme que os tokens JWT estão sendo processados corretamente
3. Verifique se as roles estão configuradas adequadamente

## Suporte Adicional

Se os problemas persistirem após tentar as soluções acima, entre em contato com o time de desenvolvimento:

- **Email de suporte**: suporte@v8.tech
- **Chat interno**: Canal #api-support no Slack
- **Repositório de Issues**: [GitHub Issues](https://github.com/v8tech/api-backend-java/issues)

## Ferramentas Úteis para Diagnóstico

1. **Visualizador de Logs**: Use o Kibana ou Graylog para análise de logs
2. **Monitoring**: Utilize o Prometheus + Grafana para monitorar métricas
3. **API Testing**: Postman ou Insomnia para testar requisições
4. **Database**: DBeaver ou pgAdmin para interagir com o PostgreSQL