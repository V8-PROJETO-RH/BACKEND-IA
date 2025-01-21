from flask import Flask
from app.groqAPP import GroqClient

app = Flask(__name__)


@app.route('/')
def hello_world():  # put application's code here
    return 'Hello World!'


if __name__ == '__main__':
    app.run()

class DataApplication:
    def __init__(self):
        self.groq_client = GroqClient()




def main():
        question = "vai ser a response dos dados da vaga"
        print(f'{question}')
        # Pergunta à GroqIA
        print("IA Analisando os dados")

        response = self.groq_client.ask_question(question)

