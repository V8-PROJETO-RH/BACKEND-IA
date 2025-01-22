from flask import Flask, jsonify
from app.groqAPP import GroqClient
import requests

app = Flask(__name__)

# Função para enviar uma requisição para a API do JAVA e armazenar o JSON de dados
API_JAVA = '' # Link da API

@app.route("/fetch_data", methods=["GET"])
def fetch_data():
    response = requests.get(API_JAVA)
    data = response.json()
    return jsonify({"status": "success", "data": data})

# Endpoint para retornar um JSON com o nível de aderência
@app.route("/nivel_aderencia", methods=["GET"])
def nivel_aderencia():
    nivel_aderencia = {
        "nivel": 0 
    }
    return jsonify(nivel_aderencia)

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



