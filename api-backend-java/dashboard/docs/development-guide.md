# Guia de Desenvolvimento

Este guia fornece instruções detalhadas para desenvolvedores que desejam contribuir ou manter a Dashboard API.

## Configuração do Ambiente de Desenvolvimento

### Pré-requisitos

- JDK 21 ou superior
- Maven 3.8+ (ou use o wrapper Maven incluído no projeto)
- IDE de sua preferência (recomendado: IntelliJ IDEA ou VS Code com extensões Java)
- Git
- Docker (opcional, para desenvolvimento com containers)

### Obtendo o Código

```bash
# Clone o repositório
git clone [url-do-repositorio]

# Navegue até o diretório do projeto
cd api-backend-java/dashboard
```

### Importando o Projeto

#### IntelliJ IDEA
1. Selecione "Open" ou "Import Project"
2. Navegue até a pasta do projeto e selecione o arquivo `pom.xml`
3. Selecione "Open as Project"
4. Aguarde a importação e indexação do projeto

#### VS Code
1. Abra o VS Code
2. Selecione File > Open Folder
3. Navegue até a pasta do projeto e selecione-a
4. Instale as extensões recomendadas para Java, se solicitado

### Configurando Variáveis de Ambiente

Para desenvolvimento local, você pode configurar as variáveis de ambiente no seu IDE ou criar um arquivo `.env` na raiz do projeto:

```properties
CRUD_IP=localhost
EUREKA_IP=localhost
```

Alternativamente, você pode configurar essas variáveis diretamente no seu IDE:

**IntelliJ IDEA**:
1. Edit Configurations > Spring Boot > DashApiApplication
2. Em "Environment variables", adicione: `CRUD_IP=localhost;EUREKA_IP=localhost`

**VS Code**:
1. Crie um arquivo `.vscode/launch.json`
2. Adicione as variáveis de ambiente na seção "env"

## Executando a Aplicação

### Usando Maven

```bash
# Compilar o projeto
./mvnw clean install

# Executar a aplicação
./mvnw spring-boot:run
```

### Usando o IDE

**IntelliJ IDEA**:
1. Localize a classe `DashApiApplication.java`
2. Clique com o botão direito do mouse > Run 'DashApiApplication'

**VS Code**:
1. Localize a classe `DashApiApplication.java`
2. Clique no ícone "Run" acima do método `main`

### Executando com Docker (opcional)

```bash
# Construir a imagem
docker build -t dashboard-api .

# Executar o container
docker run -p 8083:8083 -e CRUD_IP=host.docker.internal -e EUREKA_IP=host.docker.internal dashboard-api
```

## Estrutura do Código

```
src/main/java/tech/v8/dashboard/
├── client/                   # Clientes para comunicação com serviços externos
│   ├── VagaClient.java       # Interface que define os métodos de acesso ao CRUD
│   └── VagaClientImpl.java   # Implementação usando RestTemplate
├── config/                   # Configurações da aplicação
│   └── SwaggerConfig.java    # Configuração do Swagger/OpenAPI
├── controller/               # Controladores REST
│   └── VagaController.java   # Endpoints de vagas
├── exception/                # Classes para tratamento de exceções
│   ├── ApiErrorResponse.java        # Resposta padronizada de erro
│   ├── ApiException.java            # Classe base para exceções
│   ├── BadRequestException.java     # Exceção para requisições inválidas
│   ├── GlobalExceptionHandler.java  # Handler global de exceções
│   ├── ResourceNotFoundException.java # Exceção para recursos não encontrados
│   └── ServiceUnavailableException.java # Exceção para serviços indisponíveis
├── model/                    # Modelos de dados e DTOs
│   ├── Vaga.java             # Modelo de vaga
│   ├── VagaCandidatoAprovado.java  # Modelo de vaga com candidato aprovado
│   ├── VagasResponse.java    # Resposta do CRUD para vagas
│   ├── VagasCandidatosAprovadosResponse.java # Resposta para vagas com candidatos aprovados
│   └── dto/                  # Data Transfer Objects
│       ├── VagasAbertasDTO.java     # DTO para vagas abertas
│       ├── VagasFechadasDTO.java    # DTO para vagas fechadas
│       └── VagasCandidatosAprovadosDTO.java  # DTO para vagas com candidatos aprovados
└── service/                  # Camada de serviço
    └── VagaService.java      # Lógica de negócio para vagas
```

## Convenções de Código

### Estilo de Código

- Use o estilo de código padrão do Java
- Use a formatação automática do IDE sempre que possível
- Mantenha classes e métodos com responsabilidades únicas e bem definidas
- Limite o número de linhas por classe (máx ~300) e método (máx ~30)
- Prefira imutabilidade quando possível (uso de `final`, records, etc.)

### Nomenclatura

- Classes: PascalCase (ex: `VagaService`)
- Métodos e variáveis: camelCase (ex: `obterVagasAbertas`)
- Constantes: SNAKE_CASE_MAIÚSCULO (ex: `MAX_RESULTS`)
- Pacotes: tudo minúsculo (ex: `tech.v8.dashboard`)

### Logging

Utilize o logger SLF4J em todas as classes para registro de informações:

```java
private static final Logger logger = LoggerFactory.getLogger(MinhaClasse.class);

// Exemplos de uso
logger.debug("Processando {} itens", quantidade);
logger.info("Operação concluída com sucesso");
logger.warn("Situação inesperada, mas não crítica");
logger.error("Erro ao processar requisição", excecao);
```

### Documentação

- Adicione comentários Javadoc para classes públicas e métodos
- Mantenha comentários atualizados com o código
- Documente parâmetros, exceções e valores de retorno

