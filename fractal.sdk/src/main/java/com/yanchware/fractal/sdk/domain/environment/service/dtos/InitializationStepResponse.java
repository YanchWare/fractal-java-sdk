package com.yanchware.fractal.sdk.domain.environment.service.dtos;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

public record InitializationStepResponse(
  UUID id,
  String name,
  String description,
  Integer order,
  String resourceName,
  String resourceType,
  Map<String, Object> outputFields,
  String lastOperationStatusMessage,
  String status,
  Integer retryCount,
  Date createdAt,
  Date updatedAt)
{
}
