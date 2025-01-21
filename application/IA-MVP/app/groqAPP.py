import os
from dotenv import load_dotenv
from groq import Groq


load_dotenv()


class GroqClient:
    def __init__(self):

        api_key = os.getenv("GROQ_API_KEY")
        if not api_key:
            raise ValueError("A chave de API não foi definida. Verifique o .env ou as variáveis de ambiente.")


        self.client = Groq()


        self.model = "llama3-8b-8192"
        self.system_message = {
            "role": "system",
            "content": (
                "Você é um analista de dados da empresa Nexus, somos uma empresa focada "
                "em análise de dados escolares e com base nesses dados você irá fornecer "
                "insights e opiniões. Vale se orientar que a avaliação dos alunos é medida "
                "em porcentagens que vão de 0 a 100% e os alunos têm as seguintes matérias: "
                "Português, Matemática, Filosofia e Física. e responda na seguinte estrutura: 'Nexus AI: *conteudo separado por topicos 100% em pt-br e fale sempre dos alunos no plural* '"
            )
        }

    def ask_question(self, question):
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