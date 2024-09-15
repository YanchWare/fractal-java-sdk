package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageAutoGrow {
  ENABLED("Enabled"),
  DISABLED("Disabled");

  private final String id;

  AzureStorageAutoGrow(final String id) {
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
