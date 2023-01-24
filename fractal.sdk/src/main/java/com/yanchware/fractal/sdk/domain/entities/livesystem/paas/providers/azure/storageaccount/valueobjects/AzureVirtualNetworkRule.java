package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureStorageAction;
import com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects.AzureStorageProvisionState;

public record AzureVirtualNetworkRule(
  @JsonProperty(value = "id")
  String virtualNetworkResourceId,
  @JsonProperty("action")
  AzureStorageAction action,
  @JsonProperty("state")
  AzureStorageProvisionState state
) {}