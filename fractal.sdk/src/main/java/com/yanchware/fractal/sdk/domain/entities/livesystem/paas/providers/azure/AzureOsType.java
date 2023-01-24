package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yanchware.fractal.sdk.domain.entities.livesystem.ExtendableEnum;

import java.util.Collection;

public enum AzureOsType {
    LINUX("Linux"),
    WINDOWS("Windows");

    private final String id;

    AzureOsType(final String id) {
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
