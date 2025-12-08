package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.eventhub.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EventhubSkuTier {
  BASIC("Basic"),
  STANDARD("Standard"),
  PREMIUM("Premium");

  private final String id;

  EventhubSkuTier(final String id) {
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
