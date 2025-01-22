import os
from dotenv import load_dotenv
from groq import Groq

load_dotenv()

class GroqClient:
    def __init__(self):
        api_key = os.getenv('GROQ_API_KEY')
        if not api_key:
            raise ValueError('Variável de ambiente: API_KEY não foi definida ou está inválida.')

        self.client = Groq()
        self.model = "llama3-8b-8192"
        self.system_message = {
            "role": "system",
            "content": (
                 "Você é um Gerente de Recursos Humanos na área de Tecnologia"
                "Você irá analisar currículos e, com base nos seus conhecimentos, você irá avaliar o quão aderente o candidato é àquela determinada vaga"
                "O cálculo de aderência irá funcionar da seguinte maneira, você analisará o perfil comparando com a vaga juntamente com o resultado de uma prova pratica específica da área em que o candidato deseja aplicar, lembrando que a prova ira de 0 a 10,"
                "Iremos te passar essas duas informações, tanto da vaga quanto o valor da prova que ele tirou. Você irá devolver uma média em porcentagem que vai de 0 a 100 porcento, onde 0 significa que a pessoa não tem aderência àquela vaga e 100 significa que ela atende 100 porcento da vaga, lembrando em que a média consiste na sua análise de comparação entre o perfil do candidato e os requisitos da vaga mais a nota que ele tirou na prova"
                "Na sua resposta, só me devolva exclusivamente o valor da média, NÃO PRECISA ME EXPLICAR como você chegou aquele resultado. e seja o mais criterioso possivel"
            )
        }

    def calcular_aderencia(self, question):
        completion = self.client.chat.completions.create(
            model=self.model,
            messages=[
                self.system_message,
                {"role": "user", "content": question}
            ],
            temperature=1,
            max_tokens=1140,
            top_p=1,
            stream=False,
            stop=None,
        )
        try:
            return completion.choices[0].message.content
        except (IndexError, AttributeError):
            return 'Sem resposta'