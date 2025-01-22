from dataclasses import dataclass

@dataclass
class Participante:
    id: int
    name: str
    age: int
    experiencias: str
    formacoesAcademica: str
    licencasCertificados: str

@dataclass
class Vagas:
    id: int
    nome: str
    descricao: str
    requisitos: str
@dataclass
class Prova:
    id: int
    notaProva: float

