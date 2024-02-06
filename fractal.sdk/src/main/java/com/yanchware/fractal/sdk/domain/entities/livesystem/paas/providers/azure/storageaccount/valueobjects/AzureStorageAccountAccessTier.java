package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureStorageAccountAccessTier {
  HOT("Hot"),
  COOL("Cool"),
  PREMIUM("Premium");
  
  private final String id;

  AzureStorageAccountAccessTier(final String id) {
    this.id = id;
  }

  @JsonCreator
  public static AzureStorageAccountAccessTier fromString(String value) {
    if (value == null) {
      return null;
    }
    AzureStorageAccountAccessTier[] items = AzureStorageAccountAccessTier.values();
    for (var item : items) {
      if (item.toString().equalsIgnoreCase(value)) {
        return item;
      }
    }
    return null;
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
