from flask import Flask, jsonify, request
import requests
from app.groqAPP import GroqClient # Ou GoogleAIStudioClient, se você modificou
import json

app = Flask(__name__)

# Link da API Java
API_JAVA = '' # Substitua pelo link da sua API

class DataApplication:
    def __init__(self):
        self.groq_client = GroqClient() # Ou GoogleAIStudioClient, se você modificou

    def calcular_aderencia(self, question):
        return self.groq_client.calcular_aderencia(question)

@app.route("/fetch_data", methods=["GET"])
def fetch_data():
    try:
        response = requests.get(API_JAVA)
        response.raise_for_status()  # Lança exceção para status de erro (4xx ou 5xx)
        data = response.json()
        return jsonify({"status": "success", "data": data})
    except requests.exceptions.RequestException as e:
        return jsonify({"status": "error", "message": f"Erro ao buscar dados da API Java: {str(e)}"}), 500
    except json.JSONDecodeError:
        return jsonify({"status": "error", "message": "Resposta da API Java não é um JSON válido."}), 500

@app.route("/nivel_aderencia", methods=["GET"])
def nivel_aderencia():
    data_app = DataApplication()

    # Obtém os dados da API Java
    fetch_response = requests.get(request.url_root + "fetch_data")  # Use a URL relativa
    if fetch_response.status_code != 200:
        return jsonify({"status": "error", "message": "Falha ao obter dados da API Java."}), 500

    api_data = fetch_response.json().get("data")

    # Prepara a pergunta para a IA
    question = f"""
    Experiência: {api_data.get('experiencia')}
    Formação: {api_data.get('formacao')}
    Prova: Nota {api_data.get('nota_prova')}/10
    Competências: {api_data.get('competencias')}
    """

    # Chama a IA e obtém o resultado
    response = data_app.calcular_aderencia(question)

    try:
        nivel_aderencia = {"nivel": float(response.replace('%', ''))}
        return jsonify(nivel_aderencia)
    except ValueError:
        return jsonify({"status": "error", "message": "Resposta da IA não é um número válido."}), 500

if __name__ == '__main__':
    app.run(debug=True) # debug true para facilitar o desenvolvimento