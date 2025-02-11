package tech.v8.crudbackendmvp.model.enums;

public enum Role {
    COMUM,
    RH;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static Role fromString(String value) {
        StringBuilder statusDisponiveis = new StringBuilder();
        for (Role status : Role.values()) {
            statusDisponiveis.append(status.name()).append(", ");
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        statusDisponiveis = new StringBuilder(statusDisponiveis.substring(0, statusDisponiveis.length() - 2));
        throw new IllegalArgumentException("Role '" + value + "' inválida. As roles atualmente disponíveis são: " + statusDisponiveis);
    }
}
