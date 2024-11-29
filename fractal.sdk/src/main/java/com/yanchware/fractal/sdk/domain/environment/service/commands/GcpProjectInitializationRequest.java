package com.yanchware.fractal.sdk.domain.environment.service.commands;

import java.util.Map;

public record GcpProjectInitializationRequest (
  String organizationId,
  String projectId,
  String region,
  Map<String, String> tags){}


