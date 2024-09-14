package com.yanchware.fractal.sdk.domain.environment.service.dtos;

import com.yanchware.fractal.sdk.domain.livesystem.service.dtos.EnvironmentIdDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record InitializationRunResponse (
  UUID id,
  EnvironmentIdDto environmentId,
  String cloudProvider,
  List<InitializationStepResponse> steps,
  String status,
  String requester,
  Date createdAt){ }

