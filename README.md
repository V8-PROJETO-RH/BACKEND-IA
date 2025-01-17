Este repositório contém a infraestrutura para os microsserviços de backend e IA, incluindo os serviços de gerenciamento de usuários, vagas, candidatura e filtragem com IA. Abaixo você encontrará informações sobre como configurar e rodar os serviços, bem como a utilização da branch de desenvolvimento (`dev`).
Estrutura de Diretórios

Como Configurar e Rodar os Serviços
Pré-requisitos
- Docker e Docker Compose instalados
- Python 3.x (se necessário para os serviços backend)
- Acesso ao repositório do projeto
1. Utilizar a Branch de Desenvolvimento
Para garantir que está trabalhando na versão mais recente do projeto, faça checkout na branch de desenvolvimento (`dev`):
```bash
git checkout dev
```
2. Rodar os Serviços Backend
Para rodar os microsserviços de backend, use o Docker Compose:
```bash
docker-compose up --build
```
3. Rodar os Testes Backend
Para rodar os testes dos microsserviços de backend:
```bash
cd user-service && pytest
cd job-service && pytest
cd application-service && pytest
```
4. Rodar o Serviço de IA
Para rodar o serviço de filtragem com IA, também é necessário configurar o ambiente com as dependências específicas. Siga as etapas abaixo:
```bash
cd ai-service
pip install -r requirements.txt
docker build -t ai-service .
docker run -p 5000:5000 ai-service
```
5. Rodar os Testes de IA
Para rodar os testes do serviço de IA:
```bash
cd ai-service && pytest
```
Testes Disponíveis
Backend
- **user-service**: Testes para o serviço de gerenciamento de usuários.
- **vagas-service**: Testes para o serviço de gerenciamento de vagas.
- **candidatura-service**: Testes para o serviço de candidatura.

IA
- **ai-service**: Testes para o serviço de IA de filtragem.

SHARED-LIBS

A pasta shared-libs contém bibliotecas e utilitários compartilhados entre os microsserviços backend e IA. Ela facilita o reaproveitamento de código, o que garante uma manutenção mais eficiente e menor duplicação de lógica.

Estrutura da pasta shared-libs
- **auth**: Funções e métodos relacionados à autenticação e autorização.
- **database**: Conexões e interações com bancos de dados, configurando as conexões de maneira centralizada.
- **utils**: Funções utilitárias gerais, como manipulação de dados e formatação de respostas.
