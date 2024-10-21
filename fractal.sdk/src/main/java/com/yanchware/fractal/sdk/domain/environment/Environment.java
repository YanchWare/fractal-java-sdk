package com.yanchware.fractal.sdk.domain.environment;

import com.yanchware.fractal.sdk.domain.environment.service.dtos.EnvironmentResponse;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentDto;
import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.ProviderType;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public interface Environment {
  EnvironmentIdValue getId();
  String getName();
  Collection<UUID> getResourceGroups();
  Map<String, String> getTags();
  Map<ProviderType, CloudAgentEntity> getCloudAgentByProviderType();
  Map<String, Object> getParameters();
  boolean doesNotNeedUpdate(EnvironmentResponse existingEnvironmentResponse);
  EnvironmentDto toDto();
}
