package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageProvisionState {
  PROVISIONING ("Provisioning"),
  DEPROVISIONING ("Deprovisioning"),
  SUCCEEDED ("Succeeded"),
  FAILED ("FAILED");
  
  private final String id;

  AzureStorageProvisionState(final String id) {
    this.id = id;
  }

  @JsonValue
  public String getId() {
    return id;
  }

  @Override
  public String toString() {
    return id;
  }
}
