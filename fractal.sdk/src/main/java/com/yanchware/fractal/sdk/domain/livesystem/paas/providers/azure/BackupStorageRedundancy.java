package com.yanchware.fractal.sdk.domain.livesystem.paas.providers.azure;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum BackupStorageRedundancy {
  GEO("Geo"),
  LOCAL("Local"),
  ZONE("Zone");

  private final String value;

  BackupStorageRedundancy(final String value) {
    this.value = value;
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

  public static BackupStorageRedundancy fromString(String redundancy) {
    return Arrays.stream(values())
        .filter(r -> r.value.equalsIgnoreCase(redundancy))
        .findFirst()
        .orElse(null);
  }
}
