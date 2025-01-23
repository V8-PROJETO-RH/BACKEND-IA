package com.mvp.backend.backendmvp.model.enums;

public enum StatusVaga {
    ATIVO,
    CONTRATADO,
    SUSPENSO;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static StatusVaga fromString(String value) {
        for (StatusVaga status : StatusVaga.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Status inválido: " + value);
    }

}
