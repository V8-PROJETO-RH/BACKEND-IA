import psycopg2
import os
from dotenv import load_dotenv


load_dotenv()

class PostgreSQLDatabase:
    def __init__(self):
        self.host = os.getenv("DB_HOST")
        self.user = os.getenv("DB_USER")
        self.password = os.getenv("DB_PASSWORD")
        self.database = os.getenv("DB_NAME")
        self.port = os.getenv("DB_PORT", 5432)  # Define a porta padrão como 5432
        self.connection = None

    def connect(self):
        """Estabelecer conexão com o banco de dados PostgreSQL."""
        self.connection = psycopg2.connect(
            host=self.host,
            user=self.user,
            password=self.password,
            database=self.database,
            port=self.port
        )

    def execute_select(self, query):
        """Executar uma consulta SELECT e retornar os resultados."""
        with self.connection.cursor() as cursor:
            cursor.execute(query)
            result = cursor.fetchall()
        return result

    def execute_insert(self, query, values):
        """Executar uma consulta INSERT com parâmetros."""
        with self.connection.cursor() as cursor:
            cursor.execute(query, values)
            self.connection.commit()

    def close(self):
        """Fechar a conexão com o banco de dados."""
        if self.connection:
            self.connection.close()
            self.connection = None
