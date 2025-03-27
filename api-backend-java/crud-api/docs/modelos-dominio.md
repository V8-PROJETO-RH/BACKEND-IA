# Modelos de Domínio

Este documento descreve os principais modelos de domínio utilizados no sistema de recrutamento da V8.TECH.

## Diagrama de Classes Simplificado

```
+-------------+      +---------------+      +--------------+
|   Pessoa    |<----|  Funcionário   |      |  Candidato   |
+-------------+      +---------------+      +--------------+
       ^                    |                      |
       |                    |                      |
       +--------------------+----------------------+
                           |
                           v
+--------------+      +-------------+      +--------------+
|     Vaga     |<-----|    Prova    |      |    Detalhes  |
+--------------+      +-------------+      +--------------+
      |                     |                     ^
      |                     |                     |
      v                     v                     +------------+
+--------------+      +---------------+      +----------+      |
| VagaAplicada |----->| ResultadoFinal|      |Formacao  |      |
+--------------+      +---------------+      +----------+      |
                                             |Experiencia|     |
                                             +----------+      |
                                             |Idiomas    |-----+
                                             +----------+
                                             |Habilidades|
                                             +----------+
```

## Entidades Principais

### Pessoa

Representa a entidade base para usuários no sistema. Pode ser especializada em Candidato ou Funcionário.

**Atributos:**
- `id`: Identificador único
- `nome`: Nome completo
- `email`: Email para contato (único)
- `cpf`: CPF (único)
- `dataNascimento`: Data de nascimento
- `role`: Papel no sistema (COMUM ou RH)
- `estadoLogico`: Flag para exclusão lógica

### Candidato

Representa um candidato que pode se aplicar a vagas.

**Atributos:**
- `id`: Identificador único
- `pessoa`: Referência à entidade Pessoa
- `telefone`: Telefone para contato
- `genero`: Gênero (MASCULINO, FEMININO, OUTRO)
- `linkedin`: URL para perfil do LinkedIn
- `vagasAplicadas`: Lista de candidaturas do candidato
- `detalhesFormacao`: Formações acadêmicas
- `detalhesExperiencia`: Experiências profissionais
- `detalhesIdiomas`: Idiomas
- `detalhesHabilidades`: Habilidades técnicas

### Funcionário

Representa um funcionário da empresa que pode gerenciar vagas.

**Atributos:**
- `id`: Identificador único
- `pessoa`: Referência à entidade Pessoa
- `departamento`: Departamento em que trabalha
- `funcao`: Cargo ou função
- `vagas`: Vagas sob responsabilidade do funcionário

### Vaga

Representa uma posição de trabalho disponível.

**Atributos:**
- `id`: Identificador único
- `responsavel`: Funcionário responsável pela vaga
- `nome`: Nome/título da vaga
- `tipo`: Tipo da vaga
- `localidade`: Localização da vaga
- `modelo`: Modelo de trabalho (PRESENCIAL, REMOTO, HIBRIDO)
- `descricao`: Descrição detalhada da vaga
- `responsabilidades`: Lista de responsabilidades
- `requisitos`: Lista de requisitos
- `faixaSalarial`: Faixa salarial oferecida
- `regimeContratacao`: Regime (CLT, PJ)
- `beneficios`: Lista de benefícios oferecidos
- `status`: Status da vaga (ABERTA, FECHADA)
- `qtdVagas`: Quantidade de posições
- `atribuicoes`: Detalhes sobre atribuições
- `dataCriacao`: Data de criação da vaga
- `estadoLogico`: Flag para exclusão lógica
- `provas`: Lista de provas associadas
- `vagasAplicadas`: Lista de candidaturas para esta vaga

### VagaAplicada

Representa uma candidatura de um Candidato a uma Vaga.

