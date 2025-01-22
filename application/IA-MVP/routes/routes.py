from flask import Blueprint, request
from controllers.aderencia_controller import calcular_aderencia_handler

routes_bp = Blueprint('routes', __name__)

@routes_bp.route('/calcular_aderencia', methods=['POST'])
def calcular_aderencia():
    data = request.json
    return calcular_aderencia_handler(data)