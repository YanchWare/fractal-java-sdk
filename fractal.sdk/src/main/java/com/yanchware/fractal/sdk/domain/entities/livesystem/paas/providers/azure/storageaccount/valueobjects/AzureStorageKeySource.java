package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageKeySource {
  MICROSOFT_STORAGE("Microsoft.Storage"),
  MICROSOFT_KEYVAULT("Microsoft.Keyvault");
  
  private final String id;

  AzureStorageKeySource(final String id) {
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
