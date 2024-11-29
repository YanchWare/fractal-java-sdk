package com.yanchware.fractal.sdk.domain.environment.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;

import java.util.Date;
import java.util.UUID;

public record SecretResponse(
    UUID id,
    EnvironmentIdDto environmentId,
    String name,
    String value,
    Date createdAt,
    String createdBy,
    Date updatedAt,
    String updatedBy){ }
