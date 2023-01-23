package com.yanchware.fractal.sdk.domain.entities.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AzureIdentityType {
  NONE ("None"),
  SYSTEM_ASSIGNED ("SystemAssigned"),
  USER_ASSIGNED ("UserAssigned"),
  SYSTEM_ASSIGNED_USER_ASSIGNED ("SystemAssigned,UserAssigned");
  
  private final String id;

  AzureIdentityType(final String id) {
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
