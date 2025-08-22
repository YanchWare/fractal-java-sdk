package com.yanchware.fractal.sdk.domain.environment.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

public record EnvironmentResponse(
  EnvironmentIdDto managementEnvironmentId,
  EnvironmentIdDto id,
  String name,
  Collection<String> resourceGroups,
  Map<String, Object> parameters,
  String defaultCiCdProfileShortName,
  String status,
  Date createdAt,
  String createdBy,
  Date updatedAt,
  String updatedBy){ }


