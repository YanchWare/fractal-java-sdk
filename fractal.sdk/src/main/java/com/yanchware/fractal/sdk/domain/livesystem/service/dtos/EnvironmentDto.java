package com.yanchware.fractal.sdk.domain.livesystem.service.dtos;

import java.util.Map;

public record EnvironmentDto(
  EnvironmentIdDto id,
  Map<String, Object> parameters) { }

