package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureAppServiceRedundancyMode {
    NONE("None"),
    MANUAL("Manual"),
    FAILOVER("Failover"),
    ACTIVE_ACTIVE("ActiveActive"),
    GEO_REDUNDANT("GeoRedundant");

    private final String id;

    AzureAppServiceRedundancyMode(final String id) {
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
