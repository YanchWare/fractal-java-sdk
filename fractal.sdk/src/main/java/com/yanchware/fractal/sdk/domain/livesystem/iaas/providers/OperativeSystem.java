package com.yanchware.fractal.sdk.domain.livesystem.iaas.providers;

import com.fasterxml.jackson.annotation.JsonValue;

public enum OperativeSystem {
    LINUX("linux"),
    WINDOWS("windows");

    private final String name;

    OperativeSystem(String name) {
        this.name = name;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
