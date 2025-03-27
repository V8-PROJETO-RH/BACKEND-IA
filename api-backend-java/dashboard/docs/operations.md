# Guia de Operações

Este guia fornece instruções para implantação, operação e monitoramento da Dashboard API em ambiente de produção.

## Requisitos de Infraestrutura

### Hardware Recomendado
- **CPU**: 2 cores (mínimo), 4 cores (recomendado)
- **Memória RAM**: 2GB (mínimo), 4GB (recomendado)
- **Armazenamento**: 1GB para a aplicação e logs

### Software Necessário
- Java 21 ou superior
- Servidor de aplicação (opcional, pode usar o Tomcat embutido)
- Docker (se utilizando contêineres)
- Servidor de log centralizado (recomendado: ELK Stack ou Graylog)

### Dependências Externas
- Serviço CRUD (requisito obrigatório)
- Eureka Server (opcional, mas recomendado para ambientes de produção)

## Implantação

### Implantação Manual

#### 1. Compilar o JAR
```bash
./mvnw clean package -DskipTests
```

#### 2. Transferir o JAR para o servidor
```bash
scp target/dashboard-0.0.1-SNAPSHOT.jar usuario@servidor:/caminho/para/aplicacao/
```

#### 3. Executar no servidor
```bash
java -jar dashboard-0.0.1-SNAPSHOT.jar \
  --server.port=8083 \
  --spring.profiles.active=prod \
  -DEUREKA_IP=endereco-eureka \
  -DCRUD_IP=endereco-crud
```

### Implantação com Docker

#### 1. Criar Dockerfile (se não existir)
```dockerfile
FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/dashboard-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```

#### 2. Construir a imagem
```bash
docker build -t v8tech/dashboard-api:latest .
```

#### 3. Executar o container
```bash
docker run -d \
  --name dashboard-api \
  -p 8083:8083 \
  -e SPRING_PROFILES_ACTIVE=prod \
  -e EUREKA_IP=endereco-eureka \
  -e CRUD_IP=endereco-crud \
  v8tech/dashboard-api:latest
```

### Implantação com Docker Compose

Crie ou edite o arquivo `docker-compose.yaml`:

```yaml
version: '3.8'

services:
  dashboard-api:
    image: v8tech/dashboard-api:latest
    build:
      context: ./dashboard
    ports:
      - "8083:8083"
    environment:
      - SPRING_PROFILES_ACTIVE=prod
      - EUREKA_IP=server-gateway
      - CRUD_IP=crud-api
    networks:
      - v8tech-network
    depends_on:
      - crud-api
      - server-gateway
    restart: unless-stopped

networks:
  v8tech-network:
    driver: bridge
```

Execute com:
```bash
docker-compose up -d
```

### Implantação com Kubernetes

#### 1. Criar arquivo de deployment
```yaml
# dashboard-deployment.yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: dashboard-api
  labels:
    app: dashboard-api
spec:
  replicas: 2
  selector:
    matchLabels:
      app: dashboard-api
  template:
    metadata:
      labels:
        app: dashboard-api
    spec:
      containers:
      - name: dashboard-api
        image: v8tech/dashboard-api:latest
        ports:
        - containerPort: 8083
        env:
        - name: SPRING_PROFILES_ACTIVE
          value: "prod"
        - name: EUREKA_IP
          value: "server-gateway-service"
        - name: CRUD_IP
          value: "crud-api-service"
        resources:
          requests:
            memory: "512Mi"
            cpu: "200m"
          limits:
            memory: "1Gi"
            cpu: "500m"
        readinessProbe:
          httpGet:
            path: /actuator/health
            port: 8083
          initialDelaySeconds: 30
          periodSeconds: 10
        livenessProbe:
          httpGet:
            path: /actuator/health
            port: 8083
          initialDelaySeconds: 60
          periodSeconds: 15
```

#### 2. Criar arquivo de serviço
```yaml
# dashboard-service.yaml
apiVersion: v1
kind: Service
metadata:
  name: dashboard-api-service
spec:
  selector:
    app: dashboard-api
  ports:
  - port: 8083
    targetPort: 8083
  type: ClusterIP
```

#### 3. Aplicar configurações
```bash
kubectl apply -f dashboard-deployment.yaml
kubectl apply -f dashboard-service.yaml
```

## Configuração

### Arquivos de Configuração

O principal arquivo de configuração é o `application.properties` (ou `application.yml`). Em ambiente de produção, recomenda-se usar um arquivo `application-prod.properties` específico:

```properties
# application-prod.properties
spring.profiles.active=prod
spring.application.name=dashboard

# Configurações do servidor
server.port=8083
server.error.include-stacktrace=never
server.error.include-message=never

# Configuração Eureka
eureka.client.serviceUrl.defaultZone=http://${EUREKA_IP}:8761/eureka/
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true

# CRUD Service Configuration
crud.service.base-url=http://${CRUD_IP}:8082

# Cache Configuration
spring.cache.type=caffeine
spring.cache.caffeine.spec=maximumSize=200,expireAfterWrite=60s

# Logging
logging.level.root=WARN
logging.level.tech.v8.dashboard=INFO
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
logging.file.name=/var/log/v8tech/dashboard-api.log
logging.file.max-size=10MB
logging.file.max-history=10

# Actuator
management.endpoints.web.exposure.include=health,info,metrics
management.endpoint.health.show-details=when_authorized
management.info.env.enabled=true
```

### Variáveis de Ambiente