```java
/**
 * Obtém informações sobre vagas abertas
 *
 * @return DTO contendo dados sobre vagas abertas
 * @throws ResourceNotFoundException se nenhuma vaga aberta for encontrada
 * @throws ServiceUnavailableException se o serviço CRUD estiver indisponível
 */
public VagasAbertasDTO obterVagasAbertas() {
    // ...
}
```

## Testes

### Estrutura de Testes

O projeto segue uma abordagem de testes em camadas:

- **Testes Unitários**: Testam componentes isoladamente
- **Testes de Integração**: Testam a interação entre componentes
- **Testes de API**: Testam os endpoints HTTP

### Executando Testes

```bash
# Executar todos os testes
./mvnw test

# Executar uma classe de teste específica
./mvnw test -Dtest=VagaServiceTest

# Executar um método de teste específico
./mvnw test -Dtest=VagaServiceTest#testObterVagasAbertas

# Gerar relatório de cobertura de testes
./mvnw verify
```

### Escrevendo Testes

#### Testes Unitários

```java
@ExtendWith(MockitoExtension.class)
class VagaServiceTest {

    @Mock
    private VagaClient vagaClient;

    @InjectMocks
    private VagaService vagaService;

    @Test
    void obterVagasAbertas_DeveRetornarVagasAbertas_QuandoExistiremVagas() {
        // Arrange
        var response = new VagasResponse();
        // configurar response...
        when(vagaClient.obterVagasPorStatus("aberta")).thenReturn(response);

        // Act
        var result = vagaService.obterVagasAbertas();

        // Assert
        assertNotNull(result);
        assertEquals(response.getVagas().size(), result.totalVagasAbertas());
        // mais assertions...
    }
}
```

#### Testes de API

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class VagaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VagaService vagaService;

    @Test
    void getVagasAbertas_DeveRetornarStatus200_QuandoExistiremVagas() throws Exception {
        // Arrange
        var dto = new VagasAbertasDTO(1, List.of(
                new VagasAbertasDTO.VagaDTO(1, "Vaga Teste", "CLT", "Remoto", 
                          "Híbrido", LocalDateTime.now(), 1, "Responsável")
        ));
        
        when(vagaService.obterVagasAbertas()).thenReturn(dto);
        
        // Act & Assert
        mockMvc.perform(get("/dashboard/vagas/abertas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalVagasAbertas").value(1))
                .andExpect(jsonPath("$.vagas[0].id").value(1))
                .andExpect(jsonPath("$.vagas[0].nome").value("Vaga Teste"));
    }
}
```

## Fluxo de Trabalho Git

### Branches

- `main`: Branch principal, contém código estável e pronto para produção
- `develop`: Branch de desenvolvimento, integra features completadas
- `feature/*`: Branches para desenvolvimento de novas funcionalidades
- `bugfix/*`: Branches para correção de bugs
- `hotfix/*`: Branches para correções urgentes em produção

### Processo de Desenvolvimento

1. Crie um branch a partir de `develop` para sua feature/bugfix:
   ```bash
   git checkout develop
   git pull
   git checkout -b feature/nova-funcionalidade
   ```

2. Faça seus commits com mensagens claras e informativas:
   ```bash
   git commit -m "feat: implementa endpoint de filtro de vagas por localidade"
   ```

3. Mantenha seu branch atualizado com o develop:
   ```bash
   git fetch
   git merge origin/develop
   ```

4. Ao finalizar, envie seu branch para o repositório remoto:
   ```bash
   git push origin feature/nova-funcionalidade
   ```

5. Crie um Pull Request para o branch `develop`
6. Após aprovação e revisão de código, faça o merge

### Convenções de Commits

Siga o padrão Conventional Commits:

- `feat`: Nova funcionalidade
- `fix`: Correção de bug
- `docs`: Alterações na documentação
- `style`: Formatação, ponto e vírgula, etc; sem alteração de código
- `refactor`: Refatoração de código
- `test`: Adicionando ou corrigindo testes
- `chore`: Atualizações de tarefas de build, etc; sem alteração de código

Exemplos:
```
feat(vagas): adiciona filtro por localidade
fix(cache): corrige expiração prematura do cache
docs: atualiza documentação de instalação
```

## Processo de Release

1. Atualize a versão no arquivo `pom.xml`
2. Atualize o CHANGELOG.md com as alterações da nova versão
3. Crie um Pull Request de `develop` para `main`
4. Após aprovação e merge, crie uma tag com a versão:
   ```bash
   git tag -a v1.0.0 -m "Release v1.0.0"
   git push origin v1.0.0
   ```
5. O pipeline CI/CD detectará a nova tag e iniciará o processo de deploy

## Troubleshooting

### Problemas Comuns

#### Erro de Conexão com CRUD API
- Verifique se a variável `CRUD_IP` está configurada corretamente
- Verifique se o serviço CRUD está em execução
- Tente acessar diretamente a URL do CRUD para verificar sua disponibilidade

#### Erro ao Registrar com Eureka
- Verifique se a variável `EUREKA_IP` está configurada corretamente
- Verifique se o servidor Eureka está em execução
- Verifique se a configuração no `application.properties` está correta

#### Problemas com Cache
Se você estiver enfrentando problemas com dados desatualizados:
- O tempo de cache está definido para 60 segundos, aguarde esse período
- Reinicie a aplicação para limpar todo o cache

## Referências e Recursos Adicionais

- [Documentação do Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Documentação do Spring Cloud](https://docs.spring.io/spring-cloud/docs/current/reference/html/)
- [Documentação do Caffeine Cache](https://github.com/ben-manes/caffeine/wiki)
- [Documentação do Swagger/SpringDoc](https://springdoc.org/)