**Atributos:**
- `id`: Identificador único
- `candidato`: Candidato que se aplicou
- `vaga`: Vaga à qual o candidato se aplicou
- `resultadoFinal`: Resultado da candidatura
- `nomeIndicacao`: Nome da pessoa que indicou (se houver)
- `status`: Status da candidatura

### Prova

Representa uma avaliação técnica associada a uma vaga.

**Atributos:**
- `id`: Identificador único
- `vaga`: Vaga à qual a prova está associada
- `resultados`: Resultados dos candidatos
- `descricao`: Descrição da prova
- `estadoLogico`: Flag para exclusão lógica

### ResultadoFinal

Representa o resultado de um candidato em uma prova específica.

**Atributos:**
- `id`: Identificador único
- `prova`: Prova realizada
- `candidato`: Candidato avaliado
- `vagasAplicadas`: Candidaturas relacionadas
- `nota`: Nota obtida na prova
- `aderencia`: Percentual de aderência à vaga

### DetalhesExperiencias

Representa a experiência profissional de um candidato.

**Atributos:**
- `id`: Identificador único
- `candidato`: Candidato relacionado
- `titulo`: Cargo/título
- `empresa`: Nome da empresa
- `descricao`: Descrição das atividades
- `localidade`: Local de trabalho
- `modelo`: Modelo de trabalho (PRESENCIAL, REMOTO, HIBRIDO)
- `competencias`: Competências utilizadas
- `dataInicio`: Data de início
- `dataFinal`: Data de término

### DetalhesFormacao

Representa a formação acadêmica de um candidato.

**Atributos:**
- `id`: Identificador único
- `candidato`: Candidato relacionado
- `nomeInstituicao`: Nome da instituição
- `escolaridade`: Nível de escolaridade
- `area`: Área de estudo
- `dataInicio`: Data de início
- `dataFinal`: Data de conclusão

### DetalhesIdiomas

Representa os idiomas falados por um candidato.

**Atributos:**
- `id`: Identificador único
- `candidato`: Candidato relacionado
- `nome`: Nome do idioma
- `proficiencia`: Nível de proficiência

### DetalhesHabilidades

Representa as habilidades técnicas de um candidato.

**Atributos:**
- `id`: Identificador único
- `candidato`: Candidato relacionado
- `habilidade`: Nome da habilidade

## Enums

### Modelo
- `PRESENCIAL`
- `REMOTO`
- `HIBRIDO`

### StatusVaga
- `ABERTA`
- `FECHADA`

### RegimeContratacao
- `CLT`
- `PJ`

### Role
- `COMUM` (candidato comum)
- `RH` (funcionário de RH)

### Genero
- `MASCULINO`
- `FEMININO`
- `OUTRO`

### ExFuncionario
- `SIM`
- `NAO`

## Relacionamentos

- **Pessoa -> Candidato/Funcionário**: Relacionamento um-para-um (herança por composição)
- **Funcionário -> Vaga**: Relacionamento um-para-muitos (um funcionário responsável por várias vagas)
- **Vaga -> Prova**: Relacionamento um-para-muitos (uma vaga pode ter várias provas)
- **Candidato <-> Vaga**: Relacionamento muitos-para-muitos implementado através de VagaAplicada
- **VagaAplicada -> ResultadoFinal**: Relacionamento um-para-um (uma candidatura tem um resultado)
- **Prova -> ResultadoFinal**: Relacionamento um-para-muitos (uma prova pode ter vários resultados)
- **Candidato -> Detalhes**: Relacionamentos um-para-muitos (um candidato tem várias formações, experiências, etc.)

## Padrões e Validações

- **Email**: Validado por regex para garantir formato correto
- **CPF**: Validado para formato XXX.XXX.XXX-XX
- **Exclusão Lógica**: Implementada com `estadoLogico` para manter histórico
- **Datas**: Validações para garantir que data final não seja anterior a data inicial
- **Campos Obrigatórios**: Anotações como @NotNull, @NotBlank e @NotEmpty