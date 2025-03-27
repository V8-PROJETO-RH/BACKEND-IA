# API Endpoints

Este documento descreve todos os endpoints disponíveis na API de Recrutamento da V8.TECH, agrupados por recursos.

## Convenções

- Base URL: `http://localhost:8082`
- Formato de resposta: JSON
- Autenticação: Gerenciada pelo API Gateway (não implementada nesta API)

## Sumário

- [Candidatos](#candidatos)
- [Funcionários](#funcionários)
- [Vagas](#vagas)
- [Provas](#provas)
- [Vagas Aplicadas](#vagas-aplicadas)
- [Resultados](#resultados)

## Paginação

Endpoints que retornam múltiplos resultados suportam paginação usando os seguintes parâmetros:

- `page`: Número da página (começando de 0). Default: 0
- `size`: Tamanho da página. Default: 10, Máximo: 50

Exemplo:
```
GET /vagas?page=2&size=20
```

Resposta paginada:
```json
{
  "vagas": [...],
  "totalPages": 10,
  "totalElements": 200
}
```

---

## Candidatos

### Listar Candidatos

```
GET /candidatos
```

**Parâmetros Query:**
- `page`: Número da página (default: 0)
- `size`: Tamanho da página (default: 10, max: 50)

**Resposta:**
```json
{
  "candidatos": [
    {
      "id": 1,
      "nome": "João Silva",
      "email": "joao.silva@email.com",
      "cpf": "123.456.789-00",
      "data_nascimento": "28/11/1990",
      "role": "COMUM",
      "telefone": "(11) 99999-9999",
      "genero": "MASCULINO"
    },
    // ...
  ],
  "totalPages": 5,
  "totalElements": 45
}
```

### Buscar Candidato por ID

```
GET /candidatos/{id}
```

**Parâmetros Path:**
- `id`: ID do candidato

**Resposta:**
```json
{
  "id": 1,
  "nome": "João Silva",
  "email": "joao.silva@email.com",
  "cpf": "123.456.789-00",
  "data_nascimento": "28/11/1990",
  "role": "COMUM",
  "telefone": "(11) 99999-9999",
  "genero": "MASCULINO"
}
```

### Criar Candidato

```
POST /candidatos
```

**Corpo da Requisição:**
```json
{
  "nome": "Maria Santos",
  "email": "maria.santos@email.com",
  "cpf": "987.654.321-00",
  "data_nascimento": "15/05/1992",
  "telefone": "(11) 88888-8888",
  "genero": "FEMININO"
}
```

**Resposta:**
```json
{
  "id": 2,
  "nome": "Maria Santos",
  "email": "maria.santos@email.com",
  "cpf": "987.654.321-00",
  "data_nascimento": "15/05/1992",
  "role": "COMUM",
  "telefone": "(11) 88888-8888",
  "genero": "FEMININO"
}
```

### Atualizar Candidato

```
PUT /candidatos/{id}
```

**Parâmetros Path:**
- `id`: ID do candidato

**Corpo da Requisição:**
```json
{
  "nome": "Maria Santos Silva",
  "email": "maria.santos@email.com",
  "cpf": "987.654.321-00",
  "data_nascimento": "15/05/1992",
  "telefone": "(11) 88888-8888",
  "genero": "FEMININO",
  "role": "COMUM"
}
```

**Resposta:**
```json
{
  "id": 2,
  "nome": "Maria Santos Silva",
  "email": "maria.santos@email.com",
  "cpf": "987.654.321-00",
  "data_nascimento": "15/05/1992",
  "role": "COMUM",
  "telefone": "(11) 88888-8888",
  "genero": "FEMININO"
}
```

### Excluir Candidato

```
DELETE /candidatos/{id}
```

**Parâmetros Path:**
- `id`: ID do candidato

**Resposta:** 
204 No Content

### Obter Experiências do Candidato

```
GET /candidatos/{id}/experiencias
```

**Parâmetros Path:**
- `id`: ID do candidato

**Resposta:**
```json
[
  {
    "id": 1,
    "titulo": "Desenvolvedor Java",
    "empresa": "Empresa XYZ",
    "descricao": "Desenvolvimento de aplicações web",
    "localidade": "São Paulo",
    "modelo": "PRESENCIAL",
    "competencias": "Java, Spring Boot, PostgreSQL",
    "dt_Inicio": "01/03/2018",
    "dt_Final": "15/12/2020"
  },
  // ...
]
```

### Adicionar Experiência ao Candidato

```
POST /candidatos/{id}/experiencias
```

**Parâmetros Path:**
- `id`: ID do candidato

**Corpo da Requisição:**
```json
{
  "titulo": "Desenvolvedor Java Sênior",
  "empresa": "Empresa ABC",
  "descricao": "Desenvolvimento de microsserviços",
  "localidade": "São Paulo",
  "modelo": "REMOTO",
  "competencias": "Java, Spring Cloud, Docker",
  "dt_Inicio": "01/01/2021",
  "dt_Final": "31/12/2022"
}
```

**Resposta:**
```json
{
  "id": 2,
  "titulo": "Desenvolvedor Java Sênior",
  "empresa": "Empresa ABC",
  "descricao": "Desenvolvimento de microsserviços",
  "localidade": "São Paulo",
  "modelo": "REMOTO",
  "competencias": "Java, Spring Cloud, Docker",
  "dt_Inicio": "01/01/2021",
  "dt_Final": "31/12/2022"
}
```

### Atualizar Experiência

```
PUT /candidatos/{id}/experiencias/{idExperiencia}
```

### Remover Experiência

```
DELETE /candidatos/{id}/experiencias/{idExperiencia}
```

> Nota: Endpoints similares existem para formações (`/formacoes`), habilidades (`/habilidades`) e idiomas (`/idiomas`).

---

## Funcionários

### Listar Funcionários

```
GET /funcionarios
```

### Buscar Funcionário por ID

```
GET /funcionarios/{id}
```

### Criar Funcionário

```
POST /funcionarios
```

**Corpo da Requisição:**
```json
{
  "nome": "Carlos Costa",
  "email": "carlos.costa@v8.tech",
  "cpf": "111.222.333-44",
  "data_nascimento": "10/01/1985",
  "departamento": "Recursos Humanos",
  "funcao": "Analista de RH",
  "role": "RH"
}
```

### Atualizar Funcionário

```
PUT /funcionarios/{id}
```

### Excluir Funcionário

```
DELETE /funcionarios/{id}
```

---

## Vagas

### Listar Vagas

```
GET /vagas
```

**Parâmetros Query:**
- `page`: Número da página (default: 0)
- `size`: Tamanho da página (default: 10, max: 50)

**Resposta:**
```json
{
  "vagas": [
    {
      "id": 1,
      "nome": "Desenvolvedor Java",
      "tipo": "CLT",
      "localidade": "São Paulo",
      "modelo": "HIBRIDO",
      "status": "ABERTA",
      "quantidade_vagas": 2,
      "faixa_salarial": 10000.00,
      "responsavel": {
        "id": 1,
        "nome": "Carlos Costa",
        "role": "RH"
      }
    },
    // ...
  ],
  "totalPages": 3,
  "totalElements": 25
}
```

### Buscar Vagas (com Filtros)

```
GET /vagas/search
```

**Parâmetros Query:**
- `nome_like`: Filtro por nome (opcional)
- `modelo_like`: Filtro por modelo de trabalho (opcional)
- `local_like`: Filtro por localidade (opcional)
- `status_like`: Filtro por status (opcional)
- `page`: Número da página (default: 0)
- `size`: Tamanho da página (default: 10)

**Resposta:** Mesmo formato de `/vagas`

### Buscar Vaga por ID

```
GET /vagas/{id}
```

**Parâmetros Path:**
- `id`: ID da vaga

**Resposta:**
```json
{
  "id": 1,
  "responsavel": {
    "id": 1,
    "nome": "Carlos Costa",
    "role": "RH"
  },
  "nome": "Desenvolvedor Java",
  "tipo": "CLT",
  "localidade": "São Paulo",
  "modelo": "HIBRIDO",
  "descricao": "Vaga para desenvolvedor Java experiente",
  "responsabilidades": ["Desenvolver APIs REST", "Manter sistemas legados"],
  "requisitos": ["Java 8+", "Spring Boot", "JPA/Hibernate"],
  "faixa_salarial": 10000.00,
  "regime_contratacao": "CLT",
  "beneficios": ["Vale refeição", "Plano de saúde", "Gympass"],
  "status": "ABERTA",
  "quantidade_vagas": 2,
  "atribuicoes": "Desenvolvimento e manutenção de sistemas",
  "data_criacao": "14:30:00 25/03/2025"
}
```

### Criar Vaga

```
POST /vagas
```

**Corpo da Requisição:**
```json
{
  "nome": "Desenvolvedor Front-end",
  "tipo": "PJ",
  "localidade": "Remoto",
  "modelo": "REMOTO",
  "descricao": "Vaga para desenvolvedor React experiente",
  "responsabilidades": ["Desenvolver interfaces", "Manter aplicação React"],
  "requisitos": ["React", "TypeScript", "CSS"],
  "faixa_salarial": 8000.00,
  "regime_contratacao": "PJ",
  "beneficios": ["Horário flexível", "Auxílio home office"],
  "responsavel_id": 1,
  "status": "ABERTA",
  "quantidade": 1,
  "atribuicoes": "Desenvolvimento de interfaces web"
}
```

### Atualizar Vaga

```
PUT /vagas/{id}
```

### Excluir Vaga

```
DELETE /vagas/{id}
```

### Listar Candidaturas para Vaga

```
GET /vagas/{id}/vagas-aplicadas
```

---

## Provas

### Listar Provas de uma Vaga

```
GET /vagas/{id}/provas
```

**Parâmetros Path:**
- `id`: ID da vaga

**Resposta:**
```json
[
  {
    "id": 1,
    "vaga": {
      "id": 1,
      "nome": "Desenvolvedor Java",
      "tipo": "CLT"
    },
    "descricao": "Prova técnica de Java",
    "resultados": []
  },
  // ...
]
```

### Criar Prova

```
POST /vagas/{id}/provas
```

**Parâmetros Path:**
- `id`: ID da vaga

**Corpo da Requisição:**
```json
{
  "descricao": "Prova técnica de algoritmos"
}
```

### Atualizar Prova

```
PUT /vagas/{id}/provas/{provaId}
```

### Excluir Prova

```
DELETE /vagas/{id}/provas/{provaId}
```

---

## Vagas Aplicadas

### Listar Candidaturas

```
GET /vagas-aplicadas
```

### Buscar Candidaturas (com Filtros)

```
GET /vagas-aplicadas/search
```

**Parâmetros Query:**
- `status`: Filtro por status da candidatura (obrigatório)
- `page`: Número da página (default: 0)
- `size`: Tamanho da página (default: 10)

### Buscar Candidatura por ID

```
GET /vagas-aplicadas/{id}
```

### Criar Candidatura

```
POST /vagas-aplicadas
```

**Corpo da Requisição:**
```json
{
  "candidato_id": 2,
  "vaga_id": 1,
  "nome_indicacao": "Indicação pelo LinkedIn",
  "status": "INSCRITO"
}
```

### Atualizar Candidatura

```
PUT /vagas-aplicadas/{id}
```

### Excluir Candidatura

```
DELETE /vagas-aplicadas/{id}
```

---

## Resultados

### Buscar Resultado da Candidatura

```
GET /vagas-aplicadas/{id}/resultados
```

**Parâmetros Path:**
- `id`: ID da candidatura

**Resposta:**
```json
{
  "id": 1,
  "prova": {
    "id": 1,
    "descricao": "Prova técnica de Java"
  },
  "nota_prova": 8.5,
  "aderencia": 90.0
}
```

### Registrar Resultado

```
POST /vagas-aplicadas/{id}/resultados
```

**Parâmetros Path:**
- `id`: ID da candidatura

**Corpo da Requisição:**
```json
{
  "prova_id": 1,
  "nota_prova": 8.5,
  "aderencia": 90.0
}
```

---

## Códigos de Status

| Código | Descrição                          | Situações                                                 |
|--------|------------------------------------|---------------------------------------------------------|
| 200    | OK                                 | Operação bem-sucedida                                    |
| 201    | Created                            | Recurso criado com sucesso                              |
| 204    | No Content                         | Operação concluída sem conteúdo para retornar           |
| 400    | Bad Request                        | Requisição inválida (erro de validação)                 |
| 404    | Not Found                          | Recurso não encontrado                                  |
| 500    | Internal Server Error              | Erro do servidor                                        |

## Formatos de Erro

```json
{
  "timestamp": "14:30:00 25/03/2025",
  "status": 400,
  "error": "Bad Request",
  "message": "Erro de validação nos campos fornecidos.",
  "fieldErrors": {
    "email": "endereço de email inválido",
    "cpf": "O CPF deve estar no formato XXX.XXX.XX-XX."
  }
}
```