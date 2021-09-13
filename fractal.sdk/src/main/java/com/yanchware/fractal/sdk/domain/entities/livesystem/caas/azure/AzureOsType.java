package com.yanchware.fractal.sdk.domain.entities.livesystem.caas.azure;

public enum AzureOsType {
    LINUX("Linux"),
    WINDOWS("Windows");

    private final String id;

    AzureOsType(final String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
