package com.yanchware.fractal.sdk.domain.environment.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record EnvironmentResponse(
  EnvironmentIdDto managementEnvironmentId,
  EnvironmentIdDto id,
  String name,
  Collection<UUID> resourceGroups,
  Map<String, Object> parameters,
  String defaultCiCdProfileShortName,
  String status,
  Date createdAt,
  String createdBy,
  Date updatedAt,
  String updatedBy){ }


