package com.yanchware.fractal.sdk.domain.environment.service.commands;

import java.util.Map;

public record AwsAccountInitializationRequest(
  String organizationId,
  String accountId,
  String region,
  Map<String, String> tags)
{
}