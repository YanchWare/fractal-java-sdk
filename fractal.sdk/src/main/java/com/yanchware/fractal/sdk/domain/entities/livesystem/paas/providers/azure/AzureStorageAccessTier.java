package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageAccessTier {
  HOT("Hot"),
  COOL("Cool"),
  PREMIUM("Premium");
  
  private final String id;

  AzureStorageAccessTier(final String id) {
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
