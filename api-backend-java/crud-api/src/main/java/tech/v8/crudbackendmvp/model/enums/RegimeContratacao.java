package tech.v8.crudbackendmvp.model.enums;

public enum RegimeContratacao {
    PJ,
    CLT;

    // Mét odo para validar ignorando maiúsculas e minúsculas
    public static RegimeContratacao fromString(String value) {
        StringBuilder statusDisponiveis = new StringBuilder();
        for (RegimeContratacao status : RegimeContratacao.values()) {
            statusDisponiveis.append(status.name()).append(", ");
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }

        statusDisponiveis = new StringBuilder(statusDisponiveis.substring(0, statusDisponiveis.length() - 2));
        throw new IllegalArgumentException("Regime de contratação '" + value + "' inválido. Os regimes atualmente disponíveis são: " + statusDisponiveis);
    }
}
