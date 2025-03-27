# Referência da API

Esta seção contém a documentação detalhada de todos os endpoints disponíveis no Dashboard API, incluindo formatos de requisição, resposta e exemplos.

## Informações Gerais

- **URL Base**: `http://{host}:8083`
- **Formato de Resposta**: Todas as respostas são no formato JSON
- **Autenticação**: A autenticação é gerenciada pelo Gateway e não precisa ser implementada diretamente nas chamadas para esta API

## Endpoints Disponíveis

### 1. Listar Vagas Abertas

Retorna uma lista de vagas com status "aberta" e informações resumidas.

**Endpoint**: `GET /dashboard/vagas/abertas`

**Resposta de Sucesso (200 OK)**:

```json
{
  "totalVagasAbertas": 5,
  "vagas": [
    {
      "id": 1,
      "nome": "Desenvolvedor Java",
      "tipoVaga": "CLT",
      "localidade": "Remoto",
      "modeloVaga": "Híbrido",
      "dataCriacao": "10:30:25 21/03/2025",
      "quantidadeVagas": 2,
      "responsavel": "João Silva"
    },
    {
      "id": 2,
      "nome": "Analista de Dados",
      "tipoVaga": "PJ",
      "localidade": "São Paulo",
      "modeloVaga": "Presencial",
      "dataCriacao": "08:45:12 22/03/2025",
      "quantidadeVagas": 1,
      "responsavel": "Maria Santos"
    }
  ]
}
```

**Campos da Resposta**:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| totalVagasAbertas | Integer | Quantidade total de vagas abertas |
| vagas | Array | Lista de objetos representando vagas abertas |
| vagas[].id | Integer | Identificador único da vaga |
| vagas[].nome | String | Nome/título da vaga |
| vagas[].tipoVaga | String | Tipo da vaga (CLT, PJ, etc) |
| vagas[].localidade | String | Localização da vaga |
| vagas[].modeloVaga | String | Modelo de trabalho (Remoto, Híbrido, Presencial) |
| vagas[].dataCriacao | String | Data e hora de criação formatada (HH:mm:ss dd/MM/yyyy) |
| vagas[].quantidadeVagas | Integer | Número de vagas disponíveis |
| vagas[].responsavel | String | Nome do responsável pela vaga |

**Possíveis Erros**:

| Código | Descrição |
|--------|-----------|
| 404 | Não foram encontradas vagas abertas |
| 503 | Serviço CRUD está indisponível |

**Exemplo de Chamada**:

```bash
curl -X GET http://localhost:8083/dashboard/vagas/abertas
```

### 2. Listar Vagas Fechadas

Retorna uma lista de vagas com status "fechada" e informações resumidas.

**Endpoint**: `GET /dashboard/vagas/fechadas`

**Resposta de Sucesso (200 OK)**:

```json
{
  "totalVagasFechadas": 3,
  "vagas": [
    {
      "id": 3,
      "nome": "Analista de QA",
      "tipoVaga": "PJ",
      "localidade": "São Paulo",
      "modeloVaga": "Presencial",
      "dataCriacao": "14:20:30 15/03/2025",
      "responsavel": "Maria Santos"
    },
    {
      "id": 4,
      "nome": "DevOps Engineer",
      "tipoVaga": "CLT",
      "localidade": "Rio de Janeiro",
      "modeloVaga": "Híbrido",
      "dataCriacao": "09:15:45 16/03/2025",
      "responsavel": "Carlos Lima"
    }
  ]
}
```

**Campos da Resposta**:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| totalVagasFechadas | Integer | Quantidade total de vagas fechadas |
| vagas | Array | Lista de objetos representando vagas fechadas |
| vagas[].id | Integer | Identificador único da vaga |
| vagas[].nome | String | Nome/título da vaga |
| vagas[].tipoVaga | String | Tipo da vaga (CLT, PJ, etc) |
| vagas[].localidade | String | Localização da vaga |
| vagas[].modeloVaga | String | Modelo de trabalho (Remoto, Híbrido, Presencial) |
| vagas[].dataCriacao | String | Data e hora de criação formatada (HH:mm:ss dd/MM/yyyy) |
| vagas[].responsavel | String | Nome do responsável pela vaga |

