package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.aks;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureAgentPoolMode {
  SYSTEM("System"),
  USER("User");

  private final String id;

  AzureAgentPoolMode(final String id) {
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
