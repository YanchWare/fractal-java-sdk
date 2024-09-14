package com.yanchware.fractal.sdk.domain.environment.service.commands;

import java.util.Map;

public record OciCompartmentInitializationRequest (
  String tenancyId,
  String compartmentId,
  String region,
  Map<String, String> tags){}