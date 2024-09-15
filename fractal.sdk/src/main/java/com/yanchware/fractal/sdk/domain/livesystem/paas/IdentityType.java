package com.yanchware.fractal.sdk.domain.livesystem.paas;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum IdentityType {
  KUBELET_IDENTITY("KubeletIdentity"),
  USER_ASSIGNED_IDENTITY("UserAssignedIdentity");

  private final String identityType;

  IdentityType(String identityType) {
    this.identityType = identityType;
  }

  @JsonCreator
  public IdentityType getFromStringValue(String identityType) {
    return IdentityType.valueOf(identityType);
  }

  @JsonValue
  public String getIdentityType() {
    return identityType;
  }

  @Override
  public String toString() {
    return identityType;
  }
}
