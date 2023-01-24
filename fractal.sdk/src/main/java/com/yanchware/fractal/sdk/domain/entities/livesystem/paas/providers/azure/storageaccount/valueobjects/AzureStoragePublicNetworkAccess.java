package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStoragePublicNetworkAccess {
  ENABLED ("Enabled"),
  DISABLED ("Disabled");

  private final String id;

  AzureStoragePublicNetworkAccess(final String id) {
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
