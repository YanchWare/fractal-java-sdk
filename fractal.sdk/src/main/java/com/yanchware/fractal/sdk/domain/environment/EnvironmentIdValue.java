package com.yanchware.fractal.sdk.domain.environment;

import java.util.UUID;

public record EnvironmentIdValue(EnvironmentType type, UUID ownerId, String shortName) {

    @Override
    public String toString(){
        return String.format("%s/%s/%s", type.name(), ownerId, shortName);
    }
}
