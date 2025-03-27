# V8.TECH Dashboard API

API de dashboard para visualização e análise de métricas do sistema de recrutamento da V8.TECH.

## 📋 Visão Geral

O Dashboard API é um componente do ecossistema de recrutamento da V8.TECH que fornece endpoints especializados para visualização de dados consolidados sobre vagas e candidatos. Esta API atua como uma camada intermediária entre o serviço CRUD e o frontend do dashboard, processando e transformando dados para facilitar a visualização e tomada de decisão.

## 🚀 Início Rápido

### Pré-requisitos

- Java 21
- Maven 3.8+
- Serviço CRUD acessível
- Servidor Eureka (opcional, para ambiente de produção)

### Instalação e Execução

```bash
# Clone o repositório
git clone [url-do-repositorio]

# Navegue até o diretório do projeto
cd api-backend-java/dashboard

# Compile o projeto
./mvnw clean install

# Execute a aplicação
./mvnw spring-boot:run
```

### Configuração de Ambiente

Configure as seguintes variáveis de ambiente:

- `CRUD_IP`: Endereço IP do serviço CRUD (padrão: localhost)
- `EUREKA_IP`: Endereço IP do servidor Eureka (padrão: localhost)

## 🔍 Endpoints Principais

- **Vagas Abertas**: `GET /dashboard/vagas/abertas`
- **Vagas Fechadas**: `GET /dashboard/vagas/fechadas`
- **Vagas com Candidatos Aprovados**: `GET /dashboard/vagas/candidatos-aprovados`

## 📘 Documentação

A documentação completa está disponível na [pasta docs](/docs):

- [Índice da Documentação](/docs/README.md)
- [Referência da API](/docs/api-reference.md)
- [Arquitetura do Sistema](/docs/architecture.md)
- [Guia de Desenvolvimento](/docs/development-guide.md)
- [Instruções de Operação](/docs/operations.md)
- [Resolução de Problemas](/docs/troubleshooting.md)

## 🔧 Tecnologias Utilizadas

- Spring Boot 3.4.3
- Spring Cloud
- Spring Cache (Caffeine)
- Springdoc OpenAPI/Swagger
- Lombok

## 📝 Documentação da API

A documentação interativa da API está disponível através do Swagger:

- **URL do Swagger UI**: `http://localhost:8083/swagger-ui.html`
- **Especificação OpenAPI**: `http://localhost:8083/api-docs`

## 👥 Contato

- **Contato**: FilipeGuerreiro
- **Email**: filipe.guerreiro@v8.tech
- **URL**: https://www.v8.tech