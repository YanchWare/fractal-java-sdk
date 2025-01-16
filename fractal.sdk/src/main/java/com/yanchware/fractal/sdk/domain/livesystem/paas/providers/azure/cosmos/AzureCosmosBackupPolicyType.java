package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure.cosmos;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum AzureCosmosBackupPolicyType {
  PERIODIC("Periodic"),
  CONTINUOUS("Continuous");

  private final String value;

  AzureCosmosBackupPolicyType(final String value) {
    this.value = value;
  }

  public static AzureCosmosBackupPolicyType fromString(String redundancy) {
    return Arrays.stream(values())
      .filter(t -> t.value.equalsIgnoreCase(redundancy))
      .findFirst()
      .orElse(null);
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

}
