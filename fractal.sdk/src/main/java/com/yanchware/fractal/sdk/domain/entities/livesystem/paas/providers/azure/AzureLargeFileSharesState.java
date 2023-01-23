package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureLargeFileSharesState {
  DISABLED ("Disabled"),
  ENABLED ("Enabled");
  
  private final String id;

  AzureLargeFileSharesState(final String id) {
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
