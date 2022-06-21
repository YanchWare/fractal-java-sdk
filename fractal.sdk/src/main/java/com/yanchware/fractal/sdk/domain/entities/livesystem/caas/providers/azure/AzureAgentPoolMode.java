package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.providers.azure;

public enum AzureAgentPoolMode {
    SYSTEM("System"),
    USER("User");

    private final String id;

    AzureAgentPoolMode(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
