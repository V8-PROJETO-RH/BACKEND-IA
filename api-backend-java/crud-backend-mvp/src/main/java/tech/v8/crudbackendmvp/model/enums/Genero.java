package tech.v8.crudbackendmvp.model.enums;

public enum Genero {
    MASCULINO,
    FEMININO,
    OUTRO;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static Genero fromString(String value) {
        StringBuilder statusDisponiveis = new StringBuilder();
        for (Genero status : Genero.values()) {
            statusDisponiveis.append(status.name()).append(", ");
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        statusDisponiveis = new StringBuilder(statusDisponiveis.substring(0, statusDisponiveis.length() - 2));
        throw new IllegalArgumentException("Genero '" + value + "' inválido. Os gêneros atualmente disponíveis são: " + statusDisponiveis);
    }
}
