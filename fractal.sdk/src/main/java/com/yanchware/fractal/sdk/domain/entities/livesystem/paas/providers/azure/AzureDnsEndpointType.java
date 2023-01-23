package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureDnsEndpointType {
  STANDARD ("Standard"),
  AZURE_DNS_ZONE ("AzureDnsZone");
  
  private final String id;

  AzureDnsEndpointType(final String id) {
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
