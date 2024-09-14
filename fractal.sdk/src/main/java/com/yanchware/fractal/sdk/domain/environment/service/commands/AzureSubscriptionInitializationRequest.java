package com.yanchware.fractal.sdk.domain.environment.service.commands;

import java.util.Map;
import java.util.UUID;

public record AzureSubscriptionInitializationRequest (
  UUID tenantId,
  UUID subscriptionId,
  String region,
  Map<String, String> tags){}
