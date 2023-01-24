package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AzureUserAssignedIdentity(
  @JsonProperty(value = "principalId")
  String principalId,
  @JsonProperty(value = "clientId")
  String clientId
) {}
