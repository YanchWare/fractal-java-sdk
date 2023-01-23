package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AzureStorageIpRule(
  @JsonProperty(value = "value")
  String ipAddressOrRange,
  @JsonProperty("action")
  AzureStorageAction action
) {}
