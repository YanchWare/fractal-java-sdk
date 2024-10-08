package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.annotation.JsonValue;

public enum EnvironmentType {
  PERSONAL("Personal"),
  ORGANIZATIONAL("Organizational");

  private final String environmentType;

  EnvironmentType(final String environmentType) {
    this.environmentType = environmentType;
  }

  @JsonValue
  public String getEnvironmentType() {
    return environmentType;
  }

  public static EnvironmentType fromString(String text) {
    for (var item : EnvironmentType.values()) {
      if (item.environmentType.equalsIgnoreCase(text)) {
        return item;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return environmentType;
  }
}
