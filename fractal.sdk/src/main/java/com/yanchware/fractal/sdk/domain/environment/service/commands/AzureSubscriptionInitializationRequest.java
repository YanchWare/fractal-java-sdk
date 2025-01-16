package com.yanchware.fractal.sdk.domain.environment.service.commands;

import com.yanchware.fractal.sdk.domain.environment.EnvironmentIdValue;

import java.util.Map;
import java.util.UUID;

public record AzureSubscriptionInitializationRequest(
  EnvironmentIdValue ManagementEnvironmentId,
  UUID tenantId,
  UUID subscriptionId,
  String region,
  Map<String, String> tags)
{
}
