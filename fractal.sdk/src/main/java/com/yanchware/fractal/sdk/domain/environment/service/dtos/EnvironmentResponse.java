package com.yanchware.fractal.sdk.domain.environment.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;
import java.util.*;

public record EnvironmentResponse(
  EnvironmentIdDto id,
  String name,
  Collection<UUID> resourceGroups,
  Map<String, Object> parameters,
  String status,
  Date createdAt,
  String createdBy,
  Date updatedAt,
  String updatedBy){ }

