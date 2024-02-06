package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure.storageaccount.valueobjects;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureRecoveryServicesSkuName {
    STANDARD ("Standard"),
    RS0 ("RS0");
  
    private final String id;
  
    AzureRecoveryServicesSkuName(final String id) {
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
