package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import com.yanchware.fractal.sdk.domain.blueprint.service.dtos.BlueprintDto;

import java.util.Date;

public record LiveSystemDto(
  String liveSystemId,
  String fractalId,
  String requesterId,
  BlueprintDto blueprint,
  Date created,
  Date lastUpdated,
  LiveSystemStatusDto status,
  String statusMessage,
  EnvironmentDto environment)
{
}
