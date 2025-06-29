package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentTypeDto;
import com.yanchware.fractal.sdk.utils.StringHelper;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record EnvironmentIdValue(EnvironmentType type, UUID ownerId, String shortName) {

    @NotNull
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

    public static EnvironmentIdValue fromDto(EnvironmentIdDto dto) {
        return new EnvironmentIdValue(
          EnvironmentType.fromString(dto.type().toString()),
          dto.ownerId(),
          dto.shortName());
    }
}
