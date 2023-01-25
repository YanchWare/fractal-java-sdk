package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureDirectoryServiceOptions {
  NONE ("None"),
  AADDS ("AADDS"),
  AD ("AD"),
  AADKERB ("AADKERB");
  
  private final String id;

  AzureDirectoryServiceOptions(final String id) {
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
