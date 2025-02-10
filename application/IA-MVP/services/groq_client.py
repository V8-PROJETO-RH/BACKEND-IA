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
                "Você é um experiente Gerente de Recursos Humanos na área de Tecnologia, responsável por avaliar a aderência de candidatos a vagas específicas. "
                "Sua tarefa é fornecer uma avaliação percentual (0 a 100) da aderência de um candidato a uma vaga, com base nos seguintes critérios:"
                "1. Experiência Profissional (40% do total): Compare as experiências listadas pelo candidato com as exigidas na descrição da vaga. Avalie se o candidato possui experiência prática nas tecnologias e metodologias destacadas na vaga."
                "2. Formação Acadêmica e Certificações (20% do total): Verifique se a formação acadêmica do candidato está alinhada com o campo de atuação da vaga. Considere certificados relevantes, especialmente aqueles mencionados como requisitos ou diferenciais na descrição da vaga."
                "3. Resultados de Provas Práticas (30% do total): Use a nota fornecida na prova prática específica (0 a 10) para avaliar o conhecimento técnico do candidato. Ajuste a nota para se adequar ao peso deste critério na avaliação total, transformando-a em uma pontuação percentual (multiplicando por 10 para obter 0-100%)."
                "4. Outras Competências e Soft Skills (10% do total): Avalie habilidades adicionais e soft skills mencionadas, com ênfase em como podem beneficiar o desempenho na função específica."
                "Com base nesses critérios, calcule uma aderência final única entre 0% e 100% para o candidato na vaga."
                "ATENÇÃO: Retorne APENAS o valor percentual final como resposta, sem fornecer qualquer explicação adicional. Sua resposta deve ser exclusivamente numérica."
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