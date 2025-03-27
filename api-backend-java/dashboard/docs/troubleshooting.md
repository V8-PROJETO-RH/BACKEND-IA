# Guia de Troubleshooting

Este documento fornece informações para resolução de problemas comuns que podem ser encontrados durante a execução, desenvolvimento ou manutenção da Dashboard API.

## Problemas Comuns e Soluções

### 1. Serviço não inicia

#### Sintomas
- A aplicação não inicia e finaliza com erro
- Logs mostram exceções durante a inicialização

#### Possíveis Causas e Soluções

##### Porta já em uso
**Erro**: `Port 8083 already in use`  
**Solução**: 
- Verifique se não há outra instância da aplicação ou outro serviço usando a porta 8083
- Altere a porta da aplicação adicionando `--server.port=8084` aos argumentos de execução ou alterando o `application.properties`
- No Docker, verifique o mapeamento de portas

##### Erro de comunicação com o CRUD API ou Eureka
**Erro**: `Connection refused` ou `Unable to register with Eureka`  
**Solução**:
- Verifique se as variáveis de ambiente `CRUD_IP` e `EUREKA_IP` estão configuradas corretamente
- Certifique-se de que os serviços CRUD API e Eureka estão em execução
- Teste a conectividade com `curl` ou uma ferramenta similar:
  ```bash
  curl http://${CRUD_IP}:8082/actuator/health
  curl http://${EUREKA_IP}:8761/actuator/health
  ```

##### Versão incorreta do Java
**Erro**: `Unsupported class file major version` ou `UnsupportedClassVersionError`  
**Solução**:
- Verifique se o Java 21 ou superior está instalado e configurado como default: 
  ```bash
  java -version
  ```
- Atualize para a versão correta do Java se necessário

##### Problemas com dependências
**Erro**: `ClassNotFoundException` ou `NoClassDefFoundError`  
**Solução**:
- Recompile o projeto com `./mvnw clean package`
- Verifique se todas as dependências são resolvidas durante o build

### 2. Endpoints retornam erro 404 (Not Found)

#### Sintomas
- As requisições para os endpoints retornam erro 404

#### Possíveis Causas e Soluções

##### Caminho incorreto de acesso
**Solução**:
- Verifique se está usando o path correto: `/dashboard/vagas/abertas`, `/dashboard/vagas/fechadas`, ou `/dashboard/vagas/candidatos-aprovados`
- Verifique se não há um contexto raiz adicional configurado

##### Problemas com o controller
**Solução**:
- Verifique logs para erros durante a inicialização dos controllers
- Confirme que as anotações `@RestController` e `@RequestMapping` estão presentes no controller

### 3. Endpoints retornam erro 500 (Internal Server Error)

#### Sintomas
- As requisições retornam erro 500
- Existem exceções nos logs

#### Possíveis Causas e Soluções

##### Erro de comunicação com o CRUD API
**Erro**: `ResourceAccessException` ou `ServiceUnavailableException` nos logs  
**Solução**:
- Verifique se o CRUD API está acessível
- Verifique se os endpoints do CRUD API não mudaram
- Analise os logs detalhados para obter informações específicas sobre o erro

##### Erro de processamento de dados
**Erro**: `BadRequestException` ou outras exceções não relacionadas à conectividade  
**Solução**:
- Verifique os logs para a causa raiz da exceção
- Analise se os dados retornados pelo CRUD API estão no formato esperado

### 4. Dados desatualizados nos endpoints

#### Sintomas
- Os endpoints retornam dados antigos mesmo quando houve alterações no CRUD API

#### Possíveis Causas e Soluções

##### Cache não expirado
**Solução**:
- O cache está configurado para expirar após 60 segundos. Aguarde esse tempo e tente novamente
- Para desenvolvimento, considere desabilitar o cache temporariamente:
  ```properties
  spring.cache.type=none
  ```
- Reinicie a aplicação para limpar completamente o cache

### 5. Problemas com o registro no Eureka

#### Sintomas
- O serviço não aparece no dashboard do Eureka
- Logs mostram erros de comunicação com o Eureka

#### Possíveis Causas e Soluções

##### Configuração incorreta do Eureka
**Solução**:
- Verifique se o endereço do servidor Eureka está correto
- Confirme que o Eureka está em execução e acessível
- Verifique se o nome da aplicação está configurado corretamente em `spring.application.name`

##### Problemas de rede
**Solução**:
- Verifique se não há bloqueio de firewall ou regras de rede impedindo a comunicação
- Em ambientes Docker, verifique se os contêineres podem se comunicar através da rede

### 6. Tempo de resposta lento dos endpoints

