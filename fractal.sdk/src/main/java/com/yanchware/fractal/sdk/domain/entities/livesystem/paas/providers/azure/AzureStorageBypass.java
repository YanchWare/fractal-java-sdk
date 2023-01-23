package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageBypass {
  NONE ("None"),
  LOGGING ("Logging"),
  METRICS ("Metrics"),
  AZURE_SERVICES ("AzureServices");
  
  private final String id;

  AzureStorageBypass(final String id) {
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
