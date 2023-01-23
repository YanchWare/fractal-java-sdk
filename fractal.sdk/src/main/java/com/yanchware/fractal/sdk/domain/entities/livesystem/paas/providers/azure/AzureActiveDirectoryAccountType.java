package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureActiveDirectoryAccountType {
  USER ("User"),
  COMPUTER ("Computer");
  
  private final String id;

  AzureActiveDirectoryAccountType(final String id) {
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