#### Sintomas
- Os endpoints demoram muito para responder

#### Possíveis Causas e Soluções

##### Demora nas requisições para o CRUD API
**Solução**:
- Verifique o tempo de resposta do CRUD API com uma ferramenta como curl:
  ```bash
  time curl -s http://${CRUD_IP}:8082/vagas/search?status_like=aberta
  ```
- Analise os logs para identificar gargalos no processamento
- Verifique se há logs com aviso de timeout ou processamento lento

##### Recursos insuficientes
**Solução**:
- Verifique o uso de CPU e memória da aplicação
- Aumente os recursos disponíveis, especialmente memória, se necessário
- Otimize as configurações da JVM para melhor desempenho

### 7. Erros após atualização da aplicação

#### Sintomas
- Após atualizar para uma nova versão, os endpoints começam a falhar
- Erros como `NoSuchMethodError` ou `ClassNotFoundException` aparecem nos logs

#### Possíveis Causas e Soluções

##### Incompatibilidade entre versões
**Solução**:
- Verifique se há mudanças na API do CRUD que possam afetar a Dashboard API
- Reverta para a versão anterior enquanto investiga o problema
- Verifique se todas as dependências estão atualizadas corretamente

##### Problemas de deployment
**Solução**:
- Assegure que o processo de implantação foi concluído corretamente
- Verifique se o JAR ou container correto está sendo executado
- Em ambientes Kubernetes, verifique se o rollout foi concluído com sucesso

### 8. Problemas com permissões ou autenticação

#### Sintomas
- Acessos são negados mesmo quando parece que deveriam ser permitidos
- Erros `401 Unauthorized` ou `403 Forbidden`

#### Possíveis Causas e Soluções

##### Configuração de segurança
**Solução**:
- Verifique se o Gateway está propagando corretamente os cabeçalhos de autenticação
- Se a aplicação tem segurança própria, confirme que as configurações estão corretas

## Logs e Diagnóstico

### Aumentando o Nível de Log

Para depuração, às vezes é necessário aumentar temporariamente o nível de log:

#### Via application.properties
```properties
logging.level.root=INFO
logging.level.tech.v8.dashboard=DEBUG
logging.level.tech.v8.dashboard.client=TRACE  # Para ver comunicação detalhada com o CRUD API
```

#### Via Linha de Comando
```bash
java -jar dashboard-0.0.1-SNAPSHOT.jar --logging.level.tech.v8.dashboard=DEBUG
```

### Analisando Logs Efetivamente

1. **Procurar por exceções**:
   ```bash
   grep -i "exception\|error" dashboard-api.log
   ```

2. **Analisar requisições específicas**:
   ```bash
   grep -A 10 "/dashboard/vagas/abertas" dashboard-api.log
   ```

3. **Verificar chamadas ao CRUD API**:
   ```bash
   grep -i "Fazendo requisição para" dashboard-api.log
   ```

4. **Monitorar tempo de resposta**:
   ```bash
   grep -i "Total de vagas" dashboard-api.log | tail -50
   ```

## Ferramentas de Diagnóstico

### 1. Spring Boot Actuator

Endpoints úteis para diagnóstico:

- `/actuator/health`: Verifica a saúde da aplicação
- `/actuator/env`: Mostra variáveis de ambiente (requer configuração adicional)
- `/actuator/mappings`: Mostra mapeamentos de endpoints
- `/actuator/metrics`: Métricas da aplicação

### 2. JVM Tools

Para análise em nível de JVM:

```bash
# Mostrar processos Java em execução
jps -l

# Conectar ao processo Java e mostrar threads
jstack <pid>

# Mostrar uso de heap
jmap -heap <pid>
```

### 3. Monitoramento em Tempo Real

Para Docker:
```bash
docker stats dashboard-api
```

Para Kubernetes:
```bash
kubectl top pod -l app=dashboard-api
```

## Problemas não Listados

Se você encontrar um problema que não está listado neste guia:

1. **Analise os logs detalhadamente**  
   Os logs geralmente contêm informações valiosas sobre o problema

2. **Verifique atualizações recentes**  
   Mudanças recentes no código, configurações ou infraestrutura podem ser a causa

3. **Teste isoladamente**  
   Verifique se o problema está no Dashboard API ou nas dependências externas

4. **Reporte o problema**  
   Crie um issue no repositório com informações detalhadas sobre o problema e os passos para reproduzi-lo

## Contato para Suporte

Se você precisar de ajuda adicional, entre em contato:

- **Equipe de Desenvolvimento**: dev@v8.tech
- **Equipe DevOps**: devops@v8.tech
- **Repositório de Issues**: [Gitlab/Github URL]