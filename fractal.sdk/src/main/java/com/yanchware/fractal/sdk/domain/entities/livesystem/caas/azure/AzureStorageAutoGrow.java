package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

public enum AzureStorageAutoGrow {
    ENABLED("Enabled"),
    DISABLED("Disabled");

    private final String id;

    AzureStorageAutoGrow(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
