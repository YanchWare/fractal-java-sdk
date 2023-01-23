package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageSkuName {
  STANDARD_LRS ("Standard_LRS"),
  STANDARD_GRS ("Standard_GRS"),
  STANDARD_RAGRS ("Standard_RAGRS"),
  STANDARD_ZRS ("Standard_ZRS"),
  PREMIUM_LRS ("Premium_LRS"),
  PREMIUM_ZRS ("Premium_ZRS"),
  STANDARD_GZRS ("Standard_GZRS"),
  STANDARD_RAGZRS ("Standard_RAGZRS");
  
  private final String id;

  AzureStorageSkuName(final String id) {
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
