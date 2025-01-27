package com.mvp.backend.backendmvp.model.enums;

public enum StatusVaga {
    ATIVO,
    CONTRATADO,
    SUSPENSO;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static StatusVaga fromString(String value) {
        StringBuilder statusDisponiveis = new StringBuilder();
        for (StatusVaga status : StatusVaga.values()) {
            statusDisponiveis.append(status.name()).append(", ");
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        statusDisponiveis = new StringBuilder(statusDisponiveis.substring(0, statusDisponiveis.length() - 2));
        throw new IllegalArgumentException("Status '" + value + "' inválido. Os status atualmente disponíveis são: " + statusDisponiveis);
    }

}
