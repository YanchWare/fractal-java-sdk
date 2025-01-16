package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import java.util.UUID;

public record EnvironmentIdDto(
  EnvironmentTypeDto type,
  UUID ownerId,
  String shortName)
{

  @Override
  public String toString() {
    return type + "/" + ownerId + "/" + shortName;
  }
}
