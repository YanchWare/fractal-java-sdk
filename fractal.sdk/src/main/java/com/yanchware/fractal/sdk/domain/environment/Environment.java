package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface Environment {
  EnvironmentIdValue getId();

  String getName();

  Collection<UUID> getResourceGroups();

  Map<String, String> getTags();

  Map<String, Object> getParameters();

  EnvironmentDto toDto();
}