**Possíveis Erros**:

| Código | Descrição |
|--------|-----------|
| 404 | Não foram encontradas vagas fechadas |
| 503 | Serviço CRUD está indisponível |

**Exemplo de Chamada**:

```bash
curl -X GET http://localhost:8083/dashboard/vagas/fechadas
```

### 3. Listar Vagas com Candidatos Aprovados

Retorna uma lista de vagas que possuem candidatos com status "aprovado".

**Endpoint**: `GET /dashboard/vagas/candidatos-aprovados`

**Resposta de Sucesso (200 OK)**:

```json
{
  "totalVagasComAprovados": 2,
  "vagas": [
    {
      "id": 1,
      "nome": "Desenvolvedor Java",
      "candidatosAprovados": [
        {
          "id": 101,
          "nome": "Ana Silva",
          "notaProva": 85.5,
          "aderencia": 90.2
        },
        {
          "id": 102,
          "nome": "Carlos Mendes",
          "notaProva": 90.0,
          "aderencia": 88.5
        }
      ]
    },
    {
      "id": 2,
      "nome": "Analista de Dados",
      "candidatosAprovados": [
        {
          "id": 103,
          "nome": "Pedro Oliveira",
          "notaProva": 92.0,
          "aderencia": 95.3
        }
      ]
    }
  ]
}
```

**Campos da Resposta**:

| Campo | Tipo | Descrição |
|-------|------|-----------|
| totalVagasComAprovados | Integer | Quantidade total de vagas com candidatos aprovados |
| vagas | Array | Lista de objetos representando vagas com candidatos aprovados |
| vagas[].id | Integer | Identificador único da vaga |
| vagas[].nome | String | Nome/título da vaga |
| vagas[].candidatosAprovados | Array | Lista de candidatos aprovados para esta vaga |
| vagas[].candidatosAprovados[].id | Integer | Identificador único do candidato |
| vagas[].candidatosAprovados[].nome | String | Nome do candidato |
| vagas[].candidatosAprovados[].notaProva | Double | Nota do candidato na prova (pode ser null) |
| vagas[].candidatosAprovados[].aderencia | Double | Percentual de aderência do candidato à vaga (pode ser null) |

**Possíveis Erros**:

| Código | Descrição |
|--------|-----------|
| 404 | Não foram encontradas vagas com candidatos aprovados |
| 400 | Erro ao processar dados de vagas com candidatos aprovados |
| 503 | Serviço CRUD está indisponível |

**Exemplo de Chamada**:

```bash
curl -X GET http://localhost:8083/dashboard/vagas/candidatos-aprovados
```

## Respostas de Erro

Todas as respostas de erro seguem o padrão:

```json
{
  "status": "NOT_FOUND",
  "statusCode": 404,
  "message": "Não foram encontradas vagas abertas",
  "timestamp": "2025-03-27 14:30:25",
  "path": "/dashboard/vagas/abertas",
  "errors": []
}
```

| Campo | Tipo | Descrição |
|-------|------|-----------|
| status | String | Status HTTP como string |
| statusCode | Integer | Código do status HTTP |
| message | String | Mensagem descritiva do erro |
| timestamp | String | Data e hora do erro (formato yyyy-MM-dd HH:mm:ss) |
| path | String | Caminho da requisição que gerou o erro |
| errors | Array | Lista de erros detalhados (usado principalmente em erros de validação) |

## Cache

Os endpoints implementam cache com expiração de 60 segundos para otimizar o desempenho. Não existem endpoints específicos para limpeza do cache, que é atualizado automaticamente após o tempo de expiração.