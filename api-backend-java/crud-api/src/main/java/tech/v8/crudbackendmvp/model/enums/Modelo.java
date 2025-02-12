package tech.v8.crudbackendmvp.model.enums;

public enum Modelo {
    PRESENCIAL,
    REMOTO,
    HIBRIDO;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static Modelo fromString(String value) {
        StringBuilder statusDisponiveis = new StringBuilder();
        for (Modelo status : Modelo.values()) {
            statusDisponiveis.append(status.name()).append(", ");
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        statusDisponiveis = new StringBuilder(statusDisponiveis.substring(0, statusDisponiveis.length() - 2));
        throw new IllegalArgumentException("Modelo '" + value + "' inválido. Os modelos atualmente disponíveis são: " + statusDisponiveis);
    }
}
