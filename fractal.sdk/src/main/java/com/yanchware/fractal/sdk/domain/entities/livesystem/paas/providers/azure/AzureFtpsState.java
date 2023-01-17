package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureFtpsState {
    ALL_ALLOWED("AllAllowed"),
    FTPS_ONLY("FtpsOnly"),
    DISABLED("Disabled");

    private final String id;

    AzureFtpsState(final String id) {
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
