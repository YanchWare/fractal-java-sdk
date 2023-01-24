package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AzureResourceAccessRule(
  @JsonProperty("tenantId")
  String tenantId,
  @JsonProperty("resourceId")
  String resourceId
) {}
