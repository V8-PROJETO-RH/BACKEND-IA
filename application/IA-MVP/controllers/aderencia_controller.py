from flask import jsonify
from models.entidades import Participante, Vagas, Prova
from services.calcular_aderencia import calculate_aderencia_with_google

def calcular_aderencia_handler(data):
    try:
        participante = Participante(**data['participante'])
        vagas = Vagas(**data['vagas'])
        prova = Prova(**data['prova'])

        aderencia = calculate_aderencia_with_google(participante, vagas, prova)
        return jsonify({'aderencia': aderencia})
    except Exception as e:
        return jsonify({'error': str(e)}), 400