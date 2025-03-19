# O Que?
Esse repositório se trata de um programa cujo objetivo é verificar a aderência de determinados candidados em comparação com a vaga em que deseja aplicar, levando em consideração:
1) Experiência Profissional;
2) Formações Acadêmicas;
3) Resultado na prova prática;
4) Outras competencias e "Soft Skills"


 essa aplicação é um microserviço que ira compor a nova plataforma de vagas da V8.tech.


# Porque?
Esta aplicação visa o aumento de produtividade no setor de Recursos Humanos, onde se desfaz da necessidade de avaliação manual de cada candidato que aplicou a determinada vaga no portal da V8.tech.

# Pra Quem?

Se trata de um produto interno desenvolvido para o setor de Recursos Humanos da V8.tech.

# Como?

Principal recurso dessa aplicação se dá a IA que utiliza LLM para uma análise comparativa entre candidato x vaga conforme os critérios estabelecidos pelo RH.


## DoR - Definition of Read
- Repositório GIT
- Licença IA (Groq Cloud)
- Conhecimento mínimo em Python
- Documentação com os detalhes de arquitetura
- O DEV devera solicitar ao RH parâmetros que irá compor a aderência final.

### Estoria do usuário (User Stories)

1. Como funcionaria de Recursos Humanos da V8.tech, perco lendo currículo por currículo. Gostaria de um processo que automatizasse as análises de cada currículo para que tenha um filtro nessa primeira etapa.



## DoD - Definition of Done
- Entregar uma aplicação microservice que verifica a aderência do candidato a determinada vaga



## Critérios de Aceite
Entregar uma aplicação do tipo “microservice”, baseada em IA (Groq Cloud), que verifica o nível de aderência entre cliente x vaga e retorna o resultado para aplicação principal para consumo do Front-End via endpoint.


---
## Guia de Instalação

Requisitos:

- Python 3.12.5 
- Key Groq Cloud

---

 ## 1) Instalação:

1. Clone este repositorio
2. Se preferir abra o Projeto em uma IDE de sua escolha
3. Abra um terminal
4. Certifique-se de esta no caminho:

```C:\...\BACKEND-IA\application\IA-MVP ```

Se o terminal não estiver localizado no caminho acima, utilize o comando para ir até o diretorio correto: 

```` cd ...\BACKEND-IA\application\IA-MVP ```` 

5. Com o terminal ainda aberto, ative um ambiente virtual Python com os seguintes comandos:

```cmd
python -m venv venv    
```
    
```cmd
.\venv\Scripts\activate
```
6. Instalar as dependencias:

```cmd
pip install -r requirements.txt
```

## 2) Iniciando Projeto

1. Abra um terminal;
1. Certifique-se de esta no caminho:

```` cd ...\BACKEND-IA\application\IA-MVP ````
3. Execute o comando:

```cmd
Python main.py
````
4. Por padrão o endpoint sera inciado em:

````https://localhost:5000 ````

endpoint para teste:

```` http://localhost:5000/calcular_aderencia````

exemplo JSON para teste:

```cmd
{

    
 "participante": {
        "name": "John Doe",
        "age": 30,
        "experiencias": "experiência em desenvolvimento Python, AWS certificado",
        "formacoesAcademica": "Engenharia de Computação",
        "licencasCertificados": "AWS Certified Solutions Architect"
    },
    "vagas": {
        "nome": "Desenvolvedor Python",
        "descricao": "Desenvolver aplicações escaláveis usando Python.",
        "requisitos": "Experiência em Python, Django e AWS"
    },
    "prova": {
        "id": 1001,
        "notaProva": 8.5
    }

}
 ```



Linguaguem natural
Linguagem ubiqua