| Variável | Descrição | Valor Padrão |
|----------|-----------|--------------|
| `SPRING_PROFILES_ACTIVE` | Perfil ativo do Spring | `dev` |
| `EUREKA_IP` | Endereço IP do servidor Eureka | `localhost` |
| `CRUD_IP` | Endereço IP do serviço CRUD | `localhost` |
| `JAVA_OPTS` | Opções da JVM | `-Xms512m -Xmx1g` |

## Monitoramento

### Endpoints Actuator

O Spring Boot Actuator fornece endpoints para monitoramento da saúde e métricas da aplicação:

- `/actuator/health`: Status da saúde da aplicação
- `/actuator/info`: Informações sobre a aplicação
- `/actuator/metrics`: Métricas da aplicação

### Métricas Importantes

| Métrica | Descrição | Valor Aceitável |
|---------|-----------|-----------------|
| `http.server.requests` | Tempo de resposta das requisições | <500ms (médio) |
| `jvm.memory.used` | Uso de memória | <80% da memória máxima |
| `system.cpu.usage` | Uso de CPU | <70% contínuo |
| `cache.gets` | Taxa de acertos no cache | >80% |

### Integrações para Monitoramento

#### Prometheus e Grafana
Para integrar com Prometheus, adicione a dependência:

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

Endpoint Prometheus: `/actuator/prometheus`

#### ELK Stack
Configure o logback para enviar logs para o Logstash:

```xml
<appender name="LOGSTASH" class="net.logstash.logback.appender.LogstashTcpSocketAppender">
    <destination>logstash-host:5000</destination>
    <encoder class="net.logstash.logback.encoder.LogstashEncoder"/>
</appender>
```

## Operação Diária

### Verificação de Logs

Os logs são essenciais para diagnosticar problemas. Localize-os em:
- `/var/log/v8tech/dashboard-api.log` (implantação tradicional)
- `docker logs dashboard-api` (Docker)
- `kubectl logs deployment/dashboard-api` (Kubernetes)

### Reinicialização do Serviço

#### Serviço Systemd (implantação tradicional)
```bash
sudo systemctl restart dashboard-api
```

#### Docker
```bash
docker restart dashboard-api
```

#### Kubernetes
```bash
kubectl rollout restart deployment/dashboard-api
```

### Atualização da Aplicação

#### Docker
```bash
# Construir nova imagem
docker build -t v8tech/dashboard-api:latest .

# Atualizar o container
docker stop dashboard-api
docker rm dashboard-api
docker run -d --name dashboard-api [mesmas-opcoes-anteriores] v8tech/dashboard-api:latest
```

#### Kubernetes
```bash
# Atualizar a imagem
kubectl set image deployment/dashboard-api dashboard-api=v8tech/dashboard-api:nova-versao

# Alternativa: aplicar configurações atualizadas
kubectl apply -f dashboard-deployment.yaml
```

## Backup e Recuperação

A Dashboard API não armazena dados persistentes diretamente, mas é recomendável:

1. Backup do arquivo JAR e configurações
2. Documentação das configurações de ambiente

## Plano de Recuperação de Desastres

### Cenário 1: Falha do Serviço
1. Verificar logs para identificar a causa
2. Reiniciar o serviço
3. Verificar se as dependências externas estão acessíveis

### Cenário 2: Falha de Comunicação com o CRUD API
1. Verificar se o CRUD API está acessível
2. Verificar configuração da variável `CRUD_IP`
3. Verificar logs de erro de conexão
4. Reiniciar o serviço se necessário

### Cenário 3: Problemas com Eureka
1. Verificar se o Eureka Server está acessível
2. Verificar configuração da variável `EUREKA_IP`
3. Considerar desabilitar temporariamente a integração com Eureka em caso de falha prolongada

## Escala e Performance

### Escalabilidade Horizontal
A aplicação é stateless e pode ser escalada horizontalmente:

1. **Docker Swarm**: Aumentar réplicas
   ```bash
   docker service scale dashboard-api=3
   ```

2. **Kubernetes**: Aumentar réplicas
   ```bash
   kubectl scale deployment dashboard-api --replicas=3
   ```

### Ajustes de Performance

#### JVM
Para aplicações com alto tráfego, ajuste as configurações da JVM:
```
JAVA_OPTS="-Xms1g -Xmx2g -XX:+UseG1GC -XX:MaxGCPauseMillis=200"
```

#### Cache
Aumentar o tamanho do cache para aplicações com alto tráfego:
```properties
spring.cache.caffeine.spec=maximumSize=500,expireAfterWrite=60s
```

## Segurança

### Headers HTTP
Configure headers de segurança no aplicativo:

```properties
server.tomcat.remote-ip-header=x-forwarded-for
server.tomcat.protocol-header=x-forwarded-proto
server.tomcat.redirect-context-root=false

# Spring Security Headers (se Spring Security estiver incluído)
security.headers.content-security-policy=default-src 'self'
security.headers.x-frame-options=DENY
```

### Proxy Reverso
Recomenda-se usar um proxy reverso (Nginx/Apache) para:
- Terminação SSL
- Rate limiting
- Filtro básico de ameaças

## Lista de Verificação de Saúde

Utilize esta lista para verificação regular da saúde do sistema:

- [ ] Endpoint `/actuator/health` retorna status UP
- [ ] Logs não mostram erros recorrentes
- [ ] Uso de memória dentro dos limites esperados
- [ ] Tempo de resposta dos endpoints dentro do esperado
- [ ] Serviço está registrado no Eureka
- [ ] Comunicação com o CRUD API está funcionando
- [ ] Cache está funcionando corretamente