package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.servicebus.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceBusSkuTier {
  BASIC("Basic"),
  STANDARD("Standard"),
  PREMIUM("Premium");

  private final String id;

  ServiceBusSkuTier(final String id) {
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
