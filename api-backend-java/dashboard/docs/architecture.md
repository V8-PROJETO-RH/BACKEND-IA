# Arquitetura do Sistema

Este documento detalha a arquitetura da API de Dashboard, descrevendo seus componentes, interações e decisões de design.

## Visão Geral da Arquitetura

O Dashboard API é construído como um microsserviço Spring Boot que se integra ao ecossistema mais amplo de serviços da V8.TECH. A aplicação segue uma arquitetura em camadas e se comunica com outros serviços via HTTP.

## Diagrama de Arquitetura

```
┌─────────────────┐        ┌─────────────────┐        ┌─────────────────┐
│                 │        │                 │        │                 │
│  Cliente Web    │◄─────► │  API Gateway    │◄─────► │   Dashboard     │
│  (Frontend)     │        │                 │        │      API        │
│                 │        │                 │        │                 │
└─────────────────┘        └─────────────────┘        └────────┬────────┘
                                                              │
                                                              │
                                                              ▼
                           ┌─────────────────┐        ┌─────────────────┐
                           │                 │        │                 │
                           │  Eureka Server  │◄─────► │   CRUD API     │
                           │ (Service Disc.) │        │                 │
                           │                 │        │                 │
                           └─────────────────┘        └─────────────────┘
```

## Componentes Principais

### 1. Camada de Controlador (Controllers)
- **Propósito**: Receber requisições HTTP, direcionar para os serviços apropriados e retornar respostas formatadas
- **Classes Principais**: `VagaController`
- **Responsabilidades**: 
  - Definição de endpoints REST
  - Mapeamento de parâmetros HTTP
  - Retorno de códigos HTTP apropriados

### 2. Camada de Serviço (Services)
- **Propósito**: Implementar a lógica de negócio e orquestrar as chamadas para serviços externos
- **Classes Principais**: `VagaService`
- **Responsabilidades**:
  - Processamento de dados
  - Transformação entre modelos do domínio e DTOs
  - Gerenciamento de cache
  - Tratamento de exceções de negócio

### 3. Camada de Cliente (Clients)
- **Propósito**: Comunicação com serviços externos
- **Classes Principais**: `VagaClient` (interface), `VagaClientImpl` (implementação)
- **Responsabilidades**:
  - Comunicação com o serviço CRUD
  - Tratamento de erros de comunicação
  - Conversão de respostas HTTP

### 4. Modelos (Models)
- **Propósito**: Representar entidades do domínio e estruturas de dados
- **Classes Principais**: 
  - Entidades: `Vaga`, `VagaCandidatoAprovado`
  - DTOs: `VagasAbertasDTO`, `VagasFechadasDTO`, `VagasCandidatosAprovadosDTO`

### 5. Tratamento de Exceções
- **Propósito**: Gerenciar erros de forma centralizada e padronizada
- **Classes Principais**: 
  - `GlobalExceptionHandler`
  - `ApiErrorResponse`
  - Exceções específicas: `ResourceNotFoundException`, `BadRequestException`, `ServiceUnavailableException`

## Fluxo de Dados

1. O cliente (frontend ou outro serviço) envia uma requisição HTTP para um dos endpoints da API
2. O controlador recebe a requisição e a encaminha para o método apropriado no serviço
3. O serviço verifica inicialmente se os dados estão disponíveis em cache
   - Se estiverem em cache, retorna os dados imediatamente
   - Se não estiverem em cache, prossegue para o passo seguinte
4. O serviço utiliza o cliente para fazer uma requisição ao serviço CRUD
5. O serviço CRUD processa a requisição e retorna os dados solicitados
6. O serviço da Dashboard API processa esses dados, transformando-os no formato adequado
7. Os dados processados são armazenados em cache (por 60 segundos)
8. O serviço retorna os dados para o controlador
9. O controlador formata a resposta HTTP e a envia de volta para o cliente

## Integração com Outros Serviços

### CRUD API
- **Propósito**: Fonte primária de dados sobre vagas e candidatos
- **Integração**: Via HTTP utilizando RestTemplate
- **Endpoints Utilizados**:
  - `GET /vagas/search` com parâmetros de status
  - `GET /vagas-aplicadas/search` com parâmetros de status

### Eureka Server
- **Propósito**: Registro e descoberta de serviços
- **Integração**: Cliente Eureka integrado ao Spring Boot
- **Funcionalidade**: Permite que o serviço seja descoberto dinamicamente por outros componentes da infraestrutura

## Decisões Técnicas

### Cache
A API implementa uma estratégia de cache utilizando o Caffeine Cache do Spring:
- **Escopo**: Por endpoint (cada endpoint possui seu próprio cache)
- **Tempo de Expiração**: 60 segundos
- **Tamanho Máximo**: 100 entradas
- **Benefícios**:
  - Redução da carga no serviço CRUD
  - Melhoria significativa no tempo de resposta para requisições frequentes
  - Maior resiliência em caso de indisponibilidade temporária do serviço CRUD

### Tratamento de Erros
A aplicação implementa um sistema robusto de tratamento de erros:
- Todas as exceções são interceptadas por um manipulador global
- Respostas de erro são padronizadas no formato JSON
- Códigos HTTP apropriados são utilizados para cada tipo de erro
- Mensagens de erro são claras e informativas, sem expor detalhes de implementação

### DTOs vs Entidades
A aplicação utiliza DTOs (Data Transfer Objects) para:
- Desacoplar o modelo de domínio interno da API exposta
- Controlar precisamente quais campos são expostos na API
- Permitir transformações e agregações antes de enviar os dados para o cliente

### Logging
A aplicação implementa um sistema de logging abrangente:
- Nível de log configurável
- Logs de requisições, erros e operações importantes
- Informações contextuais como identificadores de transação
- Mensagens de log estruturadas para facilitar a análise

## Considerações de Escalabilidade

O sistema foi projetado para ser escalável:

1. **Escalabilidade Horizontal**: 
   - Sendo stateless, múltiplas instâncias podem ser executadas simultaneamente
   - O registro no Eureka permite balanceamento de carga automático

2. **Mecanismos de Resiliência**:
   - Cache reduz dependência do serviço CRUD
   - Tratamento de erros robusto para falhas em serviços externos
   - Timeouts configuráveis para chamadas externas

3. **Desempenho**:
   - Consultas paginadas ao serviço CRUD
   - Processamento eficiente de dados usando Streams do Java
   - Monitoramento de performance via logs