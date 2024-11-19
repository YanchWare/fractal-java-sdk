package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentTypeDto;
import com.yanchware.fractal.sdk.utils.StringHelper;

import java.util.UUID;

public record EnvironmentIdValue(EnvironmentType type, UUID ownerId, String shortName) {

    @Override
    public String toString(){
        return String.format("%s/%s/%s", StringHelper.convertToTitleCase(type.name()), ownerId, shortName);
    }

    public EnvironmentIdDto toDto() {
        return new EnvironmentIdDto(
                EnvironmentTypeDto.fromString(type.toString()),
                ownerId(),
                shortName());
    }
}
