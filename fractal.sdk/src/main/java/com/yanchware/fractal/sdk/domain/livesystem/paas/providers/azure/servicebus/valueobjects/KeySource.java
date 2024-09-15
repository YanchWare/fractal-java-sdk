package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.servicebus.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum KeySource {
  MICROSOFT_KEYVAULT("Microsoft.Keyvault");

  private final String id;

  KeySource(final String id) {
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
