from models.entidades import Participante, Vagas, Prova
from services.Google_client import GoogleAIStudioClient

def prepare_question(participante: Participante, vaga: Vagas, prova: Prova) -> str:
    question = (
        f"Analisar o participante com os seguintes dados:\n"
        f"Experiências: {participante.experiencias}\n"
        f"Formações Acadêmicas: {participante.formacoesAcademica}\n"
        f"Licenças e Certificados: {participante.licencasCertificados}\n\n"
        f"Vaga:\n"
        f"Nome: {vaga.nome}\n"
        f"Descrição: {vaga.descricao}\n"
        f"Requisitos: {vaga.requisitos}\n\n"
        f"Nota da Prova Pratica: {prova.notaProva}"
    )
    return question

def calculate_aderencia_with_google(participante: Participante, vaga: Vagas, prova: Prova) -> float:
    client = GoogleAIStudioClient()
    question = prepare_question(participante, vaga, prova)
    aderencia_response = client.calcular_aderencia(question)

    try:
        aderencia_percent = float(aderencia_response.replace('%', ''))
    except ValueError:
        print("Erro na interpretação da resposta:", aderencia_response)
        return 0.0

    return aderencia_percent