package tech.v8.crudbackendmvp.model.enums;

public enum ExFuncionario {
    SIM,
    NAO;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static ExFuncionario fromString(String value) {
        StringBuilder statusDisponiveis = new StringBuilder();
        for (ExFuncionario status : ExFuncionario.values()) {
            statusDisponiveis.append(status.name()).append(", ");
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        statusDisponiveis = new StringBuilder(statusDisponiveis.substring(0, statusDisponiveis.length() - 2));
        throw new IllegalArgumentException("ExFuncionario '" + value + "' inválido. Os ExFuncionarios atualmente disponíveis são: " + statusDisponiveis);
    }
}
