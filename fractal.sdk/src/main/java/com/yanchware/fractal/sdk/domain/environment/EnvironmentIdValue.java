package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentTypeDto;

import java.util.UUID;

public record EnvironmentIdValue(EnvironmentType type, UUID ownerId, String shortName) {

    @Override
    public String toString(){
        return String.format("%s\\%s\\%s", type.name(), ownerId, shortName);
    }

    public EnvironmentIdDto toDto() {
        return new EnvironmentIdDto(
                EnvironmentTypeDto.fromString(type.toString()),
                ownerId(),
                shortName());
    }
}