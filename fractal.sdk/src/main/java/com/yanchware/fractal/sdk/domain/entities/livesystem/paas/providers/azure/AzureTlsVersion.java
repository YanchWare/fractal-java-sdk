package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureTlsVersion {
    TLS1_0("TLS1_0"),
    TLS1_1("TLS1_1"),
    TLS1_2("TLS1_2");

    private final String id;

    AzureTlsVersion(final String id) {
        this.id = id;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id;
    }
}
