package com.yanchware.fractal.sdk.domain.environment;

import com.fasterxml.jackson.annotation.JsonValue;

import static org.apache.commons.lang3.StringUtils.isBlank;

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
    if(isBlank(text)) {
      throw new IllegalArgumentException("Environment type cannot be null or blank");
    }

    for (var item : EnvironmentType.values()) {
      if (item.environmentType.equalsIgnoreCase(text)) {
        return item;
      }
    }

    throw new IllegalArgumentException("Invalid environment type: " + text);
  }

  @Override
  public String toString() {
    return environmentType;
  }
